package com.wipro.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.wipro.entity.MealPlan;



@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MealPlanRepositoryTest {


    @Autowired
    private MealPlanRepository mealPlanRepository;



    @Test
    void saveMealPlan_ShouldReturnSavedMealPlan() {


        MealPlan mealPlan = new MealPlan();


        mealPlan.setUserId(1L);
        mealPlan.setMealName("High Protein Diet");
        mealPlan.setCalories(2200);
        mealPlan.setProtein(150);
        mealPlan.setCarbs(250);
        mealPlan.setFats(70);



        MealPlan saved =
                mealPlanRepository.save(mealPlan);



        assertEquals(
                "High Protein Diet",
                saved.getMealName()
        );


        assertEquals(
                2200,
                saved.getCalories()
        );
    }





    @Test
    void findById_ShouldReturnMealPlan() {


        MealPlan mealPlan =
                new MealPlan();


        mealPlan.setUserId(2L);
        mealPlan.setMealName("Weight Loss Diet");
        mealPlan.setCalories(1800);
        mealPlan.setProtein(120);
        mealPlan.setCarbs(180);
        mealPlan.setFats(50);



        MealPlan saved =
                mealPlanRepository.save(mealPlan);



        Optional<MealPlan> result =
                mealPlanRepository
                        .findById(saved.getId());



        assertTrue(
                result.isPresent()
        );


        assertEquals(
                "Weight Loss Diet",
                result.get().getMealName()
        );
    }





    @Test
    void findByUserId_ShouldReturnMealPlans() {


        MealPlan mealPlan1 =
                new MealPlan();


        mealPlan1.setUserId(10L);
        mealPlan1.setMealName("Breakfast Plan");
        mealPlan1.setCalories(500);
        mealPlan1.setProtein(30);
        mealPlan1.setCarbs(50);
        mealPlan1.setFats(15);



        MealPlan mealPlan2 =
                new MealPlan();


        mealPlan2.setUserId(10L);
        mealPlan2.setMealName("Dinner Plan");
        mealPlan2.setCalories(700);
        mealPlan2.setProtein(50);
        mealPlan2.setCarbs(80);
        mealPlan2.setFats(20);



        mealPlanRepository.save(mealPlan1);
        mealPlanRepository.save(mealPlan2);



        List<MealPlan> result =
                mealPlanRepository
                        .findByUserId(10L);



        assertEquals(
                2,
                result.size()
        );


        assertEquals(
                "Breakfast Plan",
                result.get(0).getMealName()
        );
    }





    @Test
    void findByUserId_WhenNoData_ShouldReturnEmptyList() {


        List<MealPlan> result =
                mealPlanRepository
                        .findByUserId(999L);



        assertTrue(
                result.isEmpty()
        );
    }





    @Test
    void deleteMealPlan_ShouldRemoveEntity() {


        MealPlan mealPlan =
                new MealPlan();


        mealPlan.setUserId(5L);
        mealPlan.setMealName("Delete Meal");
        mealPlan.setCalories(2000);
        mealPlan.setProtein(100);
        mealPlan.setCarbs(200);
        mealPlan.setFats(60);



        MealPlan saved =
                mealPlanRepository.save(mealPlan);



        mealPlanRepository.delete(saved);



        Optional<MealPlan> result =
                mealPlanRepository
                        .findById(saved.getId());



        assertFalse(
                result.isPresent()
        );
    }

}