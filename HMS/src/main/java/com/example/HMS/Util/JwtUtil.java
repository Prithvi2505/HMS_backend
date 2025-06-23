package com.example.HMS.Util;

import com.example.HMS.Repository.DoctorRepository;
import com.example.HMS.Repository.PatientRepository;
import com.example.HMS.Repository.StaffRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired private DoctorRepository doctorRepository;
    @Autowired private StaffRepository staffRepository;

    private final String SECRET = "my-super-secret-key-that-is-long-enough-1234567890!@#";
    private SecretKey key;
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 hour

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String username, String role) {
        // Find the user by username/email and extract ID internally
        int userId = fetchUserIdByEmail(username,role); // Implement this method

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .claim("id", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

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

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String email) {
        return email.equals(extractUsername(token)) && !isTokenExpired(token);
    }
    private int fetchUserIdByEmail(String email,String role) {
        if (role.equals("patient")) {
            return patientRepository.findByEmail(email).getId();
        } else if (role.equals("doctor")) {
            return doctorRepository.findByEmail(email).getId();
        } else if (role.equals("staff")) {
            return staffRepository.findByEmail(email).getId();
        }
        throw new RuntimeException("User not found for email: " + email);
    }
}

