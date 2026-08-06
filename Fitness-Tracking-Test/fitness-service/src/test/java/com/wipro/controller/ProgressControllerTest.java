package com.wipro.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.config.TestSecurityConfig;
import com.wipro.dto.ProgressRequest;
import com.wipro.dto.ProgressResponse;
import com.wipro.security.JwtFilter;
import com.wipro.service.ProgressService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


@WebMvcTest(ProgressController.class)
@Import(TestSecurityConfig.class)
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


        ProgressRequest request =
                new ProgressRequest();

        request.setUserId(1L);
        request.setWeight(75.5);
        request.setBodyFat(18.5);
        request.setRecordedDate(
                LocalDate.now());


        ProgressResponse response =
                new ProgressResponse();

        response.setId(1L);
        response.setUserId(1L);
        response.setWeight(75.5);
        response.setBodyFat(18.5);



        when(progressService.saveProgress(any()))
                .thenReturn(response);



        mockMvc.perform(
                post("/api/progress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(request)
                ))
                .andExpect(status().isCreated())
                .andExpect(
                    jsonPath("$.id")
                    .value(1)
                )
                .andExpect(
                    jsonPath("$.weight")
                    .value(75.5)
                );
    }




    @Test
    void getProgressByUserId_ShouldReturnList()
            throws Exception {


        ProgressResponse response =
                new ProgressResponse();

        response.setId(1L);
        response.setUserId(1L);
        response.setWeight(80.0);



        when(progressService
                .getProgressByUserId(1L))
                .thenReturn(
                        List.of(response)
                );



        mockMvc.perform(
                get("/api/progress/user/1")
                )
                .andExpect(status().isOk())
                .andExpect(
                    jsonPath("$[0].userId")
                    .value(1)
                )
                .andExpect(
                    jsonPath("$[0].weight")
                    .value(80.0)
                );
    }




    @Test
    void updateProgress_ShouldReturnUpdatedProgress()
            throws Exception {


        ProgressRequest request =
                new ProgressRequest();

        request.setUserId(1L);
        request.setWeight(70.0);
        request.setBodyFat(15.0);
        request.setRecordedDate(
                LocalDate.now());



        ProgressResponse response =
                new ProgressResponse();

        response.setId(1L);
        response.setWeight(70.0);
        response.setBodyFat(15.0);



        when(progressService
                .updateProgress(any(), any()))
                .thenReturn(response);



        mockMvc.perform(
                put("/api/progress/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(request)
                ))
                .andExpect(status().isOk())
                .andExpect(
                    jsonPath("$.weight")
                    .value(70.0)
                );
    }

}