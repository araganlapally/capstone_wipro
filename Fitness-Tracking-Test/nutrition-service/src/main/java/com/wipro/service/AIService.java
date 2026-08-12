package com.wipro.service;

public interface AIService {

    String generateNutritionRecommendation(
            Long userId,
            String prompt
    );
}