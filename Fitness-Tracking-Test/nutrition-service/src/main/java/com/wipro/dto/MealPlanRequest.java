package com.wipro.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealPlanRequest {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be positive")
    private Long userId;

    @NotNull(message = "Age is required")
    @Min(value = 13, message = "Age must be at least 13")
    @Max(value = 100, message = "Age must not exceed 100")
    private Integer age;

    @NotNull(message = "Height is required")
    @DecimalMin(value = "100.0", message = "Height must be at least 100 cm")
    @DecimalMax(value = "250.0", message = "Height must not exceed 250 cm")
    private Double height;

    @NotNull(message = "Weight is required")
    @DecimalMin(value = "25.0", message = "Weight must be at least 25 kg")
    @DecimalMax(value = "300.0", message = "Weight must not exceed 300 kg")
    private Double weight;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Goal is required")
    private String goal;
}