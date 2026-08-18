package com.wipro.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "meal_plans")
@Data
public class MealPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "meal_name", nullable = false)
    private String mealName;

    @Column(name = "calories", nullable = false)
    private Integer calories;

    @Column(name = "protein_g", nullable = false)
    private Integer protein;

    @Column(name = "carbs_g", nullable = false)
    private Integer carbs;

    @Column(name = "fats_g", nullable = false)
    private Integer fats;
}