package com.wipro.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.wipro.dto.HydrationRequest;
import com.wipro.dto.HydrationResponse;
import com.wipro.service.HydrationService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class HydrationControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private HydrationService hydrationService;

    @InjectMocks
    private HydrationController hydrationController;

    @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
                .standaloneSetup(hydrationController)
                .setValidator(new LocalValidatorFactoryBean())
                .build();
    }

    @Test
    void getTodayHydration_ShouldReturnOk()
            throws Exception {

        HydrationResponse response =
                new HydrationResponse();

        when(hydrationService.getTodayHydration(1L))
                .thenReturn(response);

        mockMvc.perform(
                get("/api/hydration/user/1/today"))
                .andExpect(status().isOk());
    }

    @Test
    void updateHydration_ShouldReturnOk()
            throws Exception {

        HydrationRequest request =
                new HydrationRequest();

        request.setUserId(1L);
        request.setWaterIntake(2000);
        request.setWaterGoal(3000);

        HydrationResponse response =
                new HydrationResponse();

        when(hydrationService.updateHydration(
                any(HydrationRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                put("/api/hydration")
                        .contentType(
                                MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        request)))
                .andExpect(status().isOk());
    }

    @Test
    void updateHydration_ShouldReturnBadRequest_WhenRequestIsInvalid()
            throws Exception {

        HydrationRequest request =
                new HydrationRequest();

        mockMvc.perform(
                put("/api/hydration")
                        .contentType(
                                MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        request)))
                .andExpect(status().isBadRequest());
    }
}