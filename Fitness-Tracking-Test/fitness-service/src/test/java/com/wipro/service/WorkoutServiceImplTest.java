package com.wipro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wipro.dto.AIServiceRequest;
import com.wipro.dto.AIWorkoutResponse;
import com.wipro.dto.UserProfileResponse;
import com.wipro.dto.UserResponse;
import com.wipro.dto.WorkoutPlanRequest;
import com.wipro.dto.WorkoutPlanResponse;
import com.wipro.entity.WorkoutPlan;
import com.wipro.exception.ResourceNotFoundException;
import com.wipro.repository.WorkoutPlanRepository;

class WorkoutServiceImplTest {

    @Mock
    private WorkoutPlanRepository workoutPlanRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserServiceClient userServiceClient;

    @Mock
    private AIService aiService;

    @InjectMocks
    private WorkoutServiceImpl workoutService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createWorkoutPlan_ShouldCreateSuccessfully() {

        // Arrange
        WorkoutPlanRequest request =
                new WorkoutPlanRequest();

        request.setUserId(1L);
        request.setPlanName("Weight Loss");
        request.setGoal("Fat Loss");
        request.setDurationWeeks(8);

        UserResponse user =
                new UserResponse();

        user.setId(1L);

        WorkoutPlan workoutPlan =
                new WorkoutPlan();

        workoutPlan.setId(10L);
        workoutPlan.setUserId(1L);
        workoutPlan.setPlanName("Weight Loss");
        workoutPlan.setGoal("Fat Loss");
        workoutPlan.setDurationWeeks(8);

        WorkoutPlanResponse expectedResponse =
                new WorkoutPlanResponse();

        expectedResponse.setId(10L);
        expectedResponse.setUserId(1L);
        expectedResponse.setPlanName("Weight Loss");
        expectedResponse.setGoal("Fat Loss");
        expectedResponse.setDurationWeeks(8);

        when(userServiceClient.getUserById(1L))
                .thenReturn(user);

        when(modelMapper.map(
                request,
                WorkoutPlan.class))
                .thenReturn(workoutPlan);

        when(workoutPlanRepository.save(workoutPlan))
                .thenReturn(workoutPlan);

        when(modelMapper.map(
                workoutPlan,
                WorkoutPlanResponse.class))
                .thenReturn(expectedResponse);

        // Act
        WorkoutPlanResponse result =
                workoutService.createWorkoutPlan(request);

        // Assert
        assertNotNull(result);

        assertEquals(
                10L,
                result.getId()
        );

        assertEquals(
                "Weight Loss",
                result.getPlanName()
        );

        assertEquals(
                "Fat Loss",
                result.getGoal()
        );

        verify(userServiceClient)
                .getUserById(1L);

        verify(workoutPlanRepository)
                .save(workoutPlan);
    }

    @Test
    void createWorkoutPlan_WhenUserNotFound_ShouldThrowException() {

        // Arrange
        WorkoutPlanRequest request =
                new WorkoutPlanRequest();

        request.setUserId(100L);
        request.setPlanName("Weight Loss");
        request.setGoal("Fat Loss");
        request.setDurationWeeks(8);

        when(userServiceClient.getUserById(100L))
                .thenReturn(null);

        // Act + Assert
        assertThrows(
                ResourceNotFoundException.class,
                () ->
                        workoutService
                                .createWorkoutPlan(request)
        );

        verify(userServiceClient)
                .getUserById(100L);
    }

