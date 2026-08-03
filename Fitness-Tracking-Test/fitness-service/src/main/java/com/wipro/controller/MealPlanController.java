package com.wipro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wipro.dto.NutritionRecommendationResponse;
import com.wipro.service.MealPlanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MealPlanController {

    private final MealPlanService mealPlanService;

    @PostMapping("/generate/{userId}")
    public ResponseEntity<NutritionRecommendationResponse>
    generateMealPlan(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                mealPlanService.generateMealPlan(userId));
    }
}