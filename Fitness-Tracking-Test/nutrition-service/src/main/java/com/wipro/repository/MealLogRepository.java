package com.wipro.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.entity.MealLog;

public interface MealLogRepository
        extends JpaRepository<MealLog, Long> {

    List<MealLog> findByUserIdAndLogDate(
            Long userId,
            LocalDate logDate);

    List<MealLog> findByUserIdOrderByLogDateDesc(
            Long userId);
}