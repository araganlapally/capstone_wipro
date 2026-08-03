package com.wipro.service;

import java.util.List;

import com.wipro.dto.ProgressRequest;
import com.wipro.dto.ProgressResponse;

public interface ProgressService {

    ProgressResponse saveProgress(
            ProgressRequest request);

    List<ProgressResponse> getProgressByUserId(
            Long userId);
    
    ProgressResponse updateProgress(
            Long id,
            ProgressRequest request);
}