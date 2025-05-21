package com.teide.tfg.order_service.clients;

import com.teide.tfg.order_service.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service")
public interface AuthClient {
    @PostMapping("/api/oauth/user")
    public UserDto getCurrentUser(@RequestParam(name = "token") String token);

}
