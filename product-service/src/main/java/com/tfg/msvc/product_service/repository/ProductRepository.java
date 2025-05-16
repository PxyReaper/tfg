package com.tfg.msvc.product_service.repository;

import com.tfg.msvc.product_service.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByIdIn(List<Long> ids);
}
