package com.wipro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.entity.Progress;

public interface ProgressRepository
        extends JpaRepository<Progress, Long> {

    List<Progress> findByUserId(Long userId);
}