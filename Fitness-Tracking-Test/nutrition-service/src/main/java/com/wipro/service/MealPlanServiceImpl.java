package com.wipro.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.dto.NutritionRecommendationResponse;
import com.wipro.dto.UserProfileResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MealPlanServiceImpl implements MealPlanService {

    private final UserServiceClient userServiceClient;
    private final AIService aiService;
    private final ObjectMapper objectMapper;

    @Override
    public NutritionRecommendationResponse generateMealPlan(Long userId) {

        try {

            // Get user profile from User Service
            UserProfileResponse profile =
                    userServiceClient.getProfile(userId);

            String prompt = String.format("""
                    You are a certified nutrition expert.

                    Create a personalized nutrition recommendation.

                    Age: %d
                    Height: %.1f cm
                    Weight: %.1f kg
                    Gender: %s
                    Goal: %s

                    Calculate:

                    1. Daily Calories Required
                    2. Daily Protein Required
                    3. Daily Carbohydrates Required
                    4. Daily Fats Required

                    Then suggest:

                    - 10 Vegetarian food options
                    - 10 Non-Vegetarian food options

                    For every food provide:

                    Food Name
                    Recommended Quantity
                    Calories
                    Protein
                    Carbohydrates
                    Fats

                    Return ONLY valid JSON.

                    {
                      "dailyCalories": 0,
                      "dailyProtein": 0,
                      "dailyCarbs": 0,
                      "dailyFats": 0,
                      "vegetarianFoods": [
                        {
                          "food": "",
                          "quantity": "",
                          "calories": 0,
                          "protein": 0,
                          "carbs": 0,
                          "fats": 0
                        }
                      ],
                      "nonVegetarianFoods": [
                        {
                          "food": "",
                          "quantity": "",
                          "calories": 0,
                          "protein": 0,
                          "carbs": 0,
                          "fats": 0
                        }
                      ]
                    }

                    Return JSON only.
                    """,
                    profile.getAge(),
                    profile.getHeight(),
                    profile.getWeight(),
                    profile.getGender(),
                    profile.getGoal());

            // Call AI Service for nutrition recommendation
            String aiResponse =
                    aiService.generateNutritionRecommendation(
                            userId,
                            prompt
                    );

            // Remove Markdown code fences if AI returns them
            aiResponse = aiResponse
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();

            System.out.println("=========== AI RESPONSE ===========");
            System.out.println(aiResponse);
            System.out.println("===================================");

            // Convert AI JSON response into DTO
            return objectMapper.readValue(
                    aiResponse,
                    NutritionRecommendationResponse.class
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Failed to generate AI nutrition recommendation",
                    e
            );
        }
    }
}