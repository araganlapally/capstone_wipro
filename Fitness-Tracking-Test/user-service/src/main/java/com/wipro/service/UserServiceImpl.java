package com.wipro.service;

import org.springframework.stereotype.Service;

import com.wipro.dto.UserProfileRequest;
import com.wipro.entity.FitnessProfile;
import com.wipro.exception.UserNotFoundException;
import com.wipro.repository.FitnessProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final FitnessProfileRepository profileRepository;

    @Override
    public FitnessProfile getProfile(Long userId) {

        return profileRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Profile not found"));
    }

    @Override
    public FitnessProfile updateProfile(
            Long userId,
            UserProfileRequest request) {

        FitnessProfile existingProfile =
                profileRepository
                        .findByUserId(userId)
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "Profile not found"));

        existingProfile.setAge(request.getAge());
        existingProfile.setHeight(request.getHeight());
        existingProfile.setWeight(request.getWeight());
        existingProfile.setGoal(request.getGoal());
        existingProfile.setGender(request.getGender());

        return profileRepository.save(existingProfile);
    }
}