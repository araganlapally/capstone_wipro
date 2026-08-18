package com.wipro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AIServiceRequest {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be greater than 0")
    private Long userId;

    @NotBlank(message = "Prompt is required")
    private String prompt;

    @NotBlank(message = "Request type is required")
    private String requestType;
}