package com.wipro.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.wipro.dto.MealLogRequest;
import com.wipro.dto.MealLogResponse;
import com.wipro.service.MealLogService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class MealLogControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private MealLogService mealLogService;

    @InjectMocks
    private MealLogController mealLogController;

    @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
                .standaloneSetup(mealLogController)
                .setValidator(new LocalValidatorFactoryBean())
                .build();
    }

    @Test
    void addMeal_ShouldReturnCreated()
            throws Exception {

        MealLogRequest request =
                new MealLogRequest();

        /*
         * Set fields according to your
         * MealLogRequest class.
         */

        request.setUserId(1L);
        request.setMealType("BREAKFAST");
        request.setFoodName("Oats");
        request.setQuantity(100);
        request.setCalories(389);
        request.setProtein(17);
        request.setCarbs(66);
        request.setFats(7);

        MealLogResponse response =
                new MealLogResponse();

        when(mealLogService.addMeal(
                any(MealLogRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                post("/api/meals/log")
                        .contentType(
                                MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        request)))
                .andExpect(
                        status().isCreated());
    }

    @Test
    void addMeal_ShouldReturnBadRequest_WhenRequestIsInvalid()
            throws Exception {

        MealLogRequest request =
                new MealLogRequest();

        mockMvc.perform(
                post("/api/meals/log")
                        .contentType(
                                MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        request)))
                .andExpect(
                        status().isBadRequest());
    }

    @Test
    void getTodayMeals_ShouldReturnOk()
            throws Exception {

        when(mealLogService.getTodayMeals(1L))
                .thenReturn(
                        Collections.emptyList());

        mockMvc.perform(
                get("/api/meals/log/user/1/today"))
                .andExpect(status().isOk());
    }

    @Test
    void updateMeal_ShouldReturnOk()
            throws Exception {

        MealLogRequest request =
                new MealLogRequest();

        request.setUserId(1L);
        request.setMealType("LUNCH");
        request.setFoodName("Rice");
        request.setQuantity(200);
        request.setCalories(260);
        request.setProtein(5);
        request.setCarbs(57);
        request.setFats(1);

        MealLogResponse response =
                new MealLogResponse();

        when(mealLogService.updateMeal(
                eq(1L),
                any(MealLogRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                put("/api/meals/log/1")
                        .contentType(
                                MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        request)))
                .andExpect(status().isOk());
    }

    @Test
    void updateMeal_ShouldReturnBadRequest_WhenRequestIsInvalid()
            throws Exception {

        MealLogRequest request =
                new MealLogRequest();

        mockMvc.perform(
                put("/api/meals/log/1")
                        .contentType(
                                MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteMeal_ShouldReturnOk()
            throws Exception {

        doNothing()
                .when(mealLogService)
                .deleteMeal(1L);

        mockMvc.perform(
                delete("/api/meals/log/1"))
                .andExpect(status().isOk());
    }
}