package com.tfg.msvc.authservice.controller;

import com.tfg.msvc.authservice.dto.AuthDto;
import com.tfg.msvc.authservice.dto.ResponseDto;
import com.tfg.msvc.authservice.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody AuthDto authDto){
        ResponseDto response = authService.login(authDto);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,response.getRefreshCookieToken().toString())
                .body(response);
    }
    @PostMapping("/logout")

    public ResponseEntity<ResponseDto> logout(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request,"REFRESH-TOKEN");
        if(cookie == null){
            return ResponseEntity.badRequest().build();
        }
        ResponseDto response = this.authService.logout("REFRESH-TOKEN");

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,response.getRefreshCookieToken().toString())
                .body(response);
    }
    @PostMapping("/refresh")
    public ResponseEntity<ResponseDto> refreshToken(@CookieValue(name = "REFRESH-TOKEN") String refreshToken){
        if(refreshToken == null){
            return ResponseEntity.status(401).build();
        }
        ResponseDto responseDto = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(responseDto);
    }
}
