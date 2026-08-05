package com.wipro.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wipro.dto.AuthResponse;
import com.wipro.dto.LoginRequest;
import com.wipro.dto.RegisterRequest;
import com.wipro.entity.Role;
import com.wipro.entity.User;
import com.wipro.exception.InvalidCredentialsException;
import com.wipro.exception.UserAlreadyExistsException;
import com.wipro.repository.UserRepository;
import com.wipro.security.CustomUserDetailsService;
import com.wipro.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService userDetailsService;

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {

            throw new UserAlreadyExistsException(
                    "Email already registered");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String token =
                jwtUtil.generateToken(
                        user.getEmail());

        return AuthResponse.builder()
                .token(token)
                .message("User Registered Successfully")
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));

        } catch (Exception e) {

            throw new InvalidCredentialsException(
                    "Invalid Email or Password");
        }

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(
                        request.getEmail());

        String token =
                jwtUtil.generateToken(
                        userDetails.getUsername());

        return AuthResponse.builder()
                .token(token)
                .message("Login Successful")
                .build();
    }
}