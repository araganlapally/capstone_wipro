package com.wipro.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ProgressServiceImpl implements ProgressService {

    private static final Logger logger =
            LoggerFactory.getLogger(ProgressServiceImpl.class);

    private final ProgressRepository progressRepository;
    private final ModelMapper modelMapper;
    private final UserServiceClient userServiceClient;

    @Override
    public ProgressResponse saveProgress(
            ProgressRequest request) {

        logger.info("Saving progress for userId: {}",
                request.getUserId());

        UserResponse user =
                userServiceClient.getUserById(
                        request.getUserId());

        if (user == null) {

            logger.error("User not found with userId: {}",
                    request.getUserId());

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

        logger.info(
                "Progress saved successfully with id: {}",
                saved.getId());

        return modelMapper.map(
                saved,
                ProgressResponse.class);
    }

    @Override
    public List<ProgressResponse> getProgressByUserId(
            Long userId) {

        logger.info(
                "Fetching progress records for userId: {}",
                userId);

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

        logger.info(
                "Updating progress record with id: {}",
                id);

        Progress progress =
                progressRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Progress not found with id : "
                                                + id));

        progress.setUserId(
                request.getUserId());

        progress.setWeight(
                request.getWeight());

        progress.setBodyFat(
                request.getBodyFat());

        progress.setRecordedDate(
                request.getRecordedDate());

        Progress updatedProgress =
                progressRepository.save(
                        progress);

        logger.info(
                "Progress updated successfully with id: {}",
                updatedProgress.getId());

        return modelMapper.map(
                updatedProgress,
                ProgressResponse.class);
    }
}