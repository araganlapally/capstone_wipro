package com.wipro.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.dto.AuthResponse;
import com.wipro.dto.LoginRequest;
import com.wipro.dto.RegisterRequest;
import com.wipro.service.AuthService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
                .standaloneSetup(authController)
                .setValidator(new LocalValidatorFactoryBean())
                .build();
    }

    @Test
    void register_ShouldReturnCreated() throws Exception {

        RegisterRequest request = new RegisterRequest();

        request.setFullName("John Doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("password123");

        AuthResponse response = new AuthResponse();

        when(authService.register(any(RegisterRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void login_ShouldReturnOk() throws Exception {

        LoginRequest request = new LoginRequest();

        request.setEmail("john.doe@example.com");
        request.setPassword("password123");

        AuthResponse response = new AuthResponse();

        when(authService.login(any(LoginRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void register_ShouldReturnBadRequest_WhenRequestIsInvalid()
            throws Exception {

        RegisterRequest request = new RegisterRequest();

        request.setFullName("");
        request.setEmail("invalid-email");
        request.setPassword("123");

        mockMvc.perform(
                post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_ShouldReturnBadRequest_WhenRequestIsInvalid()
            throws Exception {

        LoginRequest request = new LoginRequest();

        request.setEmail("");
        request.setPassword("");

        mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}