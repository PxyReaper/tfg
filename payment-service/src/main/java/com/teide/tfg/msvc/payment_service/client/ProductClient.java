package com.teide.tfg.msvc.payment_service.client;

import com.teide.tfg.msvc.payment_service.dto.ProductDto;
import com.teide.tfg.msvc.payment_service.dto.ResponseDtoClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {
 @PostMapping("/check-existence")
    public ResponseDtoClient checkExistence(@RequestBody List<ProductDto> productDtos);
}
