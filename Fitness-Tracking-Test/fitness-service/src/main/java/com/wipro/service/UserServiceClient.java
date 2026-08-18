package com.wipro.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wipro.config.FeignConfig;
import com.wipro.dto.UserProfileResponse;
import com.wipro.dto.UserResponse;

@FeignClient(
        name = "user-service",
        url = "${user.service.url}",
        configuration = FeignConfig.class
)
public interface UserServiceClient {

    @GetMapping("/api/users/{id}")
    UserResponse getUserById(
            @PathVariable("id") Long id);

    @GetMapping("/api/users/{userId}/profile")
    UserProfileResponse getProfile(
            @PathVariable("userId") Long userId);
}