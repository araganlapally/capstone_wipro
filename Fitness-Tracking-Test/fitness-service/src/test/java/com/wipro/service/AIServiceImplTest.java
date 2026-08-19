package com.wipro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class AIServiceImplTest {

    @Autowired
    private AIService aiService;

    @MockBean
    private RestTemplate restTemplate;


    @Test
    void generateWorkout_shouldReturnResponse() {

        // Arrange
        String prompt = "Create a workout plan for weight loss.";

        Map<String, Object> responseBody = Map.of(
                "response",
                "Follow a calorie deficit and exercise regularly."
        );

        ResponseEntity<Map> response =
                ResponseEntity.ok(responseBody);

        when(restTemplate.exchange(
                eq("http://localhost:11434/api/generate"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(Map.class)
        )).thenReturn(response);

        // Act
        String result =
                aiService.generateWorkout(prompt);

        // Assert
        assertEquals(
                "Follow a calorie deficit and exercise regularly.",
                result
        );

        verify(restTemplate).exchange(
                eq("http://localhost:11434/api/generate"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(Map.class)
        );
    }


    @Test
    void generateWorkout_whenRestTemplateFails_shouldThrowException() {

        // Arrange
        when(restTemplate.exchange(
                eq("http://localhost:11434/api/generate"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(Map.class)
        )).thenThrow(
                new RuntimeException("Connection refused")
        );

        // Act + Assert
        assertThrows(
                RuntimeException.class,
                () -> aiService.generateWorkout(
                        "Create a workout plan"
                )
        );
    }
}