    @Test
    void getWorkoutPlanById_ShouldReturnWorkout() {

        // Arrange
        WorkoutPlan workoutPlan =
                new WorkoutPlan();

        workoutPlan.setId(1L);
        workoutPlan.setUserId(1L);
        workoutPlan.setPlanName("Muscle Gain");
        workoutPlan.setGoal("Muscle Gain");
        workoutPlan.setDurationWeeks(12);

        WorkoutPlanResponse expectedResponse =
                new WorkoutPlanResponse();

        expectedResponse.setId(1L);
        expectedResponse.setUserId(1L);
        expectedResponse.setPlanName("Muscle Gain");
        expectedResponse.setGoal("Muscle Gain");
        expectedResponse.setDurationWeeks(12);

        when(workoutPlanRepository.findById(1L))
                .thenReturn(
                        Optional.of(workoutPlan)
                );

        when(modelMapper.map(
                workoutPlan,
                WorkoutPlanResponse.class))
                .thenReturn(expectedResponse);

        // Act
        WorkoutPlanResponse result =
                workoutService
                        .getWorkoutPlanById(1L);

        // Assert
        assertNotNull(result);

        assertEquals(
                "Muscle Gain",
                result.getPlanName()
        );

        assertEquals(
                "Muscle Gain",
                result.getGoal()
        );

        verify(workoutPlanRepository)
                .findById(1L);
    }

