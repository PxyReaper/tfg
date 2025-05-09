package com.tfg.msvc.product_service.controller;

import com.tfg.msvc.product_service.controller.DTO.ProductDTO;
import com.tfg.msvc.product_service.controller.DTO.ResponseDto;
import com.tfg.msvc.product_service.entities.Product;
import com.tfg.msvc.product_service.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductController {

    private final  IProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ProductDTO>> findById(@PathVariable Long id) {
        ResponseDto<ProductDTO> responseDto = productService.findById(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping()
    public ResponseEntity<ResponseDto<List<ProductDTO>>> findAll(@RequestParam(name = "page",defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size ) {
      ResponseDto<List<ProductDTO>> responseDto = this.productService.findAll(page,size);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping()
    public ResponseEntity<Void> save(@RequestBody ProductDTO productDTO) throws URISyntaxException {

        if (productDTO.getName().isBlank() || productDTO.getPrice() == null || productDTO.getDescription() == null) {
            return ResponseEntity.badRequest().build();
        }
        productService.save(productDTO);
        return ResponseEntity.created(new URI("/api/product/save")).build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ProductDTO productDTO) throws URISyntaxException {
        this.productService.update(productDTO, id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
         productService.deleteById(id);
         return ResponseEntity.ok().build();
    }
}
