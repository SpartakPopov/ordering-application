package com.example.ordering_app.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtFilterTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtFilter jwtFilter;

    @BeforeEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void cleanUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void validBearerToken_setsAuthenticationInSecurityContext() throws Exception {
        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn("manager");
        when(claims.get("role", String.class)).thenReturn("ROLE_MANAGER");

        when(request.getHeader("Authorization")).thenReturn("Bearer valid.token.here");
        when(jwtUtils.validateAndParse("valid.token.here")).thenReturn(Optional.of(claims));

        jwtFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("manager", SecurityContextHolder.getContext().getAuthentication().getName());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void invalidToken_doesNotSetAuthentication() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer bad.token");
        when(jwtUtils.validateAndParse("bad.token")).thenReturn(Optional.empty());

        jwtFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void noAuthorizationHeader_doesNotSetAuthentication() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
        verify(jwtUtils, never()).validateAndParse(any());
    }

    @Test
    void authHeaderWithoutBearerPrefix_doesNotSetAuthentication() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Basic dXNlcjpwYXNz");

        jwtFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
        verify(jwtUtils, never()).validateAndParse(any());
    }
}
