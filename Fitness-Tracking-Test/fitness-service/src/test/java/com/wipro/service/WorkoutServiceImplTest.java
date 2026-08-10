package com.wipro.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.modelmapper.ModelMapper;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import com.wipro.dto.AIWorkoutResponse;
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
    private UserServiceClient userServiceClient;



    @Mock
    private AIService aiService;



    private ModelMapper modelMapper;



    private WorkoutServiceImpl workoutService;




    @BeforeEach
    void setup() {


        MockitoAnnotations.openMocks(this);


        modelMapper =
                new ModelMapper();


        workoutService =
                new WorkoutServiceImpl(
                        workoutPlanRepository,
                        modelMapper,
                        userServiceClient,
                        aiService
                );
    }






    @Test
    void createWorkoutPlan_ShouldCreateSuccessfully() {



        WorkoutPlanRequest request =
                new WorkoutPlanRequest();


        request.setUserId(1L);
        request.setPlanName("Weight Loss");
        request.setGoal("Fat Loss");
        request.setDurationWeeks(8);



        UserResponse user =
                new UserResponse();


        user.setId(1L);



        when(userServiceClient
                .getUserById(1L))
                .thenReturn(user);



        WorkoutPlan workout =
                new WorkoutPlan();


        workout.setId(10L);
        workout.setUserId(1L);
        workout.setPlanName("Weight Loss");
        workout.setGoal("Fat Loss");
        workout.setDurationWeeks(8);



        when(workoutPlanRepository
                .save(org.mockito.ArgumentMatchers.any()))
                .thenReturn(workout);



        WorkoutPlanResponse response =
                workoutService
                .createWorkoutPlan(request);



        assertNotNull(response);


        assertEquals(
                "Weight Loss",
                response.getPlanName()
        );
    }







    @Test
    void getWorkoutPlanById_ShouldReturnWorkout() {



        WorkoutPlan workout =
                new WorkoutPlan();


        workout.setId(1L);
        workout.setPlanName("Muscle Gain");



        when(workoutPlanRepository
                .findById(1L))
                .thenReturn(
                        Optional.of(workout)
                );



        WorkoutPlanResponse response =
                workoutService
                .getWorkoutPlanById(1L);



        assertEquals(
                "Muscle Gain",
                response.getPlanName()
        );
    }







    @Test
    void getWorkoutPlanById_WhenNotFound_ShouldThrowException() {



        when(workoutPlanRepository
                .findById(100L))
                .thenReturn(Optional.empty());



        assertThrows(
                ResourceNotFoundException.class,
                () ->
                workoutService
                .getWorkoutPlanById(100L)
        );
    }







    @Test
    void deleteWorkoutPlan_ShouldDeleteSuccessfully() {



        WorkoutPlan workout =
                new WorkoutPlan();


        workout.setId(1L);



        when(workoutPlanRepository
                .findById(1L))
                .thenReturn(
                        Optional.of(workout)
                );



        workoutService
                .deleteWorkoutPlan(1L);



        verify(
                workoutPlanRepository
        )
        .delete(workout);
    }







    @Test
    void getWorkoutPlansByUserId_ShouldReturnList() {



        WorkoutPlan workout =
                new WorkoutPlan();


        workout.setUserId(1L);
        workout.setPlanName("Fitness Plan");



        when(workoutPlanRepository
                .findByUserId(1L))
                .thenReturn(
                        List.of(workout)
                );



        List<WorkoutPlanResponse> result =
                workoutService
                .getWorkoutPlansByUserId(1L);



        assertEquals(
                1,
                result.size()
        );
    }







    @Test
    void generateWorkout_ShouldReturnAIResponse() {



        when(userServiceClient
                .getProfile(1L))
                .thenReturn(
                        new com.wipro.dto.UserProfileResponse()
                );



        when(aiService
                .generateWorkout(
                        org.mockito.ArgumentMatchers.anyString()
                ))
                .thenReturn(
                        "AI Workout Plan"
                );



        AIWorkoutResponse response =
                workoutService
                .generateWorkout(1L);



        assertEquals(
                "AI Workout Plan",
                response.getWorkoutPlan()
        );
    }

}