package com.wipro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AIServiceRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Prompt is required")
    @Size(max = 5000, message = "Prompt must not exceed 5000 characters")
    private String prompt;

    @NotBlank(message = "Request type is required")
    private String requestType;
}