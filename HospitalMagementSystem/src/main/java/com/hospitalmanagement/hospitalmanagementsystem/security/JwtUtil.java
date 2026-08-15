package com.hospitalmanagement.hospitalmanagementsystem.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public String generateToken(String email,String role){
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getKey())
                .compact();
    }
    public boolean isTokenValid(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractEmail(String token){
        return Jwts.parserBuilder().setSigningKey(getKey())
                .build().parseClaimsJws(token).getBody().getSubject();
    }

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}

