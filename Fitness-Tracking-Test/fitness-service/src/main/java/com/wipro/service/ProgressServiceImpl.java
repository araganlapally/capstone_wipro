package com.wipro.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.wipro.dto.ProgressRequest;
import com.wipro.dto.ProgressResponse;
import com.wipro.dto.UserResponse;
import com.wipro.entity.Progress;
import com.wipro.exception.ResourceNotFoundException;
import com.wipro.repository.ProgressRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgressServiceImpl
        implements ProgressService {

    private final ProgressRepository progressRepository;
    private final ModelMapper modelMapper;
    private final UserServiceClient userServiceClient;

    @Override
    public ProgressResponse saveProgress(
            ProgressRequest request) {

        UserResponse user =
                userServiceClient.getUserById(
                        request.getUserId());

        if (user == null) {
            throw new ResourceNotFoundException(
                    "User not found");
        }

        Progress progress =
                modelMapper.map(
                        request,
                        Progress.class);

        Progress saved =
                progressRepository.save(
                        progress);

        return modelMapper.map(
                saved,
                ProgressResponse.class);
    }

    @Override
    public List<ProgressResponse>
            getProgressByUserId(Long userId) {

        return progressRepository
                .findByUserId(userId)
                .stream()
                .map(progress ->
                        modelMapper.map(
                                progress,
                                ProgressResponse.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public ProgressResponse updateProgress(
            Long id,
            ProgressRequest request) {

        Progress progress =
                progressRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Progress not found with id : "
                                        + id));

        progress.setUserId(request.getUserId());
        progress.setWeight(request.getWeight());
        progress.setBodyFat(request.getBodyFat());
        progress.setRecordedDate(
                request.getRecordedDate());

        Progress updatedProgress =
                progressRepository.save(progress);

        return modelMapper.map(
                updatedProgress,
                ProgressResponse.class);
    }
}