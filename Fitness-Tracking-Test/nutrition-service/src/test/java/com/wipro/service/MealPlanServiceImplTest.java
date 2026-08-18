package com.wipro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.dto.NutritionRecommendationResponse;
import com.wipro.dto.UserProfileResponse;

class MealPlanServiceImplTest {

    @Mock
    private UserServiceClient userServiceClient;

    @Mock
    private AIService aiService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private MealPlanServiceImpl mealPlanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateMealPlan_ShouldReturnNutritionRecommendation()
            throws Exception {

        // Arrange
        UserProfileResponse profile =
                new UserProfileResponse();

        profile.setId(1L);
        profile.setAge(25);
        profile.setHeight(175.0);
        profile.setWeight(80.0);
        profile.setGender("Male");
        profile.setGoal("Weight Loss");

        String aiJson = """
                {
                  "dailyCalories": 2200,
                  "dailyProtein": 150,
                  "dailyCarbs": 250,
                  "dailyFats": 70,
                  "vegetarianFoods": [],
                  "nonVegetarianFoods": []
                }
                """;

        NutritionRecommendationResponse expectedResponse =
                new NutritionRecommendationResponse();

        expectedResponse.setDailyCalories(2200);
        expectedResponse.setDailyProtein(150);
        expectedResponse.setDailyCarbs(250);
        expectedResponse.setDailyFats(70);

        when(userServiceClient.getProfile(1L))
                .thenReturn(profile);

        when(aiService.generateNutritionRecommendation(
                eq(1L),
                anyString()))
                .thenReturn(aiJson);

        when(objectMapper.readValue(
                anyString(),
                eq(NutritionRecommendationResponse.class)))
                .thenReturn(expectedResponse);

        // Act
        NutritionRecommendationResponse result =
                mealPlanService.generateMealPlan(1L);

        // Assert
        assertEquals(
                2200,
                result.getDailyCalories());

        assertEquals(
                150,
                result.getDailyProtein());

        assertEquals(
                250,
                result.getDailyCarbs());

        assertEquals(
                70,
                result.getDailyFats());

        verify(userServiceClient)
                .getProfile(1L);

        verify(aiService)
                .generateNutritionRecommendation(
                        eq(1L),
                        anyString());

        verify(objectMapper)
                .readValue(
                        anyString(),
                        eq(NutritionRecommendationResponse.class));
    }

    @Test
    void generateMealPlan_ShouldRemoveMarkdownCodeFences()
            throws Exception {

        // Arrange
        UserProfileResponse profile =
                new UserProfileResponse();

        profile.setAge(25);
        profile.setHeight(175.0);
        profile.setWeight(80.0);
        profile.setGender("Male");
        profile.setGoal("Weight Loss");

        String aiResponse = """
                ```json
                {
                  "dailyCalories": 2000,
                  "dailyProtein": 140,
                  "dailyCarbs": 220,
                  "dailyFats": 65,
                  "vegetarianFoods": [],
                  "nonVegetarianFoods": []
                }
                ```
                """;

        NutritionRecommendationResponse expectedResponse =
                new NutritionRecommendationResponse();

        expectedResponse.setDailyCalories(2000);

        when(userServiceClient.getProfile(1L))
                .thenReturn(profile);

        when(aiService.generateNutritionRecommendation(
                eq(1L),
                anyString()))
                .thenReturn(aiResponse);

        when(objectMapper.readValue(
                anyString(),
                eq(NutritionRecommendationResponse.class)))
                .thenReturn(expectedResponse);

        // Act
        NutritionRecommendationResponse result =
                mealPlanService.generateMealPlan(1L);

        // Assert
        assertEquals(
                2000,
                result.getDailyCalories());

        verify(objectMapper)
                .readValue(
                        eq("""
                        {
                          "dailyCalories": 2000,
                          "dailyProtein": 140,
                          "dailyCarbs": 220,
                          "dailyFats": 65,
                          "vegetarianFoods": [],
                          "nonVegetarianFoods": []
                        }
                        """.trim()),
                        eq(NutritionRecommendationResponse.class));
    }

    @Test
    void generateMealPlan_WhenUserServiceFails_ShouldThrowException() {

        // Arrange
        when(userServiceClient.getProfile(1L))
                .thenThrow(
                        new RuntimeException(
                                "User service unavailable"));

        // Act + Assert
        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> mealPlanService
                                .generateMealPlan(1L));

        assertEquals(
                "Failed to generate AI nutrition recommendation",
                exception.getMessage());
    }

    @Test
    void generateMealPlan_WhenAIServiceFails_ShouldThrowException() {

        // Arrange
        UserProfileResponse profile =
                new UserProfileResponse();

        profile.setAge(25);
        profile.setHeight(175.0);
        profile.setWeight(80.0);
        profile.setGender("Male");
        profile.setGoal("Weight Loss");

        when(userServiceClient.getProfile(1L))
                .thenReturn(profile);

        when(aiService.generateNutritionRecommendation(
                eq(1L),
                anyString()))
                .thenThrow(
                        new RuntimeException(
                                "AI service unavailable"));

        // Act + Assert
        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> mealPlanService
                                .generateMealPlan(1L));

        assertEquals(
                "Failed to generate AI nutrition recommendation",
                exception.getMessage());
    }
}