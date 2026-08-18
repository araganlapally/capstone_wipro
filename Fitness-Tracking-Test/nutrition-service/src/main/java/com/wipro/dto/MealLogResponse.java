package com.wipro.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class MealLogResponse {

    private Long id;

    private Long userId;

    private LocalDate date;

    private String mealType;

    private String foodName;

    private Integer quantity;

    private Integer calories;

    private Integer protein;

    private Integer carbs;

    private Integer fats;
}