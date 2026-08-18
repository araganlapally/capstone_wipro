package com.wipro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class AIChatRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Question is required")
    @Size(
        min = 2,
        max = 1000,
        message = "Question must be between 2 and 1000 characters"
    )
    private String question;
}