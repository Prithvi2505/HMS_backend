package com.example.HMS.Util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "my-super-secret-key-that-is-long-enough-1234567890!@#";
    private SecretKey key;

    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 hours

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    /**
     * Generates a JWT token containing email, role, and user ID.
     */
    public String generateToken(String email, String role, int userId) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .claim("id", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts all claims from a given token.
     */
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return (String) extractClaims(token).get("role");
    }

    public int extractUserId(String token) {
        return (int) extractClaims(token).get("id");
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String email) {
        return email.equals(extractUsername(token)) && !isTokenExpired(token);
    }
}
