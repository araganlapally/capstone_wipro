package com.wipro.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.entity.HydrationLog;

public interface HydrationLogRepository
        extends JpaRepository<HydrationLog, Long> {

    Optional<HydrationLog> findByUserIdAndLogDate(
            Long userId,
            LocalDate logDate);
}