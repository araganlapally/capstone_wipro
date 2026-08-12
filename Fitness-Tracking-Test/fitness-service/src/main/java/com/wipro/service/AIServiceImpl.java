package com.wipro.service;

import org.springframework.stereotype.Service;

import com.wipro.dto.AIServiceRequest;
import com.wipro.dto.AIWorkoutResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {

    private final AIServiceClient aiServiceClient;

    @Override
    public AIWorkoutResponse generateWorkout(
            AIServiceRequest request) {

        return aiServiceClient.generateWorkout(request);
    }
}