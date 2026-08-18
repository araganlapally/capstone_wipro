package com.wipro.service;

import org.springframework.stereotype.Service;

import com.wipro.dto.AIServiceRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {

    private final AIServiceClient aiServiceClient;

    @Override
    public String generateNutritionRecommendation(
            Long userId,
            String prompt) {

        AIServiceRequest request =
                new AIServiceRequest(
                        userId,
                        prompt,
                        "NUTRITION"
                );

        return aiServiceClient.generateNutrition(request);
    }
}