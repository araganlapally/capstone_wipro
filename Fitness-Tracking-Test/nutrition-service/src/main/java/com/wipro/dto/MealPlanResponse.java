package com.wipro.dto;

import lombok.Data;

@Data
public class MealPlanResponse {

    private Long id;
    private Long userId;
    private String mealName;
    private Integer calories;
    private Integer protein;
    private Integer carbs;
    private Integer fats;
}