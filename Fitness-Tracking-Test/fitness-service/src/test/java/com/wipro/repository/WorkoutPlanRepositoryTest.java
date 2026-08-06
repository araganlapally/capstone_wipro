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

import com.wipro.entity.WorkoutPlan;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WorkoutPlanRepositoryTest {


    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;



    @Test
    void saveWorkoutPlan_ShouldReturnSavedPlan() {


        WorkoutPlan workoutPlan =
                new WorkoutPlan();


        workoutPlan.setUserId(1L);
        workoutPlan.setPlanName("Weight Loss Plan");
        workoutPlan.setGoal("Weight Loss");
        workoutPlan.setDurationWeeks(8);



        WorkoutPlan saved =
                workoutPlanRepository.save(workoutPlan);



        assertEquals(
                "Weight Loss Plan",
                saved.getPlanName()
        );

        assertEquals(
                "Weight Loss",
                saved.getGoal()
        );
    }




    @Test
    void findById_ShouldReturnWorkoutPlan() {


        WorkoutPlan workoutPlan =
                new WorkoutPlan();


        workoutPlan.setUserId(1L);
        workoutPlan.setPlanName("Muscle Gain");
        workoutPlan.setGoal("Strength");
        workoutPlan.setDurationWeeks(12);



        WorkoutPlan saved =
                workoutPlanRepository.save(workoutPlan);



        Optional<WorkoutPlan> result =
                workoutPlanRepository
                        .findById(saved.getId());



        assertTrue(result.isPresent());

        assertEquals(
                "Muscle Gain",
                result.get().getPlanName()
        );
    }





    @Test
    void findByUserId_ShouldReturnWorkoutPlans() {


        WorkoutPlan workoutPlan1 =
                new WorkoutPlan();

        workoutPlan1.setUserId(10L);
        workoutPlan1.setPlanName("Plan A");
        workoutPlan1.setGoal("Fitness");
        workoutPlan1.setDurationWeeks(6);



        WorkoutPlan workoutPlan2 =
                new WorkoutPlan();

        workoutPlan2.setUserId(10L);
        workoutPlan2.setPlanName("Plan B");
        workoutPlan2.setGoal("Fitness");
        workoutPlan2.setDurationWeeks(8);



        workoutPlanRepository.save(workoutPlan1);
        workoutPlanRepository.save(workoutPlan2);



        List<WorkoutPlan> result =
                workoutPlanRepository
                        .findByUserId(10L);



        assertEquals(
                2,
                result.size()
        );

        assertEquals(
                "Plan A",
                result.get(0).getPlanName()
        );
    }





    @Test
    void findByUserId_WhenNoData_ShouldReturnEmptyList() {


        List<WorkoutPlan> result =
                workoutPlanRepository
                        .findByUserId(999L);



        assertTrue(
                result.isEmpty()
        );
    }




    @Test
    void deleteWorkoutPlan_ShouldRemoveEntity() {


        WorkoutPlan workoutPlan =
                new WorkoutPlan();


        workoutPlan.setUserId(5L);
        workoutPlan.setPlanName("Delete Plan");
        workoutPlan.setGoal("Test");
        workoutPlan.setDurationWeeks(4);



        WorkoutPlan saved =
                workoutPlanRepository.save(workoutPlan);



        workoutPlanRepository.delete(saved);



        Optional<WorkoutPlan> result =
                workoutPlanRepository
                        .findById(saved.getId());



        assertFalse(
                result.isPresent()
        );
    }

}