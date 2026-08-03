package com.wipro.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wipro.dto.ProgressRequest;
import com.wipro.dto.ProgressResponse;
import com.wipro.service.ProgressService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    @PostMapping
    public ResponseEntity<ProgressResponse> saveProgress(
            @RequestBody ProgressRequest request) {

        return new ResponseEntity<>(
                progressService.saveProgress(request),
                HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProgressResponse>>
            getProgressByUserId(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                progressService.getProgressByUserId(userId));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProgressResponse>
    updateProgress(
            @PathVariable Long id,
            @RequestBody ProgressRequest request) {

        return ResponseEntity.ok(
                progressService.updateProgress(
                        id,
                        request));
    }
}