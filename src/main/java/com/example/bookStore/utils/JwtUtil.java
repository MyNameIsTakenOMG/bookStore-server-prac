package com.example.bookStore.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private String JWT_SECRET = "secret";
    public String generateJwtToken(Authentication authentication){
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims,authentication);
    }

    private String createToken(Map<String, Object> claims, Authentication authentication) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(authentication.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }
}
