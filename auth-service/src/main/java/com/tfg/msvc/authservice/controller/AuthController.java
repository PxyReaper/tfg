package com.tfg.msvc.authservice.controller;

import com.tfg.msvc.authservice.dto.AuthDto;
import com.tfg.msvc.authservice.dto.AuthGoogleDto;
import com.tfg.msvc.authservice.dto.ResponseDto;
import com.tfg.msvc.authservice.dto.UserDto;
import com.tfg.msvc.authservice.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

@RestController
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
        System.out.println("Borrando cookie...");
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
    @PostMapping("/user")
    public ResponseEntity<UserDto> getUser(@RequestParam(name = "token") String token){
        UserDto currentUser = this.authService.getCurrentUser(token);
        return ResponseEntity.ok(currentUser);
    }
    @PostMapping("/google/login")
    public ResponseEntity<ResponseDto> googleLogin(@RequestBody AuthGoogleDto authDto){
        ResponseDto response = authService.getAuthWithGoogle(authDto);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,response.getRefreshCookieToken().toString())
                .body(response);
    }
}
