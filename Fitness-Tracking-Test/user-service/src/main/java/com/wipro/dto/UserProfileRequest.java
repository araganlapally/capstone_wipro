package com.wipro.dto;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserProfileRequest {

    @NotNull(message = "Age is required")
    @Min(value = 1, message = "Age must be greater than 0")
    private Integer age;

    @NotNull(message = "Height is required")
    private Double height;

    @NotNull(message = "Weight is required")
    private Double weight;

    @NotBlank(message = "Goal is required")
    private String goal;

    @NotBlank(message = "Gender is required")
    private String gender;
}
