package com.wipro.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.entity.FitnessProfile;


@Repository
public interface FitnessProfileRepository
        extends JpaRepository<FitnessProfile, Long> {

    Optional<FitnessProfile> findByUserId(Long userId);
}