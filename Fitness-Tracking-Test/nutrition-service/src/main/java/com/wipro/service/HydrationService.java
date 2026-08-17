package com.wipro.service;

import com.wipro.dto.HydrationRequest;
import com.wipro.dto.HydrationResponse;

import jakarta.validation.Valid;

public interface HydrationService {

    HydrationResponse getTodayHydration(
            Long userId);

    HydrationResponse updateHydration(
           HydrationRequest request);
}