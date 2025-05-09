package com.tfg.msvc.product_service.service.impl;

import com.tfg.msvc.product_service.entities.Product;
import com.tfg.msvc.product_service.persistence.IProductDAO;
import com.tfg.msvc.product_service.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDAO productDAO;

    @Override
    public List<Product> findAll() {
        return productDAO.findAll();
    }

    @Override
    public Optional<Product> findById(long id) {
        return productDAO.findById(id);
    }

    @Override
    public void save(Product product) {
        productDAO.save(product);
    }

    @Override
    public void deleteById(long id) {
        productDAO.deleteById(id);
    }
}
