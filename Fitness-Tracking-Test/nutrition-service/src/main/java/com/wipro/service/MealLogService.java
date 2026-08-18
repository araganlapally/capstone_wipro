package com.wipro.service;

import java.util.List;

import com.wipro.dto.MealLogRequest;
import com.wipro.dto.MealLogResponse;

import jakarta.validation.Valid;

public interface MealLogService {

    MealLogResponse addMeal(
            MealLogRequest request);

    List<MealLogResponse> getTodayMeals(
            Long userId);

    void deleteMeal(Long id);

    MealLogResponse updateMeal(
            Long id,
            MealLogRequest request);
}