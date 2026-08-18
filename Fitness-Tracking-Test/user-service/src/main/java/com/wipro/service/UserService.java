package com.wipro.service;

import com.wipro.dto.UserProfileRequest;
import com.wipro.entity.FitnessProfile;

public interface UserService {

    FitnessProfile getProfile(Long userId);

    FitnessProfile updateProfile(
            Long userId,
            UserProfileRequest request);
}
