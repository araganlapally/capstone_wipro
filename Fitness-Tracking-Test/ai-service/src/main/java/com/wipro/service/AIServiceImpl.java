package com.wipro.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {

    private final RestTemplate restTemplate;

    @Value("${ollama.url:http://localhost:11434}")
    private String ollamaUrl;

    @Value("${ollama.model:llama3}")
    private String ollamaModel;

    @Override
    public String generateResponse(String prompt) {

        String url = ollamaUrl + "/api/generate";

        Map<String, Object> requestBody =
                Map.of(
                        "model", ollamaModel,
                        "prompt", prompt,
                        "stream", false
                );

        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity =
                new HttpEntity<>(
                        requestBody,
                        headers);

        ResponseEntity<Map> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        Map.class);

        if (response.getBody() == null
                || response.getBody().get("response") == null) {

            throw new RuntimeException(
                    "Invalid response received from AI model");
        }

        return response.getBody()
                .get("response")
                .toString();
    }
}