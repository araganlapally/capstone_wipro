package com.wipro.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.wipro.dto.AIServiceRequest;
import com.wipro.service.AIService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AIService aiService;

    @Test
    void generateWorkout_shouldReturnAIResponse()
            throws Exception {

        AIServiceRequest request =
                new AIServiceRequest();

        request.setUserId(1L);
        request.setPrompt(
                "Create a 7-day workout plan.");
        request.setRequestType("WORKOUT");

        when(aiService.generateResponse(anyString()))
                .thenReturn(
                        "Monday: Chest and Triceps");

        // Act + Assert
        mockMvc.perform(
                post("/api/ai/workout")
                        .contentType(
                                MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        request)))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.response")
                                .value(
                                        "Monday: Chest and Triceps"));

        verify(aiService)
                .generateResponse(
                        "Create a 7-day workout plan.");
    }

    @Test
    void generateNutrition_shouldReturnAIResponse()
            throws Exception {

        // Arrange
        AIServiceRequest request =
                new AIServiceRequest();

        request.setUserId(1L);
        request.setPrompt(
                "Create a personalized nutrition plan.");
        request.setRequestType("NUTRITION");

        when(aiService.generateResponse(anyString()))
                .thenReturn(
                        "{\"dailyCalories\":2200}");

        // Act + Assert
        mockMvc.perform(
                post("/api/ai/nutrition")
                        .contentType(
                                MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        request)))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.response")
                                .value(
                                        "{\"dailyCalories\":2200}"));

        verify(aiService)
                .generateResponse(
                        "Create a personalized nutrition plan.");
    }

    @Test
    void chat_shouldReturnAIResponse()
            throws Exception {

        // Arrange
        AIServiceRequest request =
                new AIServiceRequest();

        request.setUserId(1L);
        request.setPrompt(
                "How can I lose weight?");
        request.setRequestType("CHAT");

        when(aiService.generateResponse(anyString()))
                .thenReturn(
                        "Maintain a calorie deficit and exercise regularly.");

        // Act + Assert
        mockMvc.perform(
                post("/api/ai/chat")
                        .contentType(
                                MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        request)))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.response")
                                .value(
                                        "Maintain a calorie deficit and exercise regularly."));

        verify(aiService)
                .generateResponse(
                        "How can I lose weight?");
    }
}