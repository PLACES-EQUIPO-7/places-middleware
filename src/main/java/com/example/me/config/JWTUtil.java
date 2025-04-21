package com.example.me.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.me.utils.RSAKeyLoader;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPublicKey;

@Component
public class JWTUtil {

    private final Algorithm ALGORITHM;

    public JWTUtil() throws Exception {

        RSAPublicKey PUBLIC_KEY = RSAKeyLoader.loadPublicKey("./src/main/resources/public_key.pem");
        ALGORITHM = Algorithm.RSA256(PUBLIC_KEY, null);
    }


    public String getUserFromToken(String token) {
        token = token.replace("Bearer ", "");
        token = token.trim();
        return JWT.require(ALGORITHM)
                .build()
                .verify(token)
                .getSubject();
    }
}
