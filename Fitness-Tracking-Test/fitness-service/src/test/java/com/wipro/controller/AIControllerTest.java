package com.wipro.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.dto.AIChatRequest;
import com.wipro.dto.UserProfileResponse;
import com.wipro.service.AIService;
import com.wipro.service.UserServiceClient;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AIService aiService;

    @MockBean
    private UserServiceClient userServiceClient;

    @Test
    void ask_shouldReturnPersonalizedAnswer() throws Exception {

        // Arrange
        AIChatRequest request = new AIChatRequest();
        request.setUserId(1L);
        request.setQuestion("How can I lose weight?");

        UserProfileResponse profile = new UserProfileResponse();

        profile.setAge(25);
        profile.setHeight(175.0);
        profile.setWeight(80.0);
        profile.setGender("Male");
        profile.setGoal("Weight Loss");

        when(userServiceClient.getProfile(1L))
                .thenReturn(profile);

        when(aiService.generateWorkout(any(String.class)))
                .thenReturn("You should follow a calorie deficit and exercise regularly.");

        // Act + Assert
        mockMvc.perform(
                post("/ai/ask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isOk())
        .andExpect(
                jsonPath("$.answer")
                        .value("You should follow a calorie deficit and exercise regularly.")
        );

        // Verify User Service was called
        verify(userServiceClient).getProfile(1L);

        // Verify Gemini Service was called
        verify(aiService).generateWorkout(any(String.class));
    }
}