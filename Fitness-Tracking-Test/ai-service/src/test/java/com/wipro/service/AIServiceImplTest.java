package com.wipro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.wipro.dto.AIServiceRequest;
import com.wipro.entity.AIHistory;
import com.wipro.repository.AIHistoryRepository;

@ExtendWith(MockitoExtension.class)
class AIServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private AIHistoryRepository aiHistoryRepository;

    @InjectMocks
    private AIServiceImpl aiService;


    @BeforeEach
    void setUp() {

        ReflectionTestUtils.setField(
                aiService,
                "ollamaUrl",
                "http://localhost:11434");

        ReflectionTestUtils.setField(
                aiService,
                "ollamaModel",
                "llama3");
    }


    @Test
    void generateResponse_shouldReturnAIResponse()
            throws Exception {

        AIServiceRequest request =
                new AIServiceRequest();

        request.setUserId(1L);
        request.setPrompt(
                "Create a workout plan.");
        request.setRequestType(
                "WORKOUT");


        Map<String, Object> responseBody =
                Map.of(
                        "response",
                        "Monday: Chest and Triceps");


        ResponseEntity<Map> responseEntity =
                new ResponseEntity<>(
                        responseBody,
                        HttpStatus.OK);


        when(restTemplate.exchange(
                any(String.class),
                any(org.springframework.http.HttpMethod.class),
                any(HttpEntity.class),
                any(Class.class)))
                .thenReturn(responseEntity);


        String result =
                aiService.generateResponse(request);


        assertEquals(
                "Monday: Chest and Triceps",
                result);


        verify(aiHistoryRepository)
                .save(any(AIHistory.class));
    }


    @Test
    void generateResponse_shouldSaveCorrectHistory()
            throws Exception {

        AIServiceRequest request =
                new AIServiceRequest();

        request.setUserId(10L);
        request.setPrompt(
                "Give me a nutrition plan.");
        request.setRequestType(
                "NUTRITION");


        Map<String, Object> responseBody =
                Map.of(
                        "response",
                        "2200 calories per day");


        ResponseEntity<Map> responseEntity =
                ResponseEntity.ok(responseBody);


        when(restTemplate.exchange(
                any(String.class),
                any(org.springframework.http.HttpMethod.class),
                any(HttpEntity.class),
                any(Class.class)))
                .thenReturn(responseEntity);


        aiService.generateResponse(request);


        verify(aiHistoryRepository)
                .save(any(AIHistory.class));
    }


    @Test
    void generateResponse_shouldThrowException_whenAIResponseIsMissing()
            throws Exception {

        AIServiceRequest request =
                new AIServiceRequest();

        request.setUserId(1L);
        request.setPrompt(
                "Create workout.");
        request.setRequestType(
                "WORKOUT");


        Map<String, Object> responseBody =
                Map.of();


        ResponseEntity<Map> responseEntity =
                ResponseEntity.ok(responseBody);


        when(restTemplate.exchange(
                any(String.class),
                any(org.springframework.http.HttpMethod.class),
                any(HttpEntity.class),
                any(Class.class)))
                .thenReturn(responseEntity);


        assertThrows(
                RuntimeException.class,
                () -> aiService.generateResponse(request));
    }


    @Test
    void generateResponse_shouldNotSaveHistory_whenAIResponseIsInvalid()
            throws Exception {

        AIServiceRequest request =
                new AIServiceRequest();

        request.setUserId(1L);
        request.setPrompt(
                "Create workout.");
        request.setRequestType(
                "WORKOUT");


        Map<String, Object> responseBody =
                Map.of();


        ResponseEntity<Map> responseEntity =
                ResponseEntity.ok(responseBody);


        when(restTemplate.exchange(
                any(String.class),
                any(org.springframework.http.HttpMethod.class),
                any(HttpEntity.class),
                any(Class.class)))
                .thenReturn(responseEntity);


        assertThrows(
                RuntimeException.class,
                () -> aiService.generateResponse(request));


        // History must NOT be saved
        org.mockito.Mockito.verify(
                aiHistoryRepository,
                org.mockito.Mockito.never())
                .save(any(AIHistory.class));
    }
}