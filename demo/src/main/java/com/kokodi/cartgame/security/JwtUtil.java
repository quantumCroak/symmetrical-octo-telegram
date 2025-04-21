package com.kokodi.cartgame.security;

import com.kokodi.cartgame.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtException;


import java.util.Date;
import java.util.HashMap;

import static javax.crypto.Cipher.SECRET_KEY;

public class JwtUtil {
    private static final String SECRET = "mySecretKey1234567890"; // Длинный секретный ключ
    private static final long EXPIRATION_TIME = 864_000_000; // 10 дней

    public static String generateToken(String login) {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(login)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }
}
