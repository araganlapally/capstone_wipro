package com.wipro.dto;

import lombok.Data;

@Data
public class WorkoutPlanRequest {

    private Long userId;
    private String planName;
    private String goal;
    private Integer durationWeeks;
}