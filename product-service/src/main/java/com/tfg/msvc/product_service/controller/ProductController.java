package com.tfg.msvc.product_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfg.msvc.product_service.MessageExample;
import com.tfg.msvc.product_service.controller.DTO.ProductDTO;
import com.tfg.msvc.product_service.controller.DTO.ResponseDto;
import com.tfg.msvc.product_service.factory.ResponseFactory;
import com.tfg.msvc.product_service.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;


    @GetMapping("/id/{id}")
    public ResponseEntity<ResponseDto<ProductDTO>> findById(@PathVariable Long id) {
        ResponseDto<ProductDTO> responseDto = productService.findById(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping()
    public ResponseEntity<ResponseDto<List<ProductDTO>>> findAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        ResponseDto<List<ProductDTO>> responseDto = this.productService.findAll(page, size);
        return ResponseEntity.ok(responseDto);
    }


    @PostMapping("/check-existence")
    public ResponseEntity<ResponseDto<Boolean>> exits(@RequestBody List<ProductDTO> productDTOS) {

        Boolean exist = this.productService.existsByIds(productDTOS);
        ResponseDto<Boolean> responseDto = ResponseFactory.generateSuccesResponse(exist, null, HttpStatus.OK.value()
                , null, null, null);
        return ResponseEntity.ok(responseDto);

    }

    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> save(@RequestPart(name = "product") String product, @RequestPart List<MultipartFile> files ) throws URISyntaxException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDTO productDTO = objectMapper.readValue(product, ProductDTO.class);

        if (productDTO.getName().isBlank() || productDTO.getPrice() == null || productDTO.getDescription() == null) {
            return ResponseEntity.badRequest().build();
        }
        productService.save(productDTO,files);


        return ResponseEntity.created(new URI("/api/product/save")).build();
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ProductDTO productDTO)  {
        this.productService.update(productDTO, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
