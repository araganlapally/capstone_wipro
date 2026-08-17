package com.wipro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.dto.HydrationRequest;
import com.wipro.dto.HydrationResponse;
import com.wipro.service.HydrationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/hydration")
@RequiredArgsConstructor
@CrossOrigin("*")
public class HydrationController {

    private final HydrationService hydrationService;

    @GetMapping("/user/{userId}/today")
    public ResponseEntity<HydrationResponse>
            getTodayHydration(
                    @PathVariable Long userId) {

        return ResponseEntity.ok(
                hydrationService
                        .getTodayHydration(userId));
    }

    @PutMapping
    public ResponseEntity<HydrationResponse>
            updateHydration(
                    @Valid @RequestBody HydrationRequest request) {

        return ResponseEntity.ok(
                hydrationService
                        .updateHydration(request));
    }
}