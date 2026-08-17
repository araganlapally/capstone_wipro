package com.wipro.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import lombok.Data;

@Data
public class MealLogRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Meal type is required")
    private String mealType;

    @NotBlank(message = "Food name is required")
    private String foodName;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Calories are required")
    @PositiveOrZero(message = "Calories cannot be negative")
    private Integer calories;

    @NotNull(message = "Protein is required")
    @PositiveOrZero(message = "Protein cannot be negative")
    private Integer protein;

    @NotNull(message = "Carbohydrates are required")
    @PositiveOrZero(message = "Carbohydrates cannot be negative")
    private Integer carbs;

    @NotNull(message = "Fats are required")
    @PositiveOrZero(message = "Fats cannot be negative")
    private Integer fats;
}