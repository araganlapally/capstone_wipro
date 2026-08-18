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

import com.wipro.dto.MealLogRequest;
import com.wipro.dto.MealLogResponse;
import com.wipro.service.MealLogService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/meals/log")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MealLogController {

    private final MealLogService mealLogService;

    @PostMapping
    public ResponseEntity<MealLogResponse> addMeal(
            @Valid @RequestBody MealLogRequest request) {

        return new ResponseEntity<>(
                mealLogService.addMeal(request),
                HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/today")
    public ResponseEntity<List<MealLogResponse>>
            getTodayMeals(
                    @PathVariable Long userId) {

        return ResponseEntity.ok(
                mealLogService.getTodayMeals(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealLogResponse> updateMeal(
            @PathVariable Long id,
            @Valid @RequestBody MealLogRequest request) {

        return ResponseEntity.ok(
                mealLogService.updateMeal(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMeal(
            @PathVariable Long id) {

        mealLogService.deleteMeal(id);

        return ResponseEntity.ok(
                "Meal deleted successfully");
    }
}