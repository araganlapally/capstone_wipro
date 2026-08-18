package com.wipro.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.wipro.entity.MealLog;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MealLogRepositoryTest {

    @Autowired
    private MealLogRepository mealLogRepository;

    @Test
    void findByUserIdAndLogDate_ShouldReturnMeals() {

        LocalDate date =
                LocalDate.of(2026, 8, 18);

        MealLog meal =
                new MealLog();

        meal.setUserId(1L);
        meal.setLogDate(date);
        meal.setMealType("BREAKFAST");
        meal.setFoodName("Oats");
        meal.setQuantity(100);
        meal.setCalories(389);
        meal.setProtein(17);
        meal.setCarbs(66);
        meal.setFats(7);

        mealLogRepository.save(meal);

        List<MealLog> result =
                mealLogRepository
                        .findByUserIdAndLogDate(
                                1L,
                                date);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(
                "Oats",
                result.get(0).getFoodName());
        assertEquals(
                1L,
                result.get(0).getUserId());
        assertEquals(
                date,
                result.get(0).getLogDate());
    }

    @Test
    void findByUserIdAndLogDate_ShouldReturnEmpty_WhenNoMealsExist() {

        List<MealLog> result =
                mealLogRepository
                        .findByUserIdAndLogDate(
                                999L,
                                LocalDate.of(2026, 8, 18));

        assertTrue(result.isEmpty());
    }

    @Test
    void findByUserIdOrderByLogDateDesc_ShouldReturnMealsInDescendingOrder() {

        LocalDate olderDate =
                LocalDate.of(2026, 8, 16);

        LocalDate newerDate =
                LocalDate.of(2026, 8, 18);

        MealLog olderMeal =
                createMeal(
                        1L,
                        olderDate,
                        "Breakfast",
                        "Idly");

        MealLog newerMeal =
                createMeal(
                        1L,
                        newerDate,
                        "Lunch",
                        "Rice");

        mealLogRepository.save(olderMeal);
        mealLogRepository.save(newerMeal);

        List<MealLog> result =
                mealLogRepository
                        .findByUserIdOrderByLogDateDesc(1L);

        assertEquals(2, result.size());

        assertEquals(
                newerDate,
                result.get(0).getLogDate());

        assertEquals(
                olderDate,
                result.get(1).getLogDate());
    }

    private MealLog createMeal(
            Long userId,
            LocalDate logDate,
            String mealType,
            String foodName) {

        MealLog meal =
                new MealLog();

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