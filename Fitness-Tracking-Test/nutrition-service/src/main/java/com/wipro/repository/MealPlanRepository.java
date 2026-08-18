package com.wipro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.entity.MealPlan;

public interface MealPlanRepository
        extends JpaRepository<MealPlan, Long> {

    List<MealPlan> findByUserId(Long userId);
}