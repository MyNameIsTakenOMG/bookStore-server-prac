package com.example.bookStore.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.security.auth.kerberos.EncryptionKey;
import java.security.Key;
import java.util.*;

@Component
public class JwtUtil {
    private SecretKey JWT_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);
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
//                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .signWith(JWT_SECRET,SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication validateJwt(String jwtToken) {
        Claims claims = extractClaims(jwtToken);
        if(claims.getExpiration().before(new Date())){
            return null;
        }
        String username = claims.getSubject();
        return new UsernamePasswordAuthenticationToken(username,null,new ArrayList<>());
    }

    private Claims extractClaims(String jwtToken) {
//        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
//        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return Jwts.parserBuilder().setSigningKey(JWT_SECRET).build().parseClaimsJws(jwtToken).getBody();
    }
}
