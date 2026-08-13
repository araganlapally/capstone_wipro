package com.wipro.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.wipro.dto.NutritionRecommendationResponse;
import com.wipro.service.MealPlanService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class MealPlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MealPlanService mealPlanService;

    @Test
    void generateMealPlan_ShouldReturnNutritionRecommendation()
            throws Exception {

        // Arrange
        NutritionRecommendationResponse response =
                new NutritionRecommendationResponse();

        response.setDailyCalories(2200);
        response.setDailyProtein(150);
        response.setDailyCarbs(250);
        response.setDailyFats(70);

        when(mealPlanService.generateMealPlan(1L))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                post("/api/meals/generate/1")
        )
        .andExpect(status().isOk())
        .andExpect(
                jsonPath("$.dailyCalories")
                        .value(2200))
        .andExpect(
                jsonPath("$.dailyProtein")
                        .value(150))
        .andExpect(
                jsonPath("$.dailyCarbs")
                        .value(250))
        .andExpect(
                jsonPath("$.dailyFats")
                        .value(70));
    }
}