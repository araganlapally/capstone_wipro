package com.wipro.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.dto.NutritionRecommendationResponse;
import com.wipro.dto.UserProfileResponse;
import com.wipro.repository.MealPlanRepository;
import com.wipro.entity.MealPlan;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MealPlanServiceImpl implements MealPlanService {

    private final UserServiceClient userServiceClient;
    private final GeminiService geminiService;
    private final ObjectMapper objectMapper;
    private final MealPlanRepository mealPlanRepository;

    @Override
    public NutritionRecommendationResponse generateMealPlan(Long userId) {
    	
    	System.out.println("MEAL PLAN GENERATION STARTED FOR USER = " + userId);

        try {

            UserProfileResponse profile =
                    userServiceClient.getProfile(userId);
            
            MealPlan existingPlan =
                    mealPlanRepository
                            .findTopByUserIdOrderByIdDesc(userId)
                            .orElse(null);
            
            System.out.println("EXISTING PLAN = " + existingPlan);
            
            System.out.println("PROFILE GOAL = " + profile.getGoal());

            if(existingPlan != null) {
                System.out.println("DB GOAL = " + existingPlan.getGoal());
            }

            if (existingPlan != null
                    && existingPlan.getNutritionJson() != null
                    && profile.getGoal().equals(existingPlan.getGoal())) {
            	
            	System.out.println("ENTERED CACHE BLOCK");

                System.out.println("SAVED NUTRITION PLAN FOUND FOR USER = " + userId);

                return objectMapper.readValue(
                        existingPlan.getNutritionJson(),
                        NutritionRecommendationResponse.class);
            }

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
                      "dailyCalories":0,
                      "dailyProtein":0,
                      "dailyCarbs":0,
                      "dailyFats":0,
                      "vegetarianFoods":[
                        {
                          "food":"",
                          "quantity":"",
                          "calories":0,
                          "protein":0,
                          "carbs":0,
                          "fats":0
                        }
                      ],
                      "nonVegetarianFoods":[
                        {
                          "food":"",
                          "quantity":"",
                          "calories":0,
                          "protein":0,
                          "carbs":0,
                          "fats":0
                        }
                      ]
                    }

                    IMPORTANT RULES:
                    1. Return ONLY valid JSON.
                    2. Do NOT explain anything.
                    3. Do NOT calculate step by step.
                    4. Do NOT include text before JSON.
                    5. Do NOT include text after JSON.
                    6. Do NOT use markdown.
                    7. Do NOT use ```json.
                    8. All values must be valid JSON.
                    9. Return exactly this structure and nothing else:
                    {
                    "dailyCalories":0,
                    "dailyProtein":0,
                    "dailyCarbs":0,
                    "dailyFats":0,
                    "vegetarianFoods":[],
                    "nonVegetarianFoods":[]
                    }
                    10. quantity must ALWAYS be a string.
                    11. calories must ALWAYS be a number only.
                    12. protein must ALWAYS be a number only.
                    13. carbs must ALWAYS be a number only.14. fats must ALWAYS be a number only.
                    15. Do NOT add units like g, kg, ml, oz to calories, protein, carbs or fats.
                    16. Units are allowed ONLY inside quantity.
                    Correct Examples:
                    {
                    "food":"Spinach Salad",
                    "quantity":"200g",
                    "calories":20,
                    "protein":3,
                     "carbs":5,
                     "fats":0.5
}

{
  "food":"Chicken Breast",
  "quantity":"120g",
  "calories":250,
  "protein":35,
  "carbs":10,
  "fats":6
}

Wrong Examples:

{
  "quantity":200g,
  "protein":3g,
  "carbs":5g,
  "fats":0.5g
}
                    """,
                    profile.getAge(),
                    profile.getHeight(),
                    profile.getWeight(),
                    profile.getGender(),
                    profile.getGoal());
         

            String aiResponse =
                    geminiService.generateWorkout(prompt);
            
            System.out.println("OLLAMA RESPONSE RECEIVED");

            aiResponse = aiResponse
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();
            
            if (!aiResponse.trim().endsWith("}")) {
                throw new RuntimeException("Invalid JSON received from Ollama");
            }
            
            System.out.println("BEFORE OBJECT MAPPER");
            
            NutritionRecommendationResponse response =
                    objectMapper.readValue(
                            aiResponse,
                            NutritionRecommendationResponse.class);
            
            System.out.println("AFTER OBJECT MAPPER");

            MealPlan plan =
                    existingPlan != null
                            ? existingPlan
                            : new MealPlan();

            plan.setUserId(userId);
            plan.setMealName("AI Generated Nutrition Plan");
            plan.setGoal(profile.getGoal());
            plan.setNutritionJson(aiResponse);

            System.out.println("BEFORE SAVE");

            mealPlanRepository.save(plan);

            System.out.println("MEAL PLAN SAVED");

            System.out.println("=========== AI RESPONSE ===========");
            System.out.println(aiResponse);
            System.out.println("===================================");

            return response;
        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Failed to generate AI nutrition recommendation",
                    e);
        }
    }
}