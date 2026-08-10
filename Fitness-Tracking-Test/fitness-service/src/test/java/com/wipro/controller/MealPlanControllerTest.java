package com.wipro.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.wipro.dto.FoodOption;
import com.wipro.dto.NutritionRecommendationResponse;
import com.wipro.security.JwtFilter;
import com.wipro.service.MealPlanService;



@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class MealPlanControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private MealPlanService mealPlanService;


    @MockitoBean
    private JwtFilter jwtFilter;



    @Test
    void generateMealPlan_ShouldReturnNutritionPlan()
            throws Exception {


        FoodOption food =
                new FoodOption();

        food.setFood("Chicken Breast");
        food.setQuantity("200 grams");
        food.setCalories(330.0);
        food.setProtein(62.0);
        food.setCarbs(0.0);
        food.setFats(7.0);



        NutritionRecommendationResponse response =
                new NutritionRecommendationResponse();


        response.setDailyCalories(2200);
        response.setDailyProtein(150);
        response.setDailyCarbs(250);
        response.setDailyFats(70);

        response.setNonVegetarianFoods(
                List.of(food)
        );



        when(mealPlanService.generateMealPlan(1L))
                .thenReturn(response);



        mockMvc.perform(
                post("/api/meals/generate/1")
        )
        .andExpect(status().isOk())

        .andExpect(
                jsonPath("$.dailyCalories")
                .value(2200)
        )

        .andExpect(
                jsonPath("$.dailyProtein")
                .value(150)
        )

        .andExpect(
                jsonPath("$.nonVegetarianFoods[0].food")
                .value("Chicken Breast")
        );
    }

}