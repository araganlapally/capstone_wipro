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

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "plan_name", nullable = false)
    private String planName;

    @Column(name = "goal", nullable = false)
    private String goal;

    @Column(name = "duration_weeks", nullable = false)
    private Integer durationWeeks;
}