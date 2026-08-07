package com.wipro.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiServiceImpl implements GeminiService {

    
    @Override
    public String generateWorkout(String prompt) {

    	String url = "http://localhost:11434/api/generate";


        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> requestBody = Map.of(
                "model", "llama3",
                "prompt", prompt,
                "stream", false
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity =
                new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        Map.class);

        return response.getBody().get("response").toString();
    }
}