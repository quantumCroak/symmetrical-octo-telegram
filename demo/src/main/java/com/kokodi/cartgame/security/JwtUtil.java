package com.kokodi.cartgame.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;

public class JwtUtil {
    private static final long EXPIRATION_TIME = 864_000_000; // 10 дней

    public static String generateToken(String login) {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(login)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, JwtAuthenticationFilter.SECRET)
                .compact();
    }
}
