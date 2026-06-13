package com.example.ordering_app.security;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {

    private JwtUtils jwtUtils;

    @BeforeEach
    void setUp() {
        // 32-char secret satisfies the HS256 minimum key length
        jwtUtils = new JwtUtils("test-secret-key-minimum-32-chars!!", 3600000L);
    }

    @Test
    void generateToken_returnsNonNullToken() {
        String token = jwtUtils.generateToken("manager", "ROLE_MANAGER");
        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void validateAndParse_validToken_returnsClaimsWithCorrectUsername() {
        String token = jwtUtils.generateToken("manager", "ROLE_MANAGER");

        Optional<Claims> result = jwtUtils.validateAndParse(token);

        assertTrue(result.isPresent());
        assertEquals("manager", result.get().getSubject());
    }

    @Test
    void validateAndParse_validToken_returnsClaimsWithCorrectRole() {
        String token = jwtUtils.generateToken("kitchen", "ROLE_KITCHEN");

        Optional<Claims> result = jwtUtils.validateAndParse(token);

        assertTrue(result.isPresent());
        assertEquals("ROLE_KITCHEN", result.get().get("role", String.class));
    }

    @Test
    void validateAndParse_invalidToken_returnsEmpty() {
        Optional<Claims> result = jwtUtils.validateAndParse("this.is.not.a.valid.jwt");

        assertTrue(result.isEmpty());
    }

    @Test
    void validateAndParse_expiredToken_returnsEmpty() {
        JwtUtils shortLivedUtils = new JwtUtils("test-secret-key-minimum-32-chars!!", 1L);
        String token = shortLivedUtils.generateToken("manager", "ROLE_MANAGER");

        // Token with 1ms expiry is already expired by the time we parse it
        Optional<Claims> result = shortLivedUtils.validateAndParse(token);

        assertTrue(result.isEmpty());
    }

    @Test
    void validateAndParse_emptyString_returnsEmpty() {
        Optional<Claims> result = jwtUtils.validateAndParse("");

        assertTrue(result.isEmpty());
    }

    @Test
    void generateToken_withCustomExpiry_tokenIsValid() {
        String token = jwtUtils.generateToken("manager", "ROLE_MANAGER", 10 * 60 * 60 * 1000L);

        Optional<Claims> result = jwtUtils.validateAndParse(token);

        assertTrue(result.isPresent());
        assertEquals("manager", result.get().getSubject());
    }
}
