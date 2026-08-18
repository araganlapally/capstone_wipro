package com.wipro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.dto.UserProfileRequest;
import com.wipro.entity.FitnessProfile;
import com.wipro.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}/profile")
    public ResponseEntity<FitnessProfile> getProfile(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                userService.getProfile(userId));
    }

    @PutMapping("/{userId}/profile")
    public ResponseEntity<FitnessProfile> updateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UserProfileRequest request) {

        return ResponseEntity.ok(
                userService.updateProfile(
                        userId,
                        request));
    }
}