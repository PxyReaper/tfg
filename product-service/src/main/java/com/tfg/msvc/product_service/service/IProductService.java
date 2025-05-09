package com.tfg.msvc.product_service.service;

import com.tfg.msvc.product_service.controller.DTO.ProductDTO;
import com.tfg.msvc.product_service.controller.DTO.ResponseDto;
import com.tfg.msvc.product_service.entities.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    ResponseDto<List<ProductDTO>> findAll(int page, int size);

    ResponseDto<ProductDTO> findById(long id);

    void save(ProductDTO product);

    void deleteById(long id);
    void update(ProductDTO product,Long id);
}
