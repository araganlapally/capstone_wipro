package com.wipro.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {

    private Long userId;

    private String fullName;

    private String email;

    private Integer age;

    private Double height;

    private Double weight;

    private String goal;

    private String gender;
}
