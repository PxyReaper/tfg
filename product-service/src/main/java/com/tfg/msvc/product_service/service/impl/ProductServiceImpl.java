package com.tfg.msvc.product_service.service.impl;

import com.tfg.msvc.product_service.controller.DTO.ProductDTO;
import com.tfg.msvc.product_service.controller.DTO.ResponseDto;
import com.tfg.msvc.product_service.entities.Product;
import com.tfg.msvc.product_service.exception.ProductNotFoundException;
import com.tfg.msvc.product_service.factory.ResponseFactory;
import com.tfg.msvc.product_service.persistence.IProductDAO;
import com.tfg.msvc.product_service.service.IAsyncS3Service;
import com.tfg.msvc.product_service.service.IProductService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDAO productDAO;
    @Autowired
    private IAsyncS3Service asyncS3Service;

    @Override
    public ResponseDto<List<ProductDTO>> findAll(int page, int size) {
        Page<Product> products = productDAO.findAll(page, size);
        List<ProductDTO> productDTOS = products.stream().map(p -> ProductDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .frontPage(p.getPortada())
                .leftPage(p.getLateralIzquierdo())
                .rightPage(p.getLateralDerecho())
                .upperPage(p.getArriba())
                .build()).toList();
        return ResponseFactory.generateSuccesResponse(productDTOS, null,
                HttpStatus.SC_OK, page, size, products.getTotalPages());
    }

    @Override
    public ResponseDto<ProductDTO> findById(long id) {
        Optional<Product> product = productDAO.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }
        ProductDTO productDTO = ProductDTO.builder().id(product.get().getId())
                .name(product.get().getName())
                .description(product.get().getDescription())
                .price(product.get().getPrice())
                .frontPage(product.get().getPortada())
                .leftPage(product.get().getLateralIzquierdo())
                .rightPage(product.get().getLateralDerecho())
                .upperPage(product.get().getArriba())
                .build();
        ResponseDto<ProductDTO> response = ResponseFactory.generateSuccesResponse(productDTO,
                "Producto encontrado correctamente", HttpStatus.SC_OK, null, null, null);
        return response;
    }

    @Override
    public void save(ProductDTO product, List<MultipartFile> file) {
        Product product1 = Product.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();

         productDAO.save(product1,file);
    }

    @Override
    public void deleteById(long id) {
        productDAO.deleteById(id);
    }

    @Override
    public void update(ProductDTO product, Long id) {
        Product product1 = Product.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
        this.productDAO.update(product1, id);
    }

    @Override
    public boolean existsByIds(List<ProductDTO> productDTOS) {
        System.out.println("existsByIds");
        System.out.println(productDTOS);
        return productDAO.existsByIds(productDTOS);
    }
}
