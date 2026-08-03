package com.wipro.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fitness_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FitnessProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer age;

    private Double height;

    private Double weight;

    private String goal;

    private String gender;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
