package com.wipro.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiServiceImpl implements GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Override
    public String generateWorkout(String prompt) {

        String url =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key="
            + apiKey;

        try {

            RestTemplate restTemplate = new RestTemplate();

            Map<String, Object> part = Map.of(
                    "text", prompt
            );

            Map<String, Object> content = Map.of(
                    "parts", List.of(part)
            );

            Map<String, Object> requestBody = Map.of(
                    "contents", List.of(content)
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(requestBody, headers);


            ResponseEntity<Map> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            request,
                            Map.class
                    );


            Map responseBody = response.getBody();

            List candidates =
                    (List) responseBody.get("candidates");

            Map candidate =
                    (Map) candidates.get(0);

            Map contentMap =
                    (Map) candidate.get("content");

            List parts =
                    (List) contentMap.get("parts");

            Map textPart =
                    (Map) parts.get(0);


            return textPart.get("text").toString();


        } catch (Exception e) {

            return "AI Workout Generation Failed: "
                    + e.getMessage();
        }
    }
}