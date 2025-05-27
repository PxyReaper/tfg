package com.tfg.msvc.product_service.persistence;

import com.tfg.msvc.product_service.controller.DTO.ProductDTO;
import com.tfg.msvc.product_service.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IProductDAO {
    Page<Product> findAll(int page, int size);

    Optional<Product> findById(long id);

    void save(Product product, List<MultipartFile> files );

    void deleteById(long id);

    void update(Product product, Long id);

    boolean existsByIds(List<ProductDTO> productDTOS);
}
