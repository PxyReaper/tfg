package com.teide.tfg.order_service.clients;

import com.teide.tfg.order_service.dto.ResponseClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/mail/{mail}")
    public ResponseClientDto findByMail(@PathVariable("mail") String mail);

}
