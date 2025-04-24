package com.tfg.msvc.authservice.service;

import com.tfg.msvc.authservice.dto.AuthDto;
import com.tfg.msvc.authservice.dto.ResponseDto;
import com.tfg.msvc.authservice.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public ResponseDto login(AuthDto  authDto){
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword())
            );
            String token = jwtUtils.generateToken(auth);
            return new ResponseDto(token, "Usuario autenticado correctamente");

    }
}
