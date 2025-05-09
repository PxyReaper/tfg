package com.tfg.msvc.product_service.service;

import com.tfg.msvc.product_service.entities.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    List<Product> findAll();

    Optional<Product> findById(long id);

    void save(Product product);

    void deleteById(long id);
}
