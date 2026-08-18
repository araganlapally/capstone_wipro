package com.wipro.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.wipro.dto.UserProfileRequest;
import com.wipro.entity.FitnessProfile;
import com.wipro.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setValidator(new LocalValidatorFactoryBean())
                .build();
    }

    @Test
    void getProfile_ShouldReturnOk() throws Exception {

        FitnessProfile profile =
                FitnessProfile.builder()
                        .id(1L)
                        .age(25)
                        .height(175.0)
                        .weight(70.0)
                        .goal("WEIGHT_LOSS")
                        .gender("MALE")
                        .build();

        when(userService.getProfile(1L))
                .thenReturn(profile);

        mockMvc.perform(
                get("/api/users/1/profile"))
                .andExpect(status().isOk());
    }

    @Test
    void updateProfile_ShouldReturnOk()
            throws Exception {

        UserProfileRequest request =
                new UserProfileRequest();

        request.setAge(25);
        request.setHeight(175.0);
        request.setWeight(70.0);
        request.setGoal("MUSCLE_GAIN");
        request.setGender("MALE");

        FitnessProfile profile =
                FitnessProfile.builder()
                        .id(1L)
                        .age(25)
                        .height(175.0)
                        .weight(70.0)
                        .goal("MUSCLE_GAIN")
                        .gender("MALE")
                        .build();

        when(userService.updateProfile(
                eq(1L),
                any(UserProfileRequest.class)))
                .thenReturn(profile);

        mockMvc.perform(
                put("/api/users/1/profile")
                        .contentType(
                                MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        request)))
                .andExpect(status().isOk());
    }

    @Test
    void updateProfile_ShouldReturnBadRequest_WhenProfileIsInvalid()
            throws Exception {

        UserProfileRequest request =
                new UserProfileRequest();

        request.setAge(null);
        request.setHeight(null);
        request.setWeight(null);
        request.setGoal("");
        request.setGender("");

        mockMvc.perform(
                put("/api/users/1/profile")
                        .contentType(
                                MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        request)))
                .andExpect(status().isBadRequest());
    }
}