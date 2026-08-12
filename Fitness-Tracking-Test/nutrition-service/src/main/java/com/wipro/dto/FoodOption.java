package com.wipro.dto;

import lombok.Data;

@Data
public class FoodOption {

    private String food;
    private String quantity;

    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fats;
}