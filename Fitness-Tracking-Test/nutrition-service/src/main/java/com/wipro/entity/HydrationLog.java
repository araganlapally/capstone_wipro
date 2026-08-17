package com.wipro.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(
    name = "hydration_logs",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"user_id", "log_date"}
        )
    }
)
@Data
public class HydrationLog {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id",nullable = false)
    private Long userId;

    @Column(name = "log_date",nullable = false)
    private LocalDate logDate;

    @Column(name = "water_intake_ml",nullable = false)
    private Integer waterIntake;

    @Column(name = "water_goal_ml",nullable = false)
    private Integer waterGoal;
}