package com.wipro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wipro.entity.FitnessProfile;
import com.wipro.service.UserService;

import lombok.RequiredArgsConstructor;

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
            @RequestBody FitnessProfile profile) {

        return ResponseEntity.ok(
                userService.updateProfile(
                        userId,
                        profile));
    }
}