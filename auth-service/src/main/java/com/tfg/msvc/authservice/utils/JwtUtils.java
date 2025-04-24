package com.tfg.msvc.authservice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${spring.private.user}")
    private String privateKey;

    public String generateToken( Authentication auth){
        String username = auth.getName();
        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        return JWT.create()
                .withIssuer("AMAZONIA-JWT")
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(Instant.now().plusSeconds(120L))
                .sign(algorithm);
    }
}
