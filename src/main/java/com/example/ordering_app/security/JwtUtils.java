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

@Component // register this as a Spring bean so it can be injected elsewhere
public class JwtUtils {

    private final SecretKey key; // the signing key for token verification
    private final long expirationMs; // how long a token is valid

    public JwtUtils(
            @Value("${app.jwt-secret}") String secret,  // read secret string from application.properties
            @Value("${app.jwt-expiration-ms}") long expirationMs) {
        // Key must be at least 256 bits (32 bytes) for HS256
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); // convert the raw string into a cryptographic key
        this.expirationMs = expirationMs; // turn the string into bytes first
    }

    public String generateToken(String username, String role) {
        Date now    = new Date();
        Date expiry = new Date(now.getTime() + expirationMs); // expriration after 1 hour

        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key)
                .compact();
    }

    // Returns null if the token is invalid or expired
    public Claims validateAndParse(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
}
