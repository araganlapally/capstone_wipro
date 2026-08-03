package com.wipro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.entity.WorkoutPlan;

public interface WorkoutPlanRepository
        extends JpaRepository<WorkoutPlan, Long> {

    List<WorkoutPlan> findByUserId(Long userId);
}