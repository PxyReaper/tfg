package com.tfg.msvc.authservice.clients;

import com.tfg.msvc.authservice.dto.ResponseClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/{mail}")
    public ResponseClientDto findByMail(@PathVariable String mail);
}
