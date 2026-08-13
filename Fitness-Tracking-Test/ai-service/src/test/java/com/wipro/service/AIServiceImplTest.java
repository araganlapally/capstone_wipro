package com.wipro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class AIServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AIServiceImpl aiService;

    @BeforeEach
    void setUp() {

        ReflectionTestUtils.setField(
                aiService,
                "ollamaUrl",
                "http://localhost:11434");

        ReflectionTestUtils.setField(
                aiService,
                "ollamaModel",
                "llama3");
    }

    @Test
    void generateResponse_shouldReturnAIResponse() {

        // Arrange
        String prompt =
                "Create a workout plan for weight loss.";

        Map<String, Object> responseBody =
                Map.of(
                        "response",
                        "Follow a calorie deficit and exercise regularly.");

        ResponseEntity<Map> response =
                ResponseEntity.ok(responseBody);

        when(restTemplate.exchange(
                eq("http://localhost:11434/api/generate"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(Map.class)))
                .thenReturn(response);

        // Act
        String result =
                aiService.generateResponse(prompt);

        // Assert
        assertEquals(
                "Follow a calorie deficit and exercise regularly.",
                result);

        verify(restTemplate).exchange(
                eq("http://localhost:11434/api/generate"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(Map.class));
    }

    @Test
    void generateResponse_whenResponseBodyIsNull_shouldThrowException() {

        // Arrange
        ResponseEntity<Map> response =
                ResponseEntity.ok(null);

        when(restTemplate.exchange(
                eq("http://localhost:11434/api/generate"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(Map.class)))
                .thenReturn(response);

        // Act + Assert
        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> aiService.generateResponse(
                                "Generate workout"));

        assertEquals(
                "Invalid response received from AI model",
                exception.getMessage());
    }

    @Test
    void generateResponse_whenResponseFieldMissing_shouldThrowException() {

        // Arrange
        Map<String, Object> responseBody =
                Map.of();

        ResponseEntity<Map> response =
                ResponseEntity.ok(responseBody);

        when(restTemplate.exchange(
                eq("http://localhost:11434/api/generate"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(Map.class)))
                .thenReturn(response);

        // Act + Assert
        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> aiService.generateResponse(
                                "Generate nutrition plan"));

        assertEquals(
                "Invalid response received from AI model",
                exception.getMessage());
    }

    @Test
    void generateResponse_whenRestTemplateFails_shouldThrowException() {

        // Arrange
        when(restTemplate.exchange(
                eq("http://localhost:11434/api/generate"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(Map.class)))
                .thenThrow(
                        new RuntimeException(
                                "Connection refused"));

        // Act + Assert
        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> aiService.generateResponse(
                                "Generate workout"));

        assertEquals(
                "Connection refused",
                exception.getMessage());
    }
}