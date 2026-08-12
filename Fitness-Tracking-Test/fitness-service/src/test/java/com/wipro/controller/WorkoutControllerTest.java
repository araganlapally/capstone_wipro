package com.wipro.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.wipro.dto.AIWorkoutResponse;
import com.wipro.dto.WorkoutPlanRequest;
import com.wipro.dto.WorkoutPlanResponse;

import com.wipro.security.JwtFilter;

import com.wipro.service.WorkoutService;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class WorkoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private WorkoutService workoutService;

    @MockitoBean
    private JwtFilter jwtFilter;


    // ---------------------------------------------------------
    // POST /api/workouts
    // ---------------------------------------------------------

    @Test
    void createWorkoutPlan_ShouldReturnCreated()
            throws Exception {

        WorkoutPlanRequest request =
                new WorkoutPlanRequest();

        request.setUserId(1L);
        request.setPlanName("Beginner Plan");
        request.setGoal("Weight Loss");
        request.setDurationWeeks(8);


        WorkoutPlanResponse response =
                new WorkoutPlanResponse();

        response.setId(1L);
        response.setUserId(1L);
        response.setPlanName("Beginner Plan");
        response.setGoal("Weight Loss");
        response.setDurationWeeks(8);


        when(workoutService.createWorkoutPlan(any()))
                .thenReturn(response);


        mockMvc.perform(
                post("/api/workouts")
                        .contentType(
                                MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        request))
        )
        .andExpect(status().isCreated())
        .andExpect(
                jsonPath("$.id")
                        .value(1))
        .andExpect(
                jsonPath("$.userId")
                        .value(1))
        .andExpect(
                jsonPath("$.planName")
                        .value("Beginner Plan"))
        .andExpect(
                jsonPath("$.goal")
                        .value("Weight Loss"))
        .andExpect(
                jsonPath("$.durationWeeks")
                        .value(8));
    }


    // ---------------------------------------------------------
    // GET /api/workouts/{id}
    // ---------------------------------------------------------

    @Test
    void getWorkoutPlanById_ShouldReturnWorkout()
            throws Exception {

        WorkoutPlanResponse response =
                new WorkoutPlanResponse();

        response.setId(1L);
        response.setUserId(1L);
        response.setPlanName("Gym Plan");
        response.setGoal("Muscle Gain");
        response.setDurationWeeks(12);


        when(workoutService.getWorkoutPlanById(1L))
                .thenReturn(response);


        mockMvc.perform(
                get("/api/workouts/1")
        )
        .andExpect(status().isOk())
        .andExpect(
                jsonPath("$.id")
                        .value(1))
        .andExpect(
                jsonPath("$.planName")
                        .value("Gym Plan"))
        .andExpect(
                jsonPath("$.goal")
                        .value("Muscle Gain"))
        .andExpect(
                jsonPath("$.durationWeeks")
                        .value(12));
    }


    // ---------------------------------------------------------
    // GET /api/workouts/user/{userId}
    // ---------------------------------------------------------

    @Test
    void getWorkoutPlansByUserId_ShouldReturnList()
            throws Exception {

        WorkoutPlanResponse response =
                new WorkoutPlanResponse();

        response.setId(1L);
        response.setUserId(1L);
        response.setPlanName("Plan A");
        response.setGoal("Weight Loss");
        response.setDurationWeeks(8);


        when(workoutService
                .getWorkoutPlansByUserId(1L))
                .thenReturn(List.of(response));


        mockMvc.perform(
                get("/api/workouts/user/1")
        )
        .andExpect(status().isOk())
        .andExpect(
                jsonPath("$[0].id")
                        .value(1))
        .andExpect(
                jsonPath("$[0].userId")
                        .value(1))
        .andExpect(
                jsonPath("$[0].planName")
                        .value("Plan A"))
        .andExpect(
                jsonPath("$[0].goal")
                        .value("Weight Loss"));
    }


    // ---------------------------------------------------------
    // PUT /api/workouts/{id}
    // ---------------------------------------------------------

    @Test
    void updateWorkoutPlan_ShouldReturnUpdatedWorkout()
            throws Exception {

        WorkoutPlanRequest request =
                new WorkoutPlanRequest();

        request.setUserId(1L);
        request.setPlanName("Updated Plan");
        request.setGoal("Muscle Gain");
        request.setDurationWeeks(12);


        WorkoutPlanResponse response =
                new WorkoutPlanResponse();

        response.setId(1L);
        response.setUserId(1L);
        response.setPlanName("Updated Plan");
        response.setGoal("Muscle Gain");
        response.setDurationWeeks(12);


        when(workoutService.updateWorkoutPlan(
                eq(1L),
                any(WorkoutPlanRequest.class)))
                .thenReturn(response);


        mockMvc.perform(
                put("/api/workouts/1")
                        .contentType(
                                MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        request))
        )
        .andExpect(status().isOk())
        .andExpect(
                jsonPath("$.id")
                        .value(1))
        .andExpect(
                jsonPath("$.planName")
                        .value("Updated Plan"))
        .andExpect(
                jsonPath("$.goal")
                        .value("Muscle Gain"))
        .andExpect(
                jsonPath("$.durationWeeks")
                        .value(12));
    }


    // ---------------------------------------------------------
    // DELETE /api/workouts/{id}
    // ---------------------------------------------------------

    @Test
    void deleteWorkoutPlan_ShouldReturnSuccessMessage()
            throws Exception {

        doNothing()
                .when(workoutService)
                .deleteWorkoutPlan(1L);


        mockMvc.perform(
                delete("/api/workouts/1")
        )
        .andExpect(status().isOk())
        .andExpect(
                jsonPath("$")
                        .value(
                            "Workout Plan deleted successfully"));
    }


    // ---------------------------------------------------------
    // GET /api/workouts/generate/{userId}
    // ---------------------------------------------------------

    @Test
    void generateWorkout_ShouldReturnAIWorkout()
            throws Exception {

        AIWorkoutResponse response =
                new AIWorkoutResponse(
                        "AI Workout Plan");


        when(workoutService
                .generateWorkout(1L))
                .thenReturn(response);


        mockMvc.perform(
                get("/api/workouts/generate/1")
        )
        .andExpect(status().isOk())
        .andExpect(
                jsonPath("$.workoutPlan")
                        .value("AI Workout Plan"));
    }
}