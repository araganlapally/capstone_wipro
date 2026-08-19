package com.wipro.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {

        jwtUtil = new JwtUtil();

        ReflectionTestUtils.setField(
                jwtUtil,
                "secret",
                "mysecretkeymysecretkeymysecretkey12345"
        );

        ReflectionTestUtils.setField(
                jwtUtil,
                "expiration",
                3600000L
        );
    }

    @Test
    void generateToken_Success() {

        String token =
                jwtUtil.generateToken(
                        "test@example.com"
                );

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void extractUsername_Success() {

        String username =
                "test@example.com";

        String token =
                jwtUtil.generateToken(username);

        String extractedUsername =
                jwtUtil.extractUsername(token);

        assertEquals(
                username,
                extractedUsername
        );
    }

    @Test
    void validateToken_Valid() {

        String username =
                "test@example.com";

        String token =
                jwtUtil.generateToken(username);

        boolean result =
                jwtUtil.validateToken(
                        token,
                        username
                );

        assertTrue(result);
    }

    @Test
    void validateToken_InvalidUsername() {

        String token =
                jwtUtil.generateToken(
                        "test@example.com"
                );

        boolean result =
                jwtUtil.validateToken(
                        token,
                        "wrong@example.com"
                );

        assertFalse(result);
    }

    @Test
    void validateToken_InvalidToken() {

        boolean result =
                jwtUtil.validateToken(
                        "invalid.token.value",
                        "test@example.com"
                );

        assertFalse(result);
    }
}