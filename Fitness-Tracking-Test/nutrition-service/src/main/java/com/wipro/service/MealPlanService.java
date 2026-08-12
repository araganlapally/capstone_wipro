package com.wipro.service;

import java.util.List;

import com.wipro.dto.MealPlanResponse;
import com.wipro.dto.NutritionRecommendationResponse;

public interface MealPlanService {

	NutritionRecommendationResponse generateMealPlan(Long userId);
	
    
}