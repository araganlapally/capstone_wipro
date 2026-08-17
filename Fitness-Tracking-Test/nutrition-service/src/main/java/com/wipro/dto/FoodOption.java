package com.wipro.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FoodOption {

    @NotBlank(message = "Food name is required")
    private String food;

    @NotBlank(message = "Quantity is required")
    private String quantity;

    @NotNull(message = "Calories are required")
    @DecimalMin(value = "0.0", message = "Calories cannot be negative")
    private Double calories;

    @NotNull(message = "Protein is required")
    @DecimalMin(value = "0.0", message = "Protein cannot be negative")
    private Double protein;

    @NotNull(message = "Carbohydrates are required")
    @DecimalMin(value = "0.0", message = "Carbohydrates cannot be negative")
    private Double carbs;

    @NotNull(message = "Fats are required")
    @DecimalMin(value = "0.0", message = "Fats cannot be negative")
    private Double fats;
}