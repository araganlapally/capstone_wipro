package com.wipro.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class WorkoutPlanRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Plan name is required")
    private String planName;

    @NotBlank(message = "Goal is required")
    private String goal;

    @NotNull(message = "Duration in weeks is required")
    @Min(value = 1, message = "Duration must be at least 1 week")
    @Max(value = 52, message = "Duration cannot exceed 52 weeks")
    private Integer durationWeeks;
}