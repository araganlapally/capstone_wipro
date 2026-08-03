package com.wipro.dto;

import lombok.Data;

@Data
public class WorkoutPlanResponse {

    private Long id;
    private Long userId;
    private String planName;
    private String goal;
    private Integer durationWeeks;
}