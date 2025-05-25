package com.tfg.msvc.authservice.clients;

import com.tfg.msvc.authservice.dto.ResponseClientDto;
import com.tfg.msvc.authservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/mail/{mail}")
    public ResponseEntity<ResponseClientDto> findByMail(@PathVariable String mail);
    @PostMapping
    public void save(@RequestBody UserDto responseClientDto);

}
