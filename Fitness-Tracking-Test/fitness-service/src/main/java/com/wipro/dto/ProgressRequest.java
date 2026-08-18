package com.wipro.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import lombok.Data;

@Data
public class ProgressRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Weight is required")
    @DecimalMin(value = "20.0", message = "Weight must be at least 20 kg")
    @DecimalMax(value = "300.0", message = "Weight must not exceed 300 kg")
    private Double weight;

    @DecimalMin(value = "0.0", message = "Body fat cannot be negative")
    @DecimalMax(value = "100.0", message = "Body fat cannot exceed 100%")
    private Double bodyFat;

    @NotNull(message = "Recorded date is required")
    @PastOrPresent(message = "Recorded date cannot be in the future")
    private LocalDate recordedDate;
}