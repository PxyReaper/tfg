package com.tfg.msvc.product_service.persistence.impl;

import com.tfg.msvc.product_service.entities.Product;
import com.tfg.msvc.product_service.exception.ProductNotFoundException;
import com.tfg.msvc.product_service.persistence.IProductDAO;
import com.tfg.msvc.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public class ProductDAOImpl implements IProductDAO {


    private final  ProductRepository productRepository;

    @Override
    public Page<Product> findAll(int page, int size) {
      Pageable pageable = PageRequest.of(page, size);
        return  productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
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
}
