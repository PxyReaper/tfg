package com.tfg.msvc.product_service.service;

import com.tfg.msvc.product_service.controller.DTO.FileUploadRequest;
import com.tfg.msvc.product_service.controller.DTO.S3UrlPressigned;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IAsyncS3Service {
     CompletableFuture<Void> uploadFilesFromProduct(String productId, List<FileUploadRequest> files);
     CompletableFuture<String> uploadFile(String productId, String fileName,byte[] content);
     String generatePresignedInlineUrl(String fileName, Duration duration);
     CompletableFuture<List<S3UrlPressigned>> getUrlsGroupedByDirectory();
       CompletableFuture<List<String>> getUrlsFromBucket(String directory);


}
