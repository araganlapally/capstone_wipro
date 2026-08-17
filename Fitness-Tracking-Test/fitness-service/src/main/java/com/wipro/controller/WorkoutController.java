package com.wipro.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.dto.AIWorkoutResponse;
import com.wipro.dto.WorkoutPlanRequest;
import com.wipro.dto.WorkoutPlanResponse;
import com.wipro.service.WorkoutService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
@CrossOrigin("*")
public class WorkoutController {

    private final WorkoutService workoutService;

    @PostMapping
    public ResponseEntity<WorkoutPlanResponse> createWorkoutPlan(
            @Valid @RequestBody WorkoutPlanRequest request) {

        return new ResponseEntity<>(
                workoutService.createWorkoutPlan(request),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutPlanResponse> getWorkoutPlanById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                workoutService.getWorkoutPlanById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WorkoutPlanResponse>>
            getWorkoutPlansByUserId(
                    @PathVariable Long userId) {

        return ResponseEntity.ok(
                workoutService.getWorkoutPlansByUserId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutPlanResponse>
            updateWorkoutPlan(
                    @PathVariable Long id,
                    @Valid @RequestBody WorkoutPlanRequest request) {

        return ResponseEntity.ok(
                workoutService.updateWorkoutPlan(
                        id,
                        request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkoutPlan(
            @PathVariable Long id) {

        workoutService.deleteWorkoutPlan(id);

        return ResponseEntity.ok(
                "Workout Plan deleted successfully");
    }

    /*
     * AI Workout Generation
     */
    @GetMapping("/generate/{userId}")
    public ResponseEntity<AIWorkoutResponse> generateWorkout(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                workoutService.generateWorkout(userId));
    }
}