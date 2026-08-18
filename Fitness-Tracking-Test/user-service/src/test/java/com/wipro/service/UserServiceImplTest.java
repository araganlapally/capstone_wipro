package com.wipro.service;

import com.wipro.dto.UserProfileRequest;
import com.wipro.entity.FitnessProfile;
import com.wipro.exception.UserNotFoundException;
import com.wipro.repository.FitnessProfileRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private FitnessProfileRepository profileRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getProfile_Success() {

        Long userId = 1L;

        FitnessProfile profile = new FitnessProfile();

        profile.setAge(25);
        profile.setHeight(175.0);
        profile.setWeight(70.0);
        profile.setGoal("Weight Loss");
        profile.setGender("Male");

        when(profileRepository.findByUserId(userId))
                .thenReturn(Optional.of(profile));

        FitnessProfile result =
                userService.getProfile(userId);

        assertNotNull(result);
        assertEquals(25, result.getAge());
        assertEquals(175.0, result.getHeight());
        assertEquals(70.0, result.getWeight());
        assertEquals("Weight Loss", result.getGoal());
        assertEquals("Male", result.getGender());

        verify(profileRepository, times(1))
                .findByUserId(userId);
    }

    @Test
    void getProfile_UserNotFound() {

        Long userId = 1L;

        when(profileRepository.findByUserId(userId))
                .thenReturn(Optional.empty());

        UserNotFoundException exception =
                assertThrows(
                        UserNotFoundException.class,
                        () -> userService.getProfile(userId));

        assertEquals(
                "Profile not found",
                exception.getMessage());

        verify(profileRepository, times(1))
                .findByUserId(userId);
    }

    @Test
    void updateProfile_Success() {

        Long userId = 1L;

        FitnessProfile existingProfile =
                new FitnessProfile();

        existingProfile.setAge(20);
        existingProfile.setHeight(170.0);
        existingProfile.setWeight(60.0);
        existingProfile.setGoal("Gain Weight");
        existingProfile.setGender("Male");

        UserProfileRequest request =
                new UserProfileRequest();

        request.setAge(25);
        request.setHeight(175.0);
        request.setWeight(70.0);
        request.setGoal("Weight Loss");
        request.setGender("Female");

        when(profileRepository.findByUserId(userId))
                .thenReturn(Optional.of(existingProfile));

        when(profileRepository.save(
                any(FitnessProfile.class)))
                .thenAnswer(
                        invocation ->
                                invocation.getArgument(0));

        FitnessProfile result =
                userService.updateProfile(
                        userId,
                        request);

        assertNotNull(result);

        assertEquals(
                25,
                result.getAge());

        assertEquals(
                175.0,
                result.getHeight());

        assertEquals(
                70.0,
                result.getWeight());

        assertEquals(
                "Weight Loss",
                result.getGoal());

        assertEquals(
                "Female",
                result.getGender());

        verify(profileRepository, times(1))
                .findByUserId(userId);

        verify(profileRepository, times(1))
                .save(existingProfile);
    }

    @Test
    void updateProfile_UserNotFound() {

        Long userId = 1L;

        UserProfileRequest request =
                new UserProfileRequest();

        request.setAge(25);
        request.setHeight(175.0);
        request.setWeight(70.0);
        request.setGoal("Weight Loss");
        request.setGender("Male");

        when(profileRepository.findByUserId(userId))
                .thenReturn(Optional.empty());

        UserNotFoundException exception =
                assertThrows(
                        UserNotFoundException.class,
                        () -> userService.updateProfile(
                                userId,
                                request));

        assertEquals(
                "Profile not found",
                exception.getMessage());

        verify(profileRepository, times(1))
                .findByUserId(userId);

        verify(profileRepository, never())
                .save(any(FitnessProfile.class));
    }
}