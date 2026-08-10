package com.wipro.service;

import java.util.List;
import java.util.stream.Collectors;
import com.wipro.dto.AIWorkoutResponse;
import com.wipro.dto.UserProfileResponse;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.wipro.dto.UserResponse;
import com.wipro.dto.WorkoutPlanRequest;
import com.wipro.dto.WorkoutPlanResponse;
import com.wipro.entity.WorkoutPlan;
import com.wipro.exception.ResourceNotFoundException;
import com.wipro.repository.WorkoutPlanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final ModelMapper modelMapper;
    private final UserServiceClient userServiceClient;
    private final AIService geminiService;

    @Override
    public WorkoutPlanResponse createWorkoutPlan(
            WorkoutPlanRequest request) {

        UserResponse user =
                userServiceClient.getUserById(
                        request.getUserId());

        if (user == null) {
            throw new ResourceNotFoundException(
                    "User not found with id : "
                            + request.getUserId());
        }

        WorkoutPlan workoutPlan =
                modelMapper.map(
                        request,
                        WorkoutPlan.class);

        WorkoutPlan savedWorkoutPlan =
                workoutPlanRepository.save(
                        workoutPlan);

        return modelMapper.map(
                savedWorkoutPlan,
                WorkoutPlanResponse.class);
    }

    @Override
    public WorkoutPlanResponse getWorkoutPlanById(
            Long id) {

        WorkoutPlan workoutPlan =
                workoutPlanRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Workout Plan not found with id : "
                                        + id));

        return modelMapper.map(
                workoutPlan,
                WorkoutPlanResponse.class);
    }

    @Override
    public List<WorkoutPlanResponse>
            getWorkoutPlansByUserId(Long userId) {

        return workoutPlanRepository
                .findByUserId(userId)
                .stream()
                .map(workout ->
                        modelMapper.map(
                                workout,
                                WorkoutPlanResponse.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public WorkoutPlanResponse updateWorkoutPlan(
            Long id,
            WorkoutPlanRequest request) {

        WorkoutPlan workoutPlan =
                workoutPlanRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Workout Plan not found with id : "
                                        + id));

        workoutPlan.setUserId(
                request.getUserId());

        workoutPlan.setPlanName(
                request.getPlanName());

        workoutPlan.setGoal(
                request.getGoal());

        workoutPlan.setDurationWeeks(
                request.getDurationWeeks());

        WorkoutPlan updatedWorkout =
                workoutPlanRepository.save(
                        workoutPlan);

        return modelMapper.map(
                updatedWorkout,
                WorkoutPlanResponse.class);
    }

    @Override
    public void deleteWorkoutPlan(Long id) {

        WorkoutPlan workoutPlan =
                workoutPlanRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Workout Plan not found with id : "
                                        + id));

        workoutPlanRepository.delete(workoutPlan);
    }
    @Override
    public AIWorkoutResponse generateWorkout(Long userId) {

        UserProfileResponse profile =
                userServiceClient.getProfile(userId);

        String prompt = String.format("""
        		Generate a personalized 7-day workout plan.

        		Age: %d
        		Height: %.1f cm
        		Weight: %.1f kg
        		Gender: %s
        		Goal: %s

        		Return ONLY valid JSON.

        		Format:

        		{
        		  "days": [
        		    {
        		      "day": "Monday",
        		      "focus": "Chest & Triceps",
        		      "exercises": [
        		        {
        		          "name": "Bench Press",
        		          "sets": 4,
        		          "reps": 10
        		        }
        		      ]
        		    }
        		  ]
        		}

        		Rules:
        		1. Return only JSON.
        		2. No markdown.
        		3. No explanation.
        		4. No ```json blocks.
        		5. Generate all 7 days.

        		""",
        		profile.getAge(),
        		profile.getHeight(),
        		profile.getWeight(),
        		profile.getGender(),
        		profile.getGoal()
        		);
        String workoutPlan =
                geminiService.generateWorkout(prompt);

        return new AIWorkoutResponse(workoutPlan);
    }
}