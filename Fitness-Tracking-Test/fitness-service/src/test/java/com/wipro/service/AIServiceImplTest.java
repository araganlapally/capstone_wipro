package com.wipro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wipro.dto.AIServiceRequest;
import com.wipro.dto.AIWorkoutResponse;

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
    void generateWorkout_ShouldReturnAIWorkoutResponse() {

        // Arrange
        AIServiceRequest request =
                new AIServiceRequest(
                        1L,
                        "Create a workout plan for weight loss",
                        "WORKOUT"
                );

        AIWorkoutResponse expectedResponse =
                new AIWorkoutResponse(
                        "7-day workout plan for weight loss"
                );

        when(aiServiceClient.generateWorkout(request))
                .thenReturn(expectedResponse);

        // Act
        AIWorkoutResponse actualResponse =
                aiService.generateWorkout(request);

        // Assert
        assertNotNull(actualResponse);

        assertEquals(
                "7-day workout plan for weight loss",
                actualResponse.getWorkoutPlan()
        );

        // Verify AI Service Client was called
        verify(aiServiceClient)
                .generateWorkout(request);
    }

    @Test
    void generateWorkout_ShouldReturnSameResponseFromClient() {

        // Arrange
        AIServiceRequest request =
                new AIServiceRequest(
                        1L,
                        "Create workout plan",
                        "WORKOUT"
                );

        AIWorkoutResponse expectedResponse =
                new AIWorkoutResponse(
                        "AI generated workout"
                );

        when(aiServiceClient.generateWorkout(request))
                .thenReturn(expectedResponse);

        // Act
        AIWorkoutResponse result =
                aiService.generateWorkout(request);

        // Assert
        assertEquals(
                expectedResponse,
                result
        );

        verify(aiServiceClient)
                .generateWorkout(request);
    }
}