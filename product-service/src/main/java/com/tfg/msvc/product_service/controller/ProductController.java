package com.tfg.msvc.product_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfg.msvc.product_service.MessageExample;
import com.tfg.msvc.product_service.controller.DTO.ProductDTO;
import com.tfg.msvc.product_service.controller.DTO.ResponseDto;
import com.tfg.msvc.product_service.entities.Product;
import com.tfg.msvc.product_service.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final  IProductService productService;
    private final KafkaTemplate<String, String> kafkaTemplate;

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
    @GetMapping("/test")
    public ResponseEntity<Void> test() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
            MessageExample messageExample = new MessageExample("Hola desde kafka objeto");
            String json = objectMapper.writeValueAsString(messageExample);
        kafkaTemplate.send("orders-topic",json);
        return ResponseEntity.ok().build();
    }
}
