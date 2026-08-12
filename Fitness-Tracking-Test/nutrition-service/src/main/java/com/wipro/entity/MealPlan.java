package com.wipro.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "meal_plans")
@Data
public class MealPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String mealName;

    private Integer calories;

    private Integer protein;

    private Integer carbs;

    private Integer fats;
}