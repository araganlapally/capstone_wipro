package com.wipro.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Data;

@Data
public class HydrationRequest {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be positive")
    private Long userId;

    @NotNull(message = "Water intake is required")
    @Positive(message = "Water intake must be greater than 0")
    @Max(value = 10000, message = "Water intake cannot exceed 10000 ml")
    private Integer waterIntake;

    @NotNull(message = "Water goal is required")
    @Positive(message = "Water goal must be greater than 0")
    @Max(value = 10000, message = "Water goal cannot exceed 10000 ml")
    private Integer waterGoal;
}