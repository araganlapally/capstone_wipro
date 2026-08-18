package com.wipro.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() throws Exception {

        jwtUtil = new JwtUtil();

        // Set private fields normally populated by @Value
        setField(
                jwtUtil,
                "secret",
                "mysecretkeymysecretkeymysecretkey123456789");

        setField(
                jwtUtil,
                "expiration",
                3600000L);
    }

    private void setField(
            Object object,
            String fieldName,
            Object value) throws Exception {

        Field field =
                object.getClass()
                        .getDeclaredField(fieldName);

        field.setAccessible(true);
        field.set(object, value);
    }

    @Test
    void generateToken_ShouldGenerateToken() {

        // Act
        String token =
                jwtUtil.generateToken("yaswanth");

        // Assert
        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void extractUsername_ShouldReturnUsername() {

        // Arrange
        String token =
                jwtUtil.generateToken("yaswanth");

        // Act
        String username =
                jwtUtil.extractUsername(token);

        // Assert
        assertEquals(
                "yaswanth",
                username);
    }

    @Test
    void validateToken_WithCorrectUsername_ShouldReturnTrue() {

        // Arrange
        String token =
                jwtUtil.generateToken("yaswanth");

        // Act
        boolean result =
                jwtUtil.validateToken(
                        token,
                        "yaswanth");

        // Assert
        assertTrue(result);
    }

    @Test
    void validateToken_WithWrongUsername_ShouldReturnFalse() {

        // Arrange
        String token =
                jwtUtil.generateToken("yaswanth");

        // Act
        boolean result =
                jwtUtil.validateToken(
                        token,
                        "otherUser");

        // Assert
        assertFalse(result);
    }

    @Test
    void validateToken_WithInvalidToken_ShouldReturnFalse() {

        // Act
        boolean result =
                jwtUtil.validateToken(
                        "invalid.jwt.token",
                        "yaswanth");

        // Assert
        assertFalse(result);
    }
}