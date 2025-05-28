package com.teide.tfg.msvc.payment_service.client;

import com.teide.tfg.msvc.payment_service.dto.ProductDto;
import com.teide.tfg.msvc.payment_service.dto.ResponseDtoClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {
 @PostMapping("/check-existence")
    public ResponseDtoClient<Boolean> checkExistence(@RequestBody List<ProductDto> productDtos);
 @GetMapping("/id/{id}")
public ResponseDtoClient<ProductDto> getProductById(@PathVariable("id") Long id);
}
