package com.wipro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class AIServiceImplTest {

    @Mock
    private AIServiceClient aiServiceClient;

    @InjectMocks
    private AIServiceImpl aiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateNutritionRecommendation_ShouldReturnAIResponse() {

        // Arrange
        Long userId = 1L;

        String prompt =
                "Create a nutrition plan for weight loss.";

        String expectedResponse =
                """
                {
                  "dailyCalories": 2200,
                  "dailyProtein": 150,
                  "dailyCarbs": 250,
                  "dailyFats": 70
                }
                """;

        when(aiServiceClient.generateNutrition(
                argThat(request ->
                        request.getUserId().equals(userId)
                        && request.getPrompt().equals(prompt)
                        && request.getRequestType()
                                .equals("NUTRITION"))))
                .thenReturn(expectedResponse);

        // Act
        String result =
                aiService.generateNutritionRecommendation(
                        userId,
                        prompt);

        // Assert
        assertEquals(
                expectedResponse,
                result);

        verify(aiServiceClient)
                .generateNutrition(
                        argThat(request ->
                                request.getUserId()
                                        .equals(userId)
                                && request.getPrompt()
                                        .equals(prompt)
                                && request.getRequestType()
                                        .equals("NUTRITION")));
    }

    @Test
    void generateNutritionRecommendation_WhenAIServiceFails_ShouldThrowException() {

        // Arrange
        Long userId = 1L;

        String prompt =
                "Create a nutrition plan.";

        when(aiServiceClient.generateNutrition(
                argThat(request ->
                        request.getUserId().equals(userId)
                        && request.getPrompt().equals(prompt)
                        && request.getRequestType()
                                .equals("NUTRITION"))))
                .thenThrow(
                        new RuntimeException(
                                "AI service unavailable"));

        // Act + Assert
        RuntimeException exception =
                org.junit.jupiter.api.Assertions.assertThrows(
                        RuntimeException.class,
                        () -> aiService
                                .generateNutritionRecommendation(
                                        userId,
                                        prompt));

        assertEquals(
                "AI service unavailable",
                exception.getMessage());
    }
}