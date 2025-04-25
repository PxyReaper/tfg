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

    public String generateToken( Authentication auth){
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
        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        try{
            JWTVerifier verifier =  JWT.require(algorithm)
                    .withIssuer("AMAZONIA-JWT")
                    .build();
            verifier.verify(token);
            return true;
        }catch (JWTVerificationException ex){
          return false;
        }
    }
    public String getSubject(String token){
        DecodedJWT decodedJWT = decodeToken(token);
        return decodedJWT.getSubject();
    }
    private DecodedJWT decodeToken(String token){
        return JWT.decode(token);
    }
    public ResponseCookie generateRefreshTokenCookie(String refreshToken){
        return generateCookie("REFRESH-TOKEN",refreshToken,"/api");
    }
    public ResponseCookie generateCleanResponseCookie(String cookieName){
        return ResponseCookie.from(cookieName,null).maxAge(0).build();

    }

    private ResponseCookie generateCookie(String cookieName,String cookieValue,String path){
        return ResponseCookie.from(cookieName,cookieValue).path(path).maxAge(Duration.ofDays(7))
                .httpOnly(true).build();
    }

}
