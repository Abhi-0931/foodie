package com.foodie.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class JwtUtil {

    private static final String SECRET = "foodie_secret_key_foodie_secret_key_12345"; // longer is better

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // Generate JWT token with email as subject
    public String generateTokenByEmail(String email) {
        return Jwts.builder()
                .setSubject(email) // email as subject
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 ))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7)) // 7 days
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract email from token
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String email) {
        return email.equals(extractEmail(token)) && !isTokenExpired(token);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(getSigningKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }
}