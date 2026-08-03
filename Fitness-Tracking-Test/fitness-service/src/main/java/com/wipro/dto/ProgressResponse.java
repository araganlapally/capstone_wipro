package com.wipro.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ProgressResponse {

    private Long id;
    private Long userId;
    private Double weight;
    private Double bodyFat;
    private LocalDate recordedDate;
}