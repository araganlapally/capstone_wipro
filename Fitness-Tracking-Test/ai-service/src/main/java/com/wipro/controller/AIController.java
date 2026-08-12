package com.wipro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.dto.AIServiceRequest;
import com.wipro.dto.AIServiceResponse;
import com.wipro.service.AIService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;

    @PostMapping("/workout")
    public ResponseEntity<AIServiceResponse> generateWorkout(
            @RequestBody AIServiceRequest request) {

        String response =
                aiService.generateResponse(
                        request.getPrompt());

        return ResponseEntity.ok(
                new AIServiceResponse(response));
    }

    @PostMapping("/nutrition")
    public ResponseEntity<AIServiceResponse> generateNutrition(
            @RequestBody AIServiceRequest request) {

        String response =
                aiService.generateResponse(
                        request.getPrompt());

        return ResponseEntity.ok(
                new AIServiceResponse(response));
    }

    @PostMapping("/chat")
    public ResponseEntity<AIServiceResponse> chat(
            @RequestBody AIServiceRequest request) {

        String response =
                aiService.generateResponse(
                        request.getPrompt());

        return ResponseEntity.ok(
                new AIServiceResponse(response));
    }
}