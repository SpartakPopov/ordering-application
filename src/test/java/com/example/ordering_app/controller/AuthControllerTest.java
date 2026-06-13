package com.example.ordering_app.controller;

import com.example.ordering_app.persistence.entity.UserEntity;
import com.example.ordering_app.persistence.impl.UserRepositoryJPA;
import com.example.ordering_app.security.JwtUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserRepositoryJPA userRepositoryJPA;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthController authController;

    @Test
    void login_validManagerCredentials_returns200WithToken() {
        UserEntity user = new UserEntity("manager", "hashed", "ROLE_MANAGER");
        when(userRepositoryJPA.findByUsername("manager")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("pass123", "hashed")).thenReturn(true);
        when(jwtUtils.generateToken(eq("manager"), eq("ROLE_MANAGER"), anyLong())).thenReturn("mock.jwt.token");

        ResponseEntity<Map<String, String>> response = authController.login(
                Map.of("username", "manager", "password", "pass123")
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("mock.jwt.token", response.getBody().get("token"));
        assertEquals("ROLE_MANAGER", response.getBody().get("role"));
    }

    @Test
    void login_validKitchenCredentials_returns200WithRoleKitchen() {
        UserEntity user = new UserEntity("kitchen", "hashed", "ROLE_KITCHEN");
        when(userRepositoryJPA.findByUsername("kitchen")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("kitchenpass", "hashed")).thenReturn(true);
        when(jwtUtils.generateToken(eq("kitchen"), eq("ROLE_KITCHEN"), anyLong())).thenReturn("kitchen.jwt.token");

        ResponseEntity<Map<String, String>> response = authController.login(
                Map.of("username", "kitchen", "password", "kitchenpass")
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ROLE_KITCHEN", response.getBody().get("role"));
    }

    @Test
    void login_wrongPassword_returns401() {
        UserEntity user = new UserEntity("manager", "hashed", "ROLE_MANAGER");
        when(userRepositoryJPA.findByUsername("manager")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpass", "hashed")).thenReturn(false);

        ResponseEntity<Map<String, String>> response = authController.login(
                Map.of("username", "manager", "password", "wrongpass")
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody().get("error"));
    }

    @Test
    void login_unknownUsername_returns401() {
        when(userRepositoryJPA.findByUsername("nobody")).thenReturn(Optional.empty());

        ResponseEntity<Map<String, String>> response = authController.login(
                Map.of("username", "nobody", "password", "pass")
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void login_missingUsername_returns400() {
        ResponseEntity<Map<String, String>> response = authController.login(
                Map.of("password", "pass123")
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void login_missingPassword_returns400() {
        ResponseEntity<Map<String, String>> response = authController.login(
                Map.of("username", "manager")
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
