package com.wipro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.dto.AIServiceRequest;
import com.wipro.dto.AIServiceResponse;
import com.wipro.entity.AIHistory;
import com.wipro.repository.AIHistoryRepository;
import com.wipro.service.AIService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;
    private final AIHistoryRepository aiHistoryRepository;

    @PostMapping("/workout")
    public ResponseEntity<AIServiceResponse> generateWorkout(
            @Valid @RequestBody AIServiceRequest request) {

        String response =
                aiService.generateResponse(request);

        return ResponseEntity.ok(
                new AIServiceResponse(response));
    }

    @PostMapping("/nutrition")
    public ResponseEntity<AIServiceResponse> generateNutrition(
            @Valid @RequestBody AIServiceRequest request) {

        String response =
                aiService.generateResponse(request);

        return ResponseEntity.ok(
                new AIServiceResponse(response));
    }

    @PostMapping("/chat")
    public ResponseEntity<AIServiceResponse> chat(
            @Valid @RequestBody AIServiceRequest request) {

        String response =
                aiService.generateResponse(request);

        return ResponseEntity.ok(
                new AIServiceResponse(response));
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<AIHistory>> getHistory(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                aiHistoryRepository
                        .findByUserIdOrderByCreatedAtDesc(userId));
    }
}