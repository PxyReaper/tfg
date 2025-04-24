package com.tfg.msvc.authservice.controller;

import com.tfg.msvc.authservice.dto.AuthDto;
import com.tfg.msvc.authservice.dto.ResponseDto;
import com.tfg.msvc.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody AuthDto authDto){
        ResponseDto response = authService.login(authDto);
        return ResponseEntity.ok(response);
    }
}
