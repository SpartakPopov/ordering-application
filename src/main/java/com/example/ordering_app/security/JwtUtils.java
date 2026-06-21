package com.example.ordering_app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtils {

    private final SecretKey key;
    private final long expirationMs;

    public JwtUtils(
            @Value("${app.jwt-secret}") String secret,
            @Value("${app.jwt-expiration-ms}") long expirationMs) {
        // Key must be at least 256 bits (32 bytes) for HS256
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); // convert the raw string into a cryptographic key
        this.expirationMs = expirationMs; // turn the string into bytes first
    }

    public String generateToken(String username, String role) {
        return generateToken(username, role, expirationMs);
    }

    public String generateToken(String username, String role, long customExpirationMs) {
        Date now    = new Date();
        Date expiry = new Date(now.getTime() + customExpirationMs);

        return Jwts.builder()
                .subject(username)      // who this token belongs to
                .claim("role", role)    // ROLE_MANAGER
                .issuedAt(now)          // when it was created
                .expiration(expiry)     // 1 hour from now
                .signWith(key)          // HS256 signature
                .compact();             // produce the xxx.yyy.zzz string
    }

    public Optional<Claims> validateAndParse(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return Optional.of(claims);
        } catch (JwtException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
