package com.wipro.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.entity.AIHistory;

@SpringBootTest
class AIHistoryRepositoryTest {

    @Autowired
    private AIHistoryRepository aiHistoryRepository;


    @Test
    void findByUserIdOrderByCreatedAtDesc_shouldReturnUserHistory() {

        AIHistory history =
                new AIHistory();

        history.setUserId(100L);

        history.setPrompt(
                "Create a workout plan.");

        history.setResponse(
                "Monday: Chest and Triceps");

        history.setRequestType(
                "WORKOUT");

        history.setCreatedAt(
                LocalDateTime.now());


        aiHistoryRepository.save(history);


        List<AIHistory> result =
                aiHistoryRepository
                        .findByUserIdOrderByCreatedAtDesc(100L);


        assertFalse(result.isEmpty());

        assertEquals(
                100L,
                result.get(0).getUserId());

        assertEquals(
                "WORKOUT",
                result.get(0).getRequestType());
    }
}