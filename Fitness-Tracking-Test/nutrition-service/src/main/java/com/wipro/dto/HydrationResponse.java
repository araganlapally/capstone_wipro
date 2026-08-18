package com.wipro.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class HydrationResponse {

    private Long id;

    private Long userId;

    private LocalDate date;

    private Integer waterIntake;

    private Integer waterGoal;

    private Integer remainingWater;

    private Integer percentage;
}