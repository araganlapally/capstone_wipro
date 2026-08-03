package com.wipro.dto;

import java.util.List;

import lombok.Data;

@Data
public class NutritionRecommendationResponse {

    private Integer dailyCalories;
    private Integer dailyProtein;
    private Integer dailyCarbs;
    private Integer dailyFats;

    private List<FoodOption> vegetarianFoods;
    private List<FoodOption> nonVegetarianFoods;
}