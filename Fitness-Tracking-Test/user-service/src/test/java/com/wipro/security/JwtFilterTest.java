package com.wipro.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtFilter jwtFilter;

    @BeforeEach
    void setup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_ValidToken() throws Exception {

        String token = "validToken";
        String username = "test@example.com";

        UserDetails userDetails =
                User.withUsername(username)
                        .password("password")
                        .authorities("USER")
                        .build();

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer " + token);

        when(jwtUtil.extractUsername(token))
                .thenReturn(username);

        when(userDetailsService.loadUserByUsername(username))
                .thenReturn(userDetails);

        when(jwtUtil.validateToken(token, username))
                .thenReturn(true);

        jwtFilter.doFilterInternal(
                request,
                response,
                filterChain
        );

        verify(filterChain, times(1))
                .doFilter(request, response);
    }

    @Test
    void doFilterInternal_NoAuthorizationHeader()
            throws Exception {

        when(request.getHeader("Authorization"))
                .thenReturn(null);

        jwtFilter.doFilterInternal(
                request,
                response,
                filterChain
        );

        verify(filterChain, times(1))
                .doFilter(request, response);
    }
}