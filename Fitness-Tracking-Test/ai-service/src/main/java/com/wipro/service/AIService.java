package com.wipro.service;

import com.wipro.dto.AIServiceRequest;

public interface AIService {

    String generateResponse(AIServiceRequest request);
}