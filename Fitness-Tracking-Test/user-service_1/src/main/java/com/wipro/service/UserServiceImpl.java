package com.wipro.service;



import org.springframework.stereotype.Service;

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
            FitnessProfile profile) {

        FitnessProfile existingProfile =
                profileRepository
                        .findByUserId(userId)
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "Profile not found"));

        existingProfile.setAge(profile.getAge());
        existingProfile.setHeight(profile.getHeight());
        existingProfile.setWeight(profile.getWeight());
        existingProfile.setGoal(profile.getGoal());
        existingProfile.setGender(profile.getGender());

        return profileRepository.save(existingProfile);
    }
}