    @Test
    void getWorkoutPlanById_WhenNotFound_ShouldThrowException() {

        // Arrange
        when(workoutPlanRepository.findById(100L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                ResourceNotFoundException.class,
                () ->
                        workoutService
                                .getWorkoutPlanById(100L)
        );

        verify(workoutPlanRepository)
                .findById(100L);
    }

    @Test
    void getWorkoutPlansByUserId_ShouldReturnList() {

        // Arrange
        WorkoutPlan workout1 =
                new WorkoutPlan();

        workout1.setId(1L);
        workout1.setUserId(1L);
        workout1.setPlanName("Fitness Plan");
        workout1.setGoal("Weight Loss");

        WorkoutPlan workout2 =
                new WorkoutPlan();

        workout2.setId(2L);
        workout2.setUserId(1L);
        workout2.setPlanName("Strength Plan");
        workout2.setGoal("Muscle Gain");

        WorkoutPlanResponse response1 =
                new WorkoutPlanResponse();

        response1.setId(1L);
        response1.setUserId(1L);
        response1.setPlanName("Fitness Plan");
        response1.setGoal("Weight Loss");

        WorkoutPlanResponse response2 =
                new WorkoutPlanResponse();

        response2.setId(2L);
        response2.setUserId(1L);
        response2.setPlanName("Strength Plan");
        response2.setGoal("Muscle Gain");

        when(workoutPlanRepository.findByUserId(1L))
                .thenReturn(
                        List.of(workout1, workout2)
                );

        when(modelMapper.map(
                workout1,
                WorkoutPlanResponse.class))
                .thenReturn(response1);

        when(modelMapper.map(
                workout2,
                WorkoutPlanResponse.class))
                .thenReturn(response2);

        // Act
        List<WorkoutPlanResponse> result =
                workoutService
                        .getWorkoutPlansByUserId(1L);

        // Assert
        assertNotNull(result);

        assertEquals(
                2,
                result.size()
        );

        assertEquals(
                "Fitness Plan",
                result.get(0).getPlanName()
        );

        assertEquals(
                "Strength Plan",
                result.get(1).getPlanName()
        );

        verify(workoutPlanRepository)
                .findByUserId(1L);
    }

    @Test
    void getWorkoutPlansByUserId_WhenNoPlans_ShouldReturnEmptyList() {

        // Arrange
        when(workoutPlanRepository.findByUserId(1L))
                .thenReturn(List.of());

        // Act
        List<WorkoutPlanResponse> result =
                workoutService
                        .getWorkoutPlansByUserId(1L);

        // Assert
        assertNotNull(result);

        assertEquals(
                0,
                result.size()
        );

        verify(workoutPlanRepository)
                .findByUserId(1L);
    }

    @Test
    void updateWorkoutPlan_ShouldUpdateSuccessfully() {

        // Arrange
        WorkoutPlanRequest request =
                new WorkoutPlanRequest();

        request.setUserId(1L);
        request.setPlanName("Updated Plan");
        request.setGoal("Muscle Gain");
        request.setDurationWeeks(12);

        WorkoutPlan existingWorkout =
                new WorkoutPlan();

        existingWorkout.setId(10L);
        existingWorkout.setUserId(1L);
        existingWorkout.setPlanName("Old Plan");
        existingWorkout.setGoal("Weight Loss");
        existingWorkout.setDurationWeeks(8);

        WorkoutPlanResponse expectedResponse =
                new WorkoutPlanResponse();

        expectedResponse.setId(10L);
        expectedResponse.setUserId(1L);
        expectedResponse.setPlanName("Updated Plan");
        expectedResponse.setGoal("Muscle Gain");
        expectedResponse.setDurationWeeks(12);

        when(workoutPlanRepository.findById(10L))
                .thenReturn(
                        Optional.of(existingWorkout)
                );

        when(workoutPlanRepository.save(existingWorkout))
                .thenReturn(existingWorkout);

        when(modelMapper.map(
                existingWorkout,
                WorkoutPlanResponse.class))
                .thenReturn(expectedResponse);

        // Act
        WorkoutPlanResponse result =
                workoutService.updateWorkoutPlan(
                        10L,
                        request
                );

        // Assert
        assertNotNull(result);

        assertEquals(
                "Updated Plan",
                result.getPlanName()
        );

        assertEquals(
                "Muscle Gain",
                result.getGoal()
        );

        assertEquals(
                12,
                result.getDurationWeeks()
        );

        verify(workoutPlanRepository)
                .findById(10L);

        verify(workoutPlanRepository)
                .save(existingWorkout);
    }

    @Test
    void updateWorkoutPlan_WhenNotFound_ShouldThrowException() {

        // Arrange
        WorkoutPlanRequest request =
                new WorkoutPlanRequest();

        request.setUserId(1L);
        request.setPlanName("Updated Plan");
        request.setGoal("Muscle Gain");
        request.setDurationWeeks(12);

        when(workoutPlanRepository.findById(100L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                ResourceNotFoundException.class,
                () ->
                        workoutService
                                .updateWorkoutPlan(
                                        100L,
                                        request
                                )
        );

        verify(workoutPlanRepository)
                .findById(100L);
    }

    @Test
    void deleteWorkoutPlan_ShouldDeleteSuccessfully() {

        // Arrange
        WorkoutPlan workoutPlan =
                new WorkoutPlan();

        workoutPlan.setId(1L);
        workoutPlan.setUserId(1L);
        workoutPlan.setPlanName("Fitness Plan");

        when(workoutPlanRepository.findById(1L))
                .thenReturn(
                        Optional.of(workoutPlan)
                );

        // Act
        workoutService.deleteWorkoutPlan(1L);

        // Assert
        verify(workoutPlanRepository)
                .findById(1L);

        verify(workoutPlanRepository)
                .delete(workoutPlan);
    }

    @Test
    void deleteWorkoutPlan_WhenNotFound_ShouldThrowException() {

        // Arrange
        when(workoutPlanRepository.findById(100L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                ResourceNotFoundException.class,
                () ->
                        workoutService
                                .deleteWorkoutPlan(100L)
        );

        verify(workoutPlanRepository)
                .findById(100L);
    }

    @Test
    void generateWorkout_ShouldReturnAIWorkoutResponse() {

        // Arrange
        UserProfileResponse profile =
                new UserProfileResponse();

        profile.setId(1L);
        profile.setAge(25);
        profile.setHeight(175.0);
        profile.setWeight(80.0);
        profile.setGender("Male");
        profile.setGoal("Weight Loss");

        AIWorkoutResponse expectedResponse =
                new AIWorkoutResponse(
                        "AI generated 7-day workout plan"
                );

        when(userServiceClient.getProfile(1L))
                .thenReturn(profile);

        when(aiService.generateWorkout(
                any(AIServiceRequest.class)))
                .thenReturn(expectedResponse);

        // Act
        AIWorkoutResponse result =
                workoutService.generateWorkout(1L);

        // Assert
        assertNotNull(result);

        assertEquals(
                "AI generated 7-day workout plan",
                result.getWorkoutPlan()
        );

        verify(userServiceClient)
                .getProfile(1L);

        verify(aiService)
                .generateWorkout(
                        any(AIServiceRequest.class)
                );
    }
}