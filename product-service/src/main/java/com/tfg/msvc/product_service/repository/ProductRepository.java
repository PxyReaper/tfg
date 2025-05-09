package com.tfg.msvc.product_service.repository;

import com.tfg.msvc.product_service.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
