package com.example.ordering_app.controller;

import com.example.ordering_app.persistence.entity.UserEntity;
import com.example.ordering_app.persistence.impl.UserRepositoryJPA;
import com.example.ordering_app.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepositoryJPA userRepositoryJPA;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthController(UserRepositoryJPA userRepositoryJPA,
                          PasswordEncoder passwordEncoder,
                          JwtUtils jwtUtils) {
        this.userRepositoryJPA = userRepositoryJPA;
        this.passwordEncoder   = passwordEncoder;
        this.jwtUtils          = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        if (username == null || password == null) {
            return ResponseEntity.badRequest().build();
        }

        UserEntity user = userRepositoryJPA.findByUsername(username).orElse(null);

        // Same response for wrong username and wrong password — prevents user enumeration
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }

        long expirationMs = "ROLE_KITCHEN".equals(user.getRole())
                ? 10L * 60 * 60 * 1000   // 10 hours for kitchen staff
                :  1L * 60 * 60 * 1000;  //  1 hour  for manager
        String token = jwtUtils.generateToken(user.getUsername(), user.getRole(), expirationMs);
        return ResponseEntity.ok(Map.of("token", token, "role", user.getRole()));
    }
}
