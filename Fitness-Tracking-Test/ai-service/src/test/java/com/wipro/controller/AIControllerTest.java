package com.wipro.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.dto.AIServiceRequest;
import com.wipro.entity.AIHistory;
import com.wipro.repository.AIHistoryRepository;
import com.wipro.service.AIService;

@SpringBootTest
@AutoConfigureMockMvc
class AIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AIService aiService;

    @MockitoBean
    private AIHistoryRepository aiHistoryRepository;


    @Test
    void generateWorkout_shouldReturnAIResponse()
            throws Exception {

        AIServiceRequest request = new AIServiceRequest();

        request.setUserId(1L);
        request.setPrompt("Create a 7-day workout plan.");
        request.setRequestType("WORKOUT");

        when(aiService.generateResponse(any(AIServiceRequest.class)))
                .thenReturn("Monday: Chest and Triceps");

        mockMvc.perform(
                post("/api/ai/workout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.response")
                                .value("Monday: Chest and Triceps"));

        verify(aiService)
                .generateResponse(any(AIServiceRequest.class));
    }


    @Test
    void generateNutrition_shouldReturnAIResponse()
            throws Exception {

        AIServiceRequest request = new AIServiceRequest();

        request.setUserId(1L);
        request.setPrompt("Create a personalized nutrition plan.");
        request.setRequestType("NUTRITION");

        when(aiService.generateResponse(any(AIServiceRequest.class)))
                .thenReturn("{\"dailyCalories\":2200}");

        mockMvc.perform(
                post("/api/ai/nutrition")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.response")
                                .value("{\"dailyCalories\":2200}"));

        verify(aiService)
                .generateResponse(any(AIServiceRequest.class));
    }


    @Test
    void chat_shouldReturnAIResponse()
            throws Exception {

        AIServiceRequest request = new AIServiceRequest();

        request.setUserId(1L);
        request.setPrompt("How can I lose weight?");
        request.setRequestType("CHAT");

        when(aiService.generateResponse(any(AIServiceRequest.class)))
                .thenReturn(
                        "Maintain a calorie deficit and exercise regularly.");

        mockMvc.perform(
                post("/api/ai/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.response")
                                .value(
                                        "Maintain a calorie deficit and exercise regularly."));

        verify(aiService)
                .generateResponse(any(AIServiceRequest.class));
    }


    @Test
    void getHistory_shouldReturnUserHistory()
            throws Exception {

        AIHistory history1 = new AIHistory();

        history1.setId(1L);
        history1.setUserId(1L);
        history1.setPrompt("Create a workout plan");
        history1.setResponse("Monday: Chest and Triceps");
        history1.setRequestType("WORKOUT");


        AIHistory history2 = new AIHistory();

        history2.setId(2L);
        history2.setUserId(1L);
        history2.setPrompt("Give me a diet plan");
        history2.setResponse("2200 calories per day");
        history2.setRequestType("NUTRITION");


        List<AIHistory> historyList =
                Arrays.asList(history1, history2);

        when(aiHistoryRepository
                .findByUserIdOrderByCreatedAtDesc(1L))
                .thenReturn(historyList);


        mockMvc.perform(
                get("/api/ai/history/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(
                        jsonPath("$[0].userId")
                                .value(1))
                .andExpect(
                        jsonPath("$[0].requestType")
                                .value("WORKOUT"))
                .andExpect(
                        jsonPath("$[1].requestType")
                                .value("NUTRITION"));


        verify(aiHistoryRepository)
                .findByUserIdOrderByCreatedAtDesc(1L);
    }


    @Test
    void generateWorkout_shouldReturnBadRequest_whenRequestIsInvalid()
            throws Exception {

        AIServiceRequest request = new AIServiceRequest();

        // userId, prompt and requestType intentionally missing

        mockMvc.perform(
                post("/api/ai/workout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}