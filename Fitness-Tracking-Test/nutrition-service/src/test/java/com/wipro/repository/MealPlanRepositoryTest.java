package com.wipro.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.wipro.entity.MealPlan;

@DataJpaTest
class MealPlanRepositoryTest {

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Test
    void findByUserId_ShouldReturnMealPlans() {

        // Arrange
        MealPlan mealPlan1 = new MealPlan();

        mealPlan1.setUserId(1L);
        mealPlan1.setMealName("Breakfast");
        mealPlan1.setCalories(400);
        mealPlan1.setProtein(20);
        mealPlan1.setCarbs(50);
        mealPlan1.setFats(10);

        MealPlan mealPlan2 = new MealPlan();

        mealPlan2.setUserId(1L);
        mealPlan2.setMealName("Lunch");
        mealPlan2.setCalories(600);
        mealPlan2.setProtein(35);
        mealPlan2.setCarbs(70);
        mealPlan2.setFats(15);

        mealPlanRepository.save(mealPlan1);
        mealPlanRepository.save(mealPlan2);

        // Act
        List<MealPlan> result =
                mealPlanRepository.findByUserId(1L);

        // Assert
        assertNotNull(result);

        assertEquals(
                2,
                result.size());

        assertEquals(
                "Breakfast",
                result.get(0).getMealName());

        assertEquals(
                "Lunch",
                result.get(1).getMealName());
    }

    @Test
    void findByUserId_WhenNoMealPlans_ShouldReturnEmptyList() {

        // Act
        List<MealPlan> result =
                mealPlanRepository.findByUserId(999L);

        // Assert
        assertNotNull(result);

        assertEquals(
                0,
                result.size());
    }
}