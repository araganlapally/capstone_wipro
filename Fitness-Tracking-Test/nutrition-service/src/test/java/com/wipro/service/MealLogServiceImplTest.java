package com.wipro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wipro.dto.MealLogRequest;
import com.wipro.dto.MealLogResponse;
import com.wipro.entity.MealLog;
import com.wipro.exception.ResourceNotFoundException;
import com.wipro.repository.MealLogRepository;

@ExtendWith(MockitoExtension.class)
class MealLogServiceImplTest {

    @Mock
    private MealLogRepository mealLogRepository;

    @InjectMocks
    private MealLogServiceImpl mealLogService;

    @Test
    void addMeal_ShouldSaveAndReturnMeal() {

        Long userId = 1L;

        MealLogRequest request =
                new MealLogRequest();

        request.setUserId(userId);
        request.setMealType("BREAKFAST");
        request.setFoodName("Oats");
        request.setQuantity(100);
        request.setCalories(389);
        request.setProtein(17);
        request.setCarbs(66);
        request.setFats(7);

        MealLog savedMeal =
                new MealLog();

        savedMeal.setId(1L);
        savedMeal.setUserId(userId);
        savedMeal.setLogDate(LocalDate.now());
        savedMeal.setMealType("BREAKFAST");
        savedMeal.setFoodName("Oats");
        savedMeal.setQuantity(100);
        savedMeal.setCalories(389);
        savedMeal.setProtein(17);
        savedMeal.setCarbs(66);
        savedMeal.setFats(7);

        when(mealLogRepository
                .save(any(MealLog.class)))
                .thenReturn(savedMeal);

        MealLogResponse result =
                mealLogService.addMeal(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(userId, result.getUserId());
        assertEquals(
                LocalDate.now(),
                result.getDate());
        assertEquals(
                "BREAKFAST",
                result.getMealType());
        assertEquals(
                "Oats",
                result.getFoodName());
        assertEquals(
                100,
                result.getQuantity());
        assertEquals(
                389,
                result.getCalories());
        assertEquals(
                17,
                result.getProtein());
        assertEquals(
                66,
                result.getCarbs());
        assertEquals(
                7,
                result.getFats());

        verify(mealLogRepository, times(1))
                .save(any(MealLog.class));
    }

    @Test
    void getTodayMeals_ShouldReturnMeals() {

        Long userId = 1L;
        LocalDate today = LocalDate.now();

        MealLog meal =
                createMeal(
                        1L,
                        today,
                        "BREAKFAST",
                        "Oats");

        when(mealLogRepository
                .findByUserIdAndLogDate(
                        userId,
                        today))
                .thenReturn(
                        List.of(meal));

        List<MealLogResponse> result =
                mealLogService.getTodayMeals(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(
                "Oats",
                result.get(0).getFoodName());

        verify(mealLogRepository, times(1))
                .findByUserIdAndLogDate(
                        userId,
                        today);
    }

    @Test
    void getTodayMeals_ShouldReturnEmptyList_WhenNoMealsExist() {

        Long userId = 1L;
        LocalDate today = LocalDate.now();

        when(mealLogRepository
                .findByUserIdAndLogDate(
                        userId,
                        today))
                .thenReturn(
                        List.of());

        List<MealLogResponse> result =
                mealLogService.getTodayMeals(userId);

        assertNotNull(result);
        assertEquals(0, result.size());

        verify(mealLogRepository, times(1))
                .findByUserIdAndLogDate(
                        userId,
                        today);
    }

    @Test
    void updateMeal_ShouldUpdateExistingMeal() {

        Long mealId = 1L;

        MealLog existingMeal =
                createMeal(
                        1L,
                        LocalDate.now(),
                        "BREAKFAST",
                        "Oats");

        MealLogRequest request =
                new MealLogRequest();

        request.setUserId(1L);
        request.setMealType("LUNCH");
        request.setFoodName("Rice");
        request.setQuantity(200);
        request.setCalories(260);
        request.setProtein(5);
        request.setCarbs(57);
        request.setFats(1);

        when(mealLogRepository
                .findById(mealId))
                .thenReturn(
                        Optional.of(existingMeal));

        when(mealLogRepository
                .save(any(MealLog.class)))
                .thenAnswer(
                        invocation ->
                                invocation.getArgument(0));

        MealLogResponse result =
                mealLogService.updateMeal(
                        mealId,
                        request);

        assertNotNull(result);
        assertEquals(
                "LUNCH",
                result.getMealType());
        assertEquals(
                "Rice",
                result.getFoodName());
        assertEquals(
                200,
                result.getQuantity());
        assertEquals(
                260,
                result.getCalories());
        assertEquals(
                5,
                result.getProtein());
        assertEquals(
                57,
                result.getCarbs());
        assertEquals(
                1,
                result.getFats());

        verify(mealLogRepository, times(1))
                .findById(mealId);

        verify(mealLogRepository, times(1))
                .save(existingMeal);
    }

    @Test
    void updateMeal_ShouldPreserveOriginalDate() {

        Long mealId = 1L;

        LocalDate originalDate =
                LocalDate.of(2026, 8, 15);

        MealLog existingMeal =
                createMeal(
                        1L,
                        originalDate,
                        "BREAKFAST",
                        "Oats");

        MealLogRequest request =
                new MealLogRequest();

        request.setUserId(1L);
        request.setMealType("LUNCH");
        request.setFoodName("Rice");
        request.setQuantity(200);
        request.setCalories(260);
        request.setProtein(5);
        request.setCarbs(57);
        request.setFats(1);

        when(mealLogRepository
                .findById(mealId))
                .thenReturn(
                        Optional.of(existingMeal));

        when(mealLogRepository
                .save(any(MealLog.class)))
                .thenAnswer(
                        invocation ->
                                invocation.getArgument(0));

        MealLogResponse result =
                mealLogService.updateMeal(
                        mealId,
                        request);

        assertEquals(
                originalDate,
                result.getDate());

        verify(mealLogRepository, times(1))
                .save(existingMeal);
    }

    @Test
    void updateMeal_ShouldThrowException_WhenMealNotFound() {

        Long mealId = 999L;

        MealLogRequest request =
                new MealLogRequest();

        request.setUserId(1L);
        request.setMealType("LUNCH");
        request.setFoodName("Rice");
        request.setQuantity(200);
        request.setCalories(260);
        request.setProtein(5);
        request.setCarbs(57);
        request.setFats(1);

        when(mealLogRepository
                .findById(mealId))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> mealLogService.updateMeal(
                                mealId,
                                request));

        assertEquals(
                "Meal not found with id : " + mealId,
                exception.getMessage());

        verify(mealLogRepository, times(1))
                .findById(mealId);

        verify(mealLogRepository, never())
                .save(any(MealLog.class));
    }

    @Test
    void deleteMeal_ShouldDeleteExistingMeal() {

        Long mealId = 1L;

        MealLog meal =
                createMeal(
                        1L,
                        LocalDate.now(),
                        "BREAKFAST",
                        "Oats");

        meal.setId(mealId);

        when(mealLogRepository
                .findById(mealId))
                .thenReturn(
                        Optional.of(meal));

        mealLogService.deleteMeal(mealId);

        verify(mealLogRepository, times(1))
                .findById(mealId);

        verify(mealLogRepository, times(1))
                .delete(meal);
    }

    @Test
    void deleteMeal_ShouldThrowException_WhenMealNotFound() {

        Long mealId = 999L;

        when(mealLogRepository
                .findById(mealId))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> mealLogService.deleteMeal(mealId));

        assertEquals(
                "Meal not found with id : " + mealId,
                exception.getMessage());

        verify(mealLogRepository, times(1))
                .findById(mealId);

        verify(mealLogRepository, never())
                .delete(any(MealLog.class));
    }

    private MealLog createMeal(
            Long userId,
            LocalDate logDate,
            String mealType,
            String foodName) {

        MealLog meal =
                new MealLog();

        meal.setId(1L);
        meal.setUserId(userId);
        meal.setLogDate(logDate);
        meal.setMealType(mealType);
        meal.setFoodName(foodName);
        meal.setQuantity(100);
        meal.setCalories(200);
        meal.setProtein(10);
        meal.setCarbs(30);
        meal.setFats(5);

        return meal;
    }
}