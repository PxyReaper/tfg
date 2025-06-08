package com.tfg.msvc.authservice.service;

import com.tfg.msvc.authservice.clients.UserClient;
import com.tfg.msvc.authservice.dto.*;
import com.tfg.msvc.authservice.utils.JwtUtils;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
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
    private final UserClient userClient;

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
        return new ResponseDto(null,cookie,"Sesión del usuario cerrada correctamente, borrando cookie");
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
    public UserDto getCurrentUser(String token){
        String username = jwtUtils.getSubject(token);
        ResponseEntity<ResponseClientDto> response = userClient.findByMail(username);
        return response.getBody().getResult();

    }
    public ResponseDto getAuthWithGoogle(AuthGoogleDto authGoogleDto){
        try {
            ResponseEntity<ResponseClientDto> user = userClient.findByMail(authGoogleDto.getEmail());

            // 🔍 DEBUG - Añade estos logs temporalmente
            System.out.println("Status Code: " + user.getStatusCode());
            System.out.println("Status Code Value: " + user.getStatusCode().value());
            System.out.println("is4xxClientError: " + user.getStatusCode().is4xxClientError());
            System.out.println("Body: " + user.getBody());
        }catch(FeignException ex){
            UserDto userDto = new UserDto(null,authGoogleDto.getGiven_name(),authGoogleDto.getFamily_name(),
                    null,null,null,authGoogleDto.getEmail(),null,null,null,null,null);
            userClient.save(userDto);
        }
        UserDetails userAuthenticated = this.userService.loadUserByUsername(authGoogleDto.getEmail());
        Authentication auth =  new UsernamePasswordAuthenticationToken(
                userAuthenticated.getUsername(), null, userAuthenticated.getAuthorities()
        );
        String token = this.jwtUtils.generateToken(auth);
        String refreshToken = jwtUtils.generateRefreshToken(auth);
        ResponseCookie cookie = jwtUtils.generateRefreshTokenCookie(refreshToken);
        return new ResponseDto(token,cookie,"Usuario autenticado correctamente");
    }



}
