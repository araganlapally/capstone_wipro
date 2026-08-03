package com.wipro.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ProgressRequest {

    private Long userId;
    private Double weight;
    private Double bodyFat;
    private LocalDate recordedDate;
}