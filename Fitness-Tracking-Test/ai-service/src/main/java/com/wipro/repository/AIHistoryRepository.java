package com.wipro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.entity.AIHistory;

public interface AIHistoryRepository
        extends JpaRepository<AIHistory, Long> {

    List<AIHistory> findByUserIdOrderByCreatedAtDesc(Long userId);
}