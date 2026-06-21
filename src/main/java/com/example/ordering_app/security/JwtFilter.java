package com.example.ordering_app.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
// every single HTTP request for the backend before it reaches controller

        String header = request.getHeader("Authorization"); // the header from the frontend

        if (header != null && header.startsWith("Bearer ")) { // Bearer for checking if its JWT not another auth type
            String token = header.substring(7); // strip "Bearer " prefix so its just raw token string
            jwtUtils.validateAndParse(token).ifPresent(claims -> { // sends for validation to JWTUtils
                String username = claims.getSubject();
                String role     = claims.get("role", String.class);

                // Put authenticated user into the Security context for this request
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                List.of(new SimpleGrantedAuthority(role))// permissions
                        );
                SecurityContextHolder.getContext().setAuthentication(auth);
            });
        }

        chain.doFilter(request, response); // passes the request to the next step
    }
}
