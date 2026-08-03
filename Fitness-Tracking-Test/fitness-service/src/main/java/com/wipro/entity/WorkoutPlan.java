package com.wipro.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "workout_plans")
@Data
public class WorkoutPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String planName;

    private String goal;

    private Integer durationWeeks;
}