package com.wipro.security;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;


import jakarta.servlet.FilterChain;



class JwtFilterTest {


    private JwtFilter jwtFilter;


    private JwtUtil jwtUtil;



    @BeforeEach
    void setup() {


        jwtUtil =
                Mockito.mock(JwtUtil.class);


        jwtFilter =
                new JwtFilter(jwtUtil);


        SecurityContextHolder
                .clearContext();
    }





    @Test
    void doFilter_WithValidToken_ShouldAuthenticate()
            throws Exception {


        String token =
                "valid-token";


        String username =
                "sai@test.com";



        MockHttpServletRequest request =
                new MockHttpServletRequest();


        request.addHeader(
                "Authorization",
                "Bearer " + token
        );



        MockHttpServletResponse response =
                new MockHttpServletResponse();



        FilterChain filterChain =
                Mockito.mock(FilterChain.class);



        when(jwtUtil.extractUsername(token))
                .thenReturn(username);



        jwtFilter.doFilter(
                request,
                response,
                filterChain
        );



        assertNotNull(
                SecurityContextHolder
                .getContext()
                .getAuthentication()
        );



        assertEquals(
                username,
                SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal()
        );
    }





    @Test
    void doFilter_WithNoToken_ShouldContinueFilter()
            throws Exception {


        MockHttpServletRequest request =
                new MockHttpServletRequest();



        MockHttpServletResponse response =
                new MockHttpServletResponse();



        FilterChain filterChain =
                Mockito.mock(FilterChain.class);



        jwtFilter.doFilter(
                request,
                response,
                filterChain
        );



        Mockito.verify(
                filterChain
        )
        .doFilter(
                request,
                response
        );
    }

}