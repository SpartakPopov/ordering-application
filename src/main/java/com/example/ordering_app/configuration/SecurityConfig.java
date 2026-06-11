package com.example.ordering_app.configuration;

import com.example.ordering_app.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // JWT is stateless — no session needed
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // CSRF not needed for stateless JWT APIs
            .csrf(AbstractHttpConfigurer::disable)

            .authorizeHttpRequests(auth -> auth
                // Public — customers and kitchen can access these without a token
                .requestMatchers(HttpMethod.GET,  "/api/menu/**").permitAll()
                .requestMatchers(HttpMethod.GET,  "/api/categories/**").permitAll()
                .requestMatchers(HttpMethod.GET,  "/api/orders/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/orders").permitAll()
                .requestMatchers(HttpMethod.PATCH,"/api/orders/**").hasAnyRole("KITCHEN", "MANAGER")
                .requestMatchers(HttpMethod.POST, "/api/payments/**").permitAll()
                .requestMatchers(HttpMethod.GET,  "/api/payments/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .requestMatchers("/ws/**").permitAll()
                .requestMatchers("/actuator/**").permitAll()

                // Protected — only authenticated managers can modify the menu
                .requestMatchers(HttpMethod.POST,   "/api/menu/**").hasRole("MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/menu/**").hasRole("MANAGER")
                .requestMatchers(HttpMethod.PATCH,  "/api/menu/**").hasRole("MANAGER")

                // Everything else requires authentication
                .anyRequest().authenticated()
            )

            // Run JwtFilter before Spring's default username/password filter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
