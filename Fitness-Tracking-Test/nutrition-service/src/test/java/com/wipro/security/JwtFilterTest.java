package com.wipro.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class JwtFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private JwtFilter jwtFilter;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        jwtFilter =
                new JwtFilter(jwtUtil);

        SecurityContextHolder
                .clearContext();
    }

    @Test
    void doFilter_WithValidToken_ShouldAuthenticateUser()
            throws ServletException, IOException {

        // Arrange
        String token = "valid-token";

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer " + token);

        when(jwtUtil.extractUsername(token))
                .thenReturn("yaswanth");

        // Act
        jwtFilter.doFilter(
                request,
                response,
                filterChain);

        // Assert
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        assertNotNull(authentication);

        assertEquals(
                "yaswanth",
                authentication.getPrincipal());

        verify(filterChain)
                .doFilter(
                        request,
                        response);
    }

    @Test
    void doFilter_WithoutAuthorizationHeader_ShouldContinueFilterChain()
            throws ServletException, IOException {

        // Arrange
        when(request.getHeader("Authorization"))
                .thenReturn(null);

        // Act
        jwtFilter.doFilter(
                request,
                response,
                filterChain);

        // Assert
        assertEquals(
                null,
                SecurityContextHolder
                        .getContext()
                        .getAuthentication());

        verify(filterChain)
                .doFilter(
                        request,
                        response);
    }

    @Test
    void doFilter_WithInvalidToken_ShouldReturnUnauthorized()
            throws ServletException, IOException {

        // Arrange
        String token = "invalid-token";

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer " + token);

        when(jwtUtil.extractUsername(token))
                .thenThrow(
                        new io.jsonwebtoken.JwtException(
                                "Invalid JWT"));

        // Act
        jwtFilter.doFilter(
                request,
                response,
                filterChain);

        // Assert
        verify(response)
                .sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Invalid JWT Token");
    }

    @Test
    void doFilter_WithNonBearerAuthorization_ShouldContinueFilterChain()
            throws ServletException, IOException {

        // Arrange
        when(request.getHeader("Authorization"))
                .thenReturn("Basic username:password");

        // Act
        jwtFilter.doFilter(
                request,
                response,
                filterChain);

        // Assert
        assertEquals(
                null,
                SecurityContextHolder
                        .getContext()
                        .getAuthentication());

        verify(filterChain)
                .doFilter(
                        request,
                        response);
    }

    @Test
    void doFilter_WhenAlreadyAuthenticated_ShouldNotReplaceAuthentication()
            throws ServletException, IOException {

        // Arrange
        Authentication existingAuthentication =
                org.mockito.Mockito.mock(
                        Authentication.class);

        SecurityContextHolder
                .getContext()
                .setAuthentication(
                        existingAuthentication);

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer valid-token");

        when(jwtUtil.extractUsername("valid-token"))
                .thenReturn("yaswanth");

        // Act
        jwtFilter.doFilter(
                request,
                response,
                filterChain);

        // Assert
        assertEquals(
                existingAuthentication,
                SecurityContextHolder
                        .getContext()
                        .getAuthentication());

        verify(filterChain)
                .doFilter(
                        request,
                        response);
    }
}