package com.wipro.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wipro.config.FeignConfig;
import com.wipro.dto.AIServiceRequest;

@FeignClient(
        name = "ai-service",
        url = "${ai.service.url}",
        configuration = FeignConfig.class
)
public interface AIServiceClient {

    @PostMapping("/api/ai/nutrition")
    String generateNutrition(
            @RequestBody AIServiceRequest request
    );
}