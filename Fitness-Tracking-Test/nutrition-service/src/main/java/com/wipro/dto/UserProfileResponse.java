package com.wipro.dto;

import lombok.Data;

@Data
public class UserProfileResponse {

    private Long id;
    private Integer age;
    private Double height;
    private Double weight;
    private String goal;
    private String gender;
}
