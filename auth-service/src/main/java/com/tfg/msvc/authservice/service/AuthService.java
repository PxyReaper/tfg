package com.tfg.msvc.authservice.service;

import com.tfg.msvc.authservice.dto.AuthDto;
import com.tfg.msvc.authservice.dto.ResponseDto;
import com.tfg.msvc.authservice.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserService  userService;

    public ResponseDto login(AuthDto  authDto){
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword())
            );
            String token = jwtUtils.generateToken(auth);
            String refreshToken = jwtUtils.generateRefreshToken(auth);
            ResponseCookie cookie = jwtUtils.generateRefreshTokenCookie(refreshToken);
        System.out.println(cookie != null ? "Cookie no nula": "Cookie nula");
            return new ResponseDto(token,cookie ,"Usuario autenticado correctamente");

    }
    public ResponseDto logout(String cookieName){
        ResponseCookie cookie = jwtUtils.generateCleanResponseCookie(cookieName);
        return new ResponseDto(null,cookie,"Sesión del usuario cerrada correctamente");
    }
    public ResponseDto refreshToken(String refreshToken){
        String username = jwtUtils.getSubject(refreshToken);
        UserDetails user = userService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getUsername(), null, user.getAuthorities()
        );
        String token = jwtUtils.generateToken(authentication);
        return new ResponseDto(token, null, null);

    }
}
