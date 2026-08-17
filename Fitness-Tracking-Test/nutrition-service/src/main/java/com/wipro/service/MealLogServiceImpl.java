package com.wipro.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wipro.dto.MealLogRequest;
import com.wipro.dto.MealLogResponse;
import com.wipro.entity.MealLog;
import com.wipro.exception.ResourceNotFoundException;
import com.wipro.repository.MealLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MealLogServiceImpl
        implements MealLogService {

    private final MealLogRepository mealLogRepository;

    @Override
    public MealLogResponse addMeal(
            MealLogRequest request) {

        MealLog meal = new MealLog();

        meal.setUserId(request.getUserId());
        meal.setLogDate(LocalDate.now());
        meal.setMealType(request.getMealType());
        meal.setFoodName(request.getFoodName());
        meal.setQuantity(request.getQuantity());
        meal.setCalories(request.getCalories());
        meal.setProtein(request.getProtein());
        meal.setCarbs(request.getCarbs());
        meal.setFats(request.getFats());

        MealLog saved =
                mealLogRepository.save(meal);

        return mapToResponse(saved);
    }

    @Override
    public List<MealLogResponse> getTodayMeals(
            Long userId) {

        return mealLogRepository
                .findByUserIdAndLogDate(
                        userId,
                        LocalDate.now())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public MealLogResponse updateMeal(
            Long id,
            MealLogRequest request) {

        MealLog mealLog =
                mealLogRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Meal not found with id : "
                                                + id));

        mealLog.setUserId(
                request.getUserId());

        mealLog.setMealType(
                request.getMealType());

        mealLog.setFoodName(
                request.getFoodName());

        mealLog.setQuantity(
                request.getQuantity());

        mealLog.setCalories(
                request.getCalories());

        mealLog.setProtein(
                request.getProtein());

        mealLog.setCarbs(
                request.getCarbs());

        mealLog.setFats(
                request.getFats());

        /*
         * Date is intentionally not updated.
         * The original logged date is preserved.
         */

        MealLog updatedMeal =
                mealLogRepository.save(mealLog);

        return mapToResponse(updatedMeal);
    }

    @Override
    public void deleteMeal(Long id) {

        MealLog meal =
                mealLogRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Meal not found with id : "
                                                + id));

        mealLogRepository.delete(meal);
    }

    private MealLogResponse mapToResponse(
            MealLog meal) {

        MealLogResponse response =
                new MealLogResponse();

        response.setId(meal.getId());
        response.setUserId(meal.getUserId());
        response.setDate(meal.getLogDate());
        response.setMealType(meal.getMealType());
        response.setFoodName(meal.getFoodName());
        response.setQuantity(meal.getQuantity());
        response.setCalories(meal.getCalories());
        response.setProtein(meal.getProtein());
        response.setCarbs(meal.getCarbs());
        response.setFats(meal.getFats());

        return response;
    }
}