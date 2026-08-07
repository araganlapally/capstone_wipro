package com.wipro.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.entity.FitnessProfile;
import com.wipro.service.UserService;
import com.wipro.security.JwtFilter;
import com.wipro.security.JwtUtil;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetProfile() throws Exception {

        FitnessProfile profile = new FitnessProfile();

        when(userService.getProfile(1L))
                .thenReturn(profile);

        mockMvc.perform(get("/api/users/1/profile"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateProfile() throws Exception {

        FitnessProfile profile = new FitnessProfile();

        when(userService.updateProfile(any(Long.class),
                any(FitnessProfile.class)))
                .thenReturn(profile);

        mockMvc.perform(put("/api/users/1/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(profile)))
                .andExpect(status().isOk());
    }
}