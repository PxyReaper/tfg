package com.tfg.msvc.product_service.service;

import com.tfg.msvc.product_service.controller.DTO.ProductDTO;
import com.tfg.msvc.product_service.controller.DTO.ResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {

    ResponseDto<List<ProductDTO>> findAll(int page, int size);

    ResponseDto<ProductDTO> findById(long id);

    void save(ProductDTO product, List<MultipartFile> files);

    void deleteById(long id);

    void update(ProductDTO product, Long id);

    boolean existsByIds(List<ProductDTO> products);
}
