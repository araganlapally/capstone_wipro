package com.wipro.security;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() throws Exception {

        jwtUtil = new JwtUtil();

        Field secretField =
                JwtUtil.class.getDeclaredField("secret");

        secretField.setAccessible(true);
        secretField.set(jwtUtil,
                "mysecretkeymysecretkeymysecretkey123456");

        Field expirationField =
                JwtUtil.class.getDeclaredField("expiration");

        expirationField.setAccessible(true);
        expirationField.set(jwtUtil, 86400000L);
    }

    @Test
    void generateToken_ShouldReturnToken() {

        String token =
                jwtUtil.generateToken("sai@test.com");

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void extractUsername_ShouldReturnUsername() {

        String token =
                jwtUtil.generateToken("sai@test.com");

        String username =
                jwtUtil.extractUsername(token);

        assertEquals("sai@test.com", username);
    }

    @Test
    void validateToken_WithCorrectUsername_ShouldReturnTrue() {

        String token =
                jwtUtil.generateToken("sai@test.com");

        assertTrue(
                jwtUtil.validateToken(
                        token,
                        "sai@test.com")
        );
    }

    @Test
    void validateToken_WithWrongUsername_ShouldReturnFalse() {

        String token =
                jwtUtil.generateToken("sai@test.com");

        assertFalse(
                jwtUtil.validateToken(
                        token,
                        "admin@test.com")
        );
    }

    @Test
    void extractUsername_InvalidToken_ShouldThrowException() {

        assertThrows(
                Exception.class,
                () -> jwtUtil.extractUsername("invalid.token")
        );
    }
}