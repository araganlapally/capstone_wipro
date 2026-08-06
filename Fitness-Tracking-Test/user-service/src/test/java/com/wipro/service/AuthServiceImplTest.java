package com.wipro.service;

import com.wipro.dto.AuthResponse;
import com.wipro.dto.RegisterRequest;
import com.wipro.entity.User;
import com.wipro.exception.UserAlreadyExistsException;
import com.wipro.repository.UserRepository;
import com.wipro.security.CustomUserDetailsService;
import com.wipro.security.JwtUtil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void testRegisterSuccess() {

        RegisterRequest request = new RegisterRequest();
        request.setFullName("badri vishal");
        request.setEmail("badri@gmail.com");
        request.setPassword("badri123");

        when(userRepository.existsByEmail("badri@gmail.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("badri123"))
                .thenReturn("encodedPassword");

        when(jwtUtil.generateToken("badri@gmail.com"))
                .thenReturn("jwt-token");

        AuthResponse response =
                authService.register(request);

        assertNotNull(response);
        assertEquals("jwt-token",
                response.getToken());

        verify(userRepository, times(1))
                .save(any(User.class));
    }
    @Test
    void testRegisterEmailAlreadyExists() {

        RegisterRequest request = new RegisterRequest();
        request.setFullName("badri vishal");
        request.setEmail("badri@gmail.com");
        request.setPassword("badri123");

        when(userRepository.existsByEmail("badri@gmail.com"))
                .thenReturn(true);

        assertThrows(
                UserAlreadyExistsException.class,
                () -> authService.register(request)
        );
    }
}