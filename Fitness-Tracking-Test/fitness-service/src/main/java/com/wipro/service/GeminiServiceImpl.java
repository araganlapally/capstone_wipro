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
    			"https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key="
    			+ apiKey;


        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> requestBody = Map.of(
                "contents",
                List.of(
                        Map.of(
                                "parts",
                                List.of(
                                        Map.of("text", prompt)
                                )
                        )
                )
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

        try {

            List<?> candidates =
                    (List<?>) response.getBody().get("candidates");

            Map<?, ?> candidate =
                    (Map<?, ?>) candidates.get(0);

            Map<?, ?> content =
                    (Map<?, ?>) candidate.get("content");

            List<?> parts =
                    (List<?>) content.get("parts");

            Map<?, ?> part =
                    (Map<?, ?>) parts.get(0);

            return part.get("text").toString();

        } catch (Exception e) {
            e.printStackTrace();

            return """
                   AI Workout Generation Failed

                   Reason:
                   %s
                   """.formatted(e.getMessage());
        }
    }
}