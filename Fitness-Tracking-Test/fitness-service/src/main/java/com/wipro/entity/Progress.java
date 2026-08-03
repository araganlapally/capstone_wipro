package com.wipro.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "progress")
@Data
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Double weight;

    private Double bodyFat;

    private LocalDate recordedDate;
}