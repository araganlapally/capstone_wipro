package com.wipro.service;

import java.util.List;

import com.wipro.dto.AIWorkoutResponse;
import com.wipro.dto.WorkoutPlanRequest;
import com.wipro.dto.WorkoutPlanResponse;

public interface WorkoutService {

    WorkoutPlanResponse createWorkoutPlan(
            WorkoutPlanRequest request);

    WorkoutPlanResponse getWorkoutPlanById(
            Long id);

    WorkoutPlanResponse updateWorkoutPlan(
            Long id,
            WorkoutPlanRequest request);

    List<WorkoutPlanResponse> getWorkoutPlansByUserId(
            Long userId);

    void deleteWorkoutPlan(
            Long id);

    /*
     * AI Workout Generation
     */
    AIWorkoutResponse generateWorkout(
            Long userId);
}