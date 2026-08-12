package com.wipro.dto;

import lombok.Data;

@Data
public class MealPlanRequest {

    private Long userId;

    private Integer age;
    private Double height;
    private Double weight;

    private String gender;
    private String goal;
}