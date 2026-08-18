package com.wipro.service;

import com.wipro.dto.AIServiceRequest;
import com.wipro.dto.AIWorkoutResponse;

public interface AIService {

    AIWorkoutResponse generateWorkout(AIServiceRequest request);
}