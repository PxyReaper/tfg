package com.tfg.msvc.product_service.persistence.impl;

import com.tfg.msvc.product_service.controller.DTO.FileUploadRequest;
import com.tfg.msvc.product_service.controller.DTO.ProductDTO;
import com.tfg.msvc.product_service.entities.Product;
import com.tfg.msvc.product_service.exception.ProductNotFoundException;
import com.tfg.msvc.product_service.persistence.IProductDAO;
import com.tfg.msvc.product_service.repository.ProductRepository;
import com.tfg.msvc.product_service.service.IAsyncS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductDAOImpl implements IProductDAO {

    private final IAsyncS3Service asyncS3Service;
    private final ProductRepository productRepository;

    @Override
    public Page<Product> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }

    @Override
    public void save(Product product, List<MultipartFile> files) {
        List<FileUploadRequest> fileUploadRequests = files.stream()
                .map(f -> {
                    try {
                        return new FileUploadRequest(f.getBytes(), f.getOriginalFilename());
                    } catch (IOException e) {
                        throw new RuntimeException("Error reading file: " + f.getOriginalFilename(), e);
                    }
                })
                .toList();

        Product productSaved = productRepository.save(product);

        this.asyncS3Service.uploadFilesFromProduct(String.valueOf(productSaved.getId()), fileUploadRequests)
                .thenCompose(v -> this.asyncS3Service.getUrlsFromBucket(String.valueOf(productSaved.getId())))
                .thenApply(urls -> {
                    Map<String, String> urlsMap = urls.stream().collect(Collectors.toMap(
                            url -> {
                                String[] parts = url.split("/");
                                return parts[parts.length - 1].split("\\.")[0]; // "portada", "lateral_izquierdo", etc.
                            },
                            url -> url,
                            (u1, u2) -> u1 // En caso de colisión, mantener el primero
                    ));

                    productSaved.setPortada(urlsMap.get("portada"));
                    productSaved.setLateralIzquierdo(urlsMap.get("lateral_izquierdo"));
                    productSaved.setLateralDerecho(urlsMap.get("lateral_derecho"));
                    productSaved.setArriba(urlsMap.get("arriba"));
                    return productRepository.save(productSaved);
                })
                .exceptionally(ex -> {
                    // Manejo de errores asincrónicos
                    System.err.println("Error al subir archivos y actualizar el producto: " + ex.getMessage());
                    return null;
                });
    }


    @Override
    public void deleteById(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void update(Product product, Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }
        productOptional.get().setName(product.getName());
        productOptional.get().setDescription(product.getDescription());
        productOptional.get().setPrice(product.getPrice());
        productRepository.save(productOptional.get());
    }

    @Override
    public boolean existsByIds(List<ProductDTO> productDTOS) {
        List<Long> productIds = productDTOS.stream().map(ProductDTO::getId).collect(Collectors.toList());
        List<Product> products = productRepository.findAllById(productIds);

        if (products.size() != productDTOS.size()) {
            System.out.println("Algunos productos no existen en la base de datos");
            return false;
        }

        // Opcional: puedes reordenar la lista 'products' para que coincida con el orden de los DTOs
        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        for (ProductDTO dto : productDTOS) {
            Product dbProduct = productMap.get(dto.getId());
            if (dbProduct == null) {
                System.out.println("Producto con ID " + dto.getId() + " no existe.");
                return false;
            }

            // Aquí puedes reconstruir el DTO si quieres usarlo después
            // o simplemente ignorar name/description/price que viene del cliente
        }

        return true;
    }

}
