package com.wipro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AIServiceRequest {

    private Long userId;

    private String prompt;

    private String requestType;
}