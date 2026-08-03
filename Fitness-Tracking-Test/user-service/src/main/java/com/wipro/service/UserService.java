package com.wipro.service;

import com.wipro.entity.FitnessProfile;

public interface UserService {

    FitnessProfile getProfile(Long userId);

    FitnessProfile updateProfile(
            Long userId,
            FitnessProfile profile);
}
