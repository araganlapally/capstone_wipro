package com.wipro.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.dto.ProgressRequest;
import com.wipro.dto.ProgressResponse;
import com.wipro.security.JwtFilter;
import com.wipro.service.ProgressService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ProgressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProgressService progressService;

    @MockitoBean
    private JwtFilter jwtFilter;

    @Test
    void saveProgress_ShouldReturnCreated() throws Exception {

        ProgressRequest request = new ProgressRequest();

        request.setUserId(1L);
        request.setWeight(75.5);
        request.setBodyFat(18.5);
        request.setRecordedDate(
                LocalDate.of(2026, 8, 11));

        ProgressResponse response = new ProgressResponse();

        response.setId(1L);
        response.setUserId(1L);
        response.setWeight(75.5);
        response.setBodyFat(18.5);
        response.setRecordedDate(
                LocalDate.of(2026, 8, 11));

        when(progressService.saveProgress(any(ProgressRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                post("/api/progress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.weight").value(75.5))
                .andExpect(jsonPath("$.bodyFat").value(18.5))
                .andExpect(
                        jsonPath("$.recordedDate")
                                .value("2026-08-11"));
    }

    @Test
    void getProgressByUserId_ShouldReturnProgressList()
            throws Exception {

        ProgressResponse response =
                new ProgressResponse();

        response.setId(1L);
        response.setUserId(1L);
        response.setWeight(75.5);
        response.setBodyFat(18.5);
        response.setRecordedDate(
                LocalDate.of(2026, 8, 11));

        when(progressService.getProgressByUserId(1L))
                .thenReturn(List.of(response));

        mockMvc.perform(
                get("/api/progress/user/1"))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.length()")
                                .value(1))
                .andExpect(
                        jsonPath("$[0].id")
                                .value(1))
                .andExpect(
                        jsonPath("$[0].userId")
                                .value(1))
                .andExpect(
                        jsonPath("$[0].weight")
                                .value(75.5))
                .andExpect(
                        jsonPath("$[0].bodyFat")
                                .value(18.5))
                .andExpect(
                        jsonPath("$[0].recordedDate")
                                .value("2026-08-11"));
    }

    @Test
    void updateProgress_ShouldReturnUpdatedProgress()
            throws Exception {

        ProgressRequest request =
                new ProgressRequest();

        request.setUserId(1L);
        request.setWeight(74.0);
        request.setBodyFat(17.0);
        request.setRecordedDate(
                LocalDate.of(2026, 8, 11));

        ProgressResponse response =
                new ProgressResponse();

        response.setId(1L);
        response.setUserId(1L);
        response.setWeight(74.0);
        response.setBodyFat(17.0);
        response.setRecordedDate(
                LocalDate.of(2026, 8, 11));

        when(progressService.updateProgress(
                eq(1L),
                any(ProgressRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                put("/api/progress/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.id")
                                .value(1))
                .andExpect(
                        jsonPath("$.userId")
                                .value(1))
                .andExpect(
                        jsonPath("$.weight")
                                .value(74.0))
                .andExpect(
                        jsonPath("$.bodyFat")
                                .value(17.0))
                .andExpect(
                        jsonPath("$.recordedDate")
                                .value("2026-08-11"));
    }
}