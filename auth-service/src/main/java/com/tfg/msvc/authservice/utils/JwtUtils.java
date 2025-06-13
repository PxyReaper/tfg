package com.tfg.msvc.authservice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {
    @Value("${spring.private.user}")
    private String privateKey;

    public String generateToken(Authentication auth){
        String username = auth.getName();
        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        List<String> authorities = auth.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).toList();
        return JWT.create()
                .withIssuer("AMAZONIA-JWT")
                .withClaim("role", authorities)
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(Instant.now().plusSeconds(120L))
                .sign(algorithm);
    }

    public String generateRefreshToken(Authentication authentication){
        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        String username = authentication.getName();

        return JWT.create()
                .withIssuer("AMAZONIA-JWT")
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS))
                .sign(algorithm);
    }

    public boolean verifyToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("AMAZONIA-JWT")
                    .build();
            verifier.verify(token);
            return true;
        }catch (JWTVerificationException ex){
            System.err.println("JWT Verification failed: " + ex.getMessage());
            return false;
        }
    }

    // ✅ Método seguro que verifica la firma antes de extraer el subject
    public String getSubject(String token){
        try {
            DecodedJWT decodedJWT = this.decode(token);
            return decodedJWT.getSubject();

        } catch (JWTVerificationException ex) {
            throw new RuntimeException("Invalid token: " + ex.getMessage());
        }
    }

    // ✅ Nuevo método que verifica Y decodifica el token
    private DecodedJWT verifyAndDecodeToken(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("AMAZONIA-JWT")
                .build();
        return verifier.verify(token); // Esto verifica Y decodifica
    }

    // ✅ Método adicional para obtener claims de forma segura
    public List<String> getRoles(String token) {
        try {
            DecodedJWT decodedJWT = verifyAndDecodeToken(token);
            return decodedJWT.getClaim("role").asList(String.class);
        } catch (JWTVerificationException ex) {
            throw new RuntimeException("Invalid token: " + ex.getMessage());
        }
    }
    public DecodedJWT decode(String token){
        return JWT.decode(token);
    }
    public ResponseCookie generateRefreshTokenCookie(String refreshToken){
        return generateCookie("REFRESH-TOKEN", refreshToken, "/");
    }

    public ResponseCookie generateCleanResponseCookie(String cookieName){
        return ResponseCookie.from(cookieName, "").path("/").maxAge(0).build();
    }

    private ResponseCookie generateCookie(String cookieName, String cookieValue, String path){
        return ResponseCookie.from(cookieName, cookieValue)
                .path(path)
                .maxAge(Duration.ofDays(7))
                .httpOnly(true)
                .secure(false)
                .build();
    }
}