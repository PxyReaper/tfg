package com.tfg.msvc.product_service.schedule;

import com.tfg.msvc.product_service.controller.DTO.S3UrlPressigned;
import com.tfg.msvc.product_service.entities.Product;
import com.tfg.msvc.product_service.repository.ProductRepository;
import com.tfg.msvc.product_service.service.IAsyncS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UploadScheduleUrls {
    private final ProductRepository productRepository;
    private final IAsyncS3Service asyncS3Service;
    @Scheduled(cron = "0 */30 * * * *")
    public void refreshUrlsInDatabase(){
        CompletableFuture<Void> urls = this.asyncS3Service.getUrlsGroupedByDirectory()
                .thenAccept(urlList -> {
                    Map<Long, S3UrlPressigned> urlsPorProducto = urlList.stream()
                            .collect(Collectors.toMap(
                                    s3 -> Long.valueOf(s3.getDirectory()),
                                    s3 -> s3
                            ));

                    List<Long> ids = new ArrayList<>(urlsPorProducto.keySet());

                    List<Product> products = this.productRepository.findAllById(ids);

                    Map<Long, Product> productById = products.stream()
                            .collect(Collectors.toMap(Product::getId, Function.identity()));

                    for (Long productId : ids) {
                        Product product = productById.get(productId);
                        S3UrlPressigned s3Url = urlsPorProducto.get(productId);

                        if (product != null && s3Url != null) {
                            // Asumiendo que getUrls() devuelve List<String> con URLs, y que el nombre del archivo está dentro de la URL
                            for (String urlStr : s3Url.getUrls()) {
                                if (urlStr.contains("portada")) {
                                    product.setPortada(urlStr);
                                } else if (urlStr.contains("lateral_izquierdo")) {
                                    product.setLateralIzquierdo(urlStr);
                                } else if (urlStr.contains("lateral_derecho")) {
                                    product.setLateralDerecho(urlStr);
                                } else if (urlStr.contains("arriba")) {
                                    product.setArriba(urlStr);

                                }
                                // más condiciones si hay más tipos
                            }


                        }
                    }

                    this.productRepository.saveAll(products);
                    System.out.println("Productos guardados");
                });

    }
    private String getFileNameFromUrl(String url) {
        if (url == null) return "";
        // Quitar los parámetros (todo lo que viene después de '?')
        int paramIndex = url.indexOf('?');
        String cleanUrl = (paramIndex != -1) ? url.substring(0, paramIndex) : url;

        // Extraer el archivo (lo que va después del último '/')
        int lastSlashIndex = cleanUrl.lastIndexOf('/');
        if (lastSlashIndex == -1) return cleanUrl;
        return cleanUrl.substring(lastSlashIndex + 1);
    }

}
