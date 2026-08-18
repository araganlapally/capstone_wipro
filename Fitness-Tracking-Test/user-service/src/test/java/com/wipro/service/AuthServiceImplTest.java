package com.wipro.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wipro.dto.AuthResponse;
import com.wipro.dto.RegisterRequest;
import com.wipro.entity.FitnessProfile;
import com.wipro.entity.User;
import com.wipro.repository.FitnessProfileRepository;
import com.wipro.repository.UserRepository;
import com.wipro.security.JwtUtil;

import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FitnessProfileRepository fitnessProfileRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void testRegisterSuccess() {

        RegisterRequest request = new RegisterRequest();

        request.setFullName("John Doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("password123");

        User user = User.builder()
                .id(1L)
                .fullName("John Doe")
                .email("john.doe@example.com")
                .password("encodedPassword")
                .role("USER")
                .build();

        FitnessProfile profile = FitnessProfile.builder()
                .id(1L)
                .age(25)
                .height(175.0)
                .weight(70.0)
                .goal("WEIGHT_LOSS")
                .gender("MALE")
                .user(user)
                .build();

        when(userRepository.existsByEmail(
                request.getEmail()))
                .thenReturn(false);

        when(passwordEncoder.encode(
                request.getPassword()))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        when(fitnessProfileRepository.save(
                any(FitnessProfile.class)))
                .thenReturn(profile);

        when(jwtUtil.generateToken(anyString()))
                .thenReturn("test-jwt-token");

        AuthResponse response =
                authService.register(request);

        assertNotNull(response);

        verify(userRepository)
                .save(any(User.class));

        verify(fitnessProfileRepository)
                .save(any(FitnessProfile.class));
    }
}