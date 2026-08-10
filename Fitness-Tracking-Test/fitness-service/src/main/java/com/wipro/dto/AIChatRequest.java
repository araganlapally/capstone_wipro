package com.wipro.dto;

import lombok.Data;

@Data
public class AIChatRequest {

    private Long userId;
    private String question;
}