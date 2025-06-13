package com.tfg.msvc.product_service.service.impl;

import com.tfg.msvc.product_service.controller.DTO.FileUploadRequest;
import com.tfg.msvc.product_service.controller.DTO.S3UrlPressigned;
import com.tfg.msvc.product_service.service.IAsyncS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AsyncS3ServiceImpl implements IAsyncS3Service {
    private final S3AsyncClient s3AsyncClient;
    private final S3Presigner s3Presigner;

    @Value("${aws.bucket.name}")
    private String bucketName;
    @Override
    @Async
    public CompletableFuture<Void> uploadFilesFromProduct(String productId, List<FileUploadRequest> files) {
        long startTime = System.nanoTime();
        System.out.println("Inicio del proceso asyncrono de archivos " + Thread.currentThread().getName());

        // ✅ Leer los bytes de todos los archivos inmediatamente
        List<CompletableFuture<?>> archivos = files.stream()
                .map(file -> {
                    try {
                        String originalFileName = file.getFileName();
                        byte[] content = file.getFile(); // SE LEE AQUÍ, ANTES DEL HILO ASYNC

                        return this.uploadFile(productId, originalFileName, content)
                                .exceptionally(ex -> {
                                    System.out.println("Error al subir archivo: " + originalFileName + " -> " + ex.getMessage());
                                    return null;
                                });
                    } catch (Exception e) {
                        System.out.println("Error al leer archivo " + file.getFileName() + ": " + e.getMessage());
                        return CompletableFuture.completedFuture(null);
                    }
                })
                .toList();

        return CompletableFuture.allOf(archivos.toArray(new CompletableFuture[0]))
                .thenRun(() -> {
                    long endTime = System.nanoTime();
                    long duration = endTime - startTime;
                    System.out.println("Tiempo total para que se hayan subido los archivos al S3 " +
                            duration / 1_000_000 + " ms");
                });
    };
    @Async
    @Override
    public CompletableFuture<String> uploadFile(String productId,String fileName, byte[] content) {
        String prefix = productId.concat("/");
        fileName = prefix + fileName;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        CompletableFuture<PutObjectResponse> response = this.s3AsyncClient.putObject(
                putObjectRequest,
                AsyncRequestBody.fromBytes(content)
        );

        return response.thenApply(PutObjectResponse::eTag);
    }
    @Override
    public String generatePresignedInlineUrl(String fileName, Duration duration) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(fileName)
                .responseContentDisposition("inline").build();
        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(duration)
                .getObjectRequest(getObjectRequest)
                .build();
        PresignedGetObjectRequest presignedGetObjectRequest =  this.s3Presigner.presignGetObject(getObjectPresignRequest);
        return presignedGetObjectRequest.url().toString();
    }
    public  CompletableFuture<List<String>> getUrlsFromBucket(String directory) {
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder().bucket(bucketName)
                .prefix(directory).build();
        return this.s3AsyncClient.listObjectsV2(listObjectsV2Request).thenApply(v -> {

            return    v.contents().stream()
                    .map(generate -> this.generatePresignedInlineUrl(generate.key(),Duration.ofDays(7)))
                    .toList();
        });
    }
    public CompletableFuture<List<S3UrlPressigned>> getUrlsGroupedByDirectory() {
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder().bucket(bucketName).build();
        return this.s3AsyncClient.listObjectsV2(listObjectsV2Request).thenCompose( response -> {
            Map<String, List<String>> keysByDirectory = response.contents().stream()
                    .map(S3Object::key)
                    .collect(Collectors.groupingBy(
                            this::extractDirectory
                    ));
            List<CompletableFuture<S3UrlPressigned>> urlsByDirectory = keysByDirectory.entrySet().stream()
                    .map( entry -> {
                        String directory = entry.getKey();
                        return getUrlsFromBucket(directory)
                                .thenApply( v -> new S3UrlPressigned(directory,v));
                    }).toList();
            CompletableFuture<Void> allDone = CompletableFuture.allOf(urlsByDirectory.toArray(new CompletableFuture[0]));
            return allDone.thenApply(v -> urlsByDirectory.stream()
                    .map(future -> future.getNow(null))
                    .toList());
        });
    }
    private String extractDirectory(String key) {
        int lastSlash = key.lastIndexOf('/');
        return lastSlash == -1 ? "" : key.substring(0, lastSlash);
    }
}
