package com.wipro.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.dto.NutritionRecommendationResponse;
import com.wipro.dto.UserProfileResponse;

import com.wipro.repository.MealPlanRepository;



class MealPlanServiceImplTest {



    @Mock
    private UserServiceClient userServiceClient;


    @Mock
    private GeminiService geminiService;



    private MealPlanServiceImpl mealPlanService;



    private ObjectMapper objectMapper;
    
    
    @Mock
    private MealPlanRepository mealPlanRepository;





    @BeforeEach
    void setup() {


        MockitoAnnotations.openMocks(this);


        objectMapper =
                new ObjectMapper();



        mealPlanService =
                new MealPlanServiceImpl(
                        userServiceClient,
                        geminiService,
                        objectMapper,
                        mealPlanRepository
                );
    }







    @Test
    void generateMealPlan_ShouldReturnNutritionResponse()
            throws Exception {



        UserProfileResponse profile =
                new UserProfileResponse();



        profile.setAge(25);
        profile.setHeight(175.0);
        profile.setWeight(70.0);
        profile.setGender("Male");
        profile.setGoal("Muscle Gain");



        when(userServiceClient
                .getProfile(1L))
                .thenReturn(profile);





        String aiResponse =
                """
                {
                  "dailyCalories":2500,
                  "dailyProtein":150,
                  "dailyCarbs":300,
                  "dailyFats":70,
                  "vegetarianFoods":[],
                  "nonVegetarianFoods":[]
                }
                """;



        when(geminiService
                .generateWorkout(
                        org.mockito.ArgumentMatchers.anyString()
                ))
                .thenReturn(aiResponse);





        NutritionRecommendationResponse response =
                mealPlanService
                .generateMealPlan(1L);




        assertEquals(
                2500,
                response.getDailyCalories()
        );


        assertEquals(
                150,
                response.getDailyProtein()
        );
    }








    @Test
    void generateMealPlan_WhenGeminiFails_ShouldThrowException() {



        when(userServiceClient
                .getProfile(1L))
                .thenThrow(
                        new RuntimeException(
                                "User service unavailable"
                        )
                );



        assertThrows(
                RuntimeException.class,
                () ->
                mealPlanService
                .generateMealPlan(1L)
        );
    }

}