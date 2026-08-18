package com.wipro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wipro.dto.ProgressRequest;
import com.wipro.dto.ProgressResponse;
import com.wipro.dto.UserResponse;
import com.wipro.entity.Progress;
import com.wipro.exception.ResourceNotFoundException;
import com.wipro.repository.ProgressRepository;

class ProgressServiceImplTest {

    @Mock
    private ProgressRepository progressRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserServiceClient userServiceClient;

    @InjectMocks
    private ProgressServiceImpl progressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveProgress_ShouldSaveSuccessfully() {

        // Arrange
        ProgressRequest request = new ProgressRequest();

        request.setUserId(1L);
        request.setWeight(75.0);
        request.setBodyFat(18.5);
        request.setRecordedDate(
                LocalDate.of(2026, 8, 11)
        );

        UserResponse user = new UserResponse();
        user.setId(1L);

        Progress progress = new Progress();

        progress.setId(10L);
        progress.setUserId(1L);
        progress.setWeight(75.0);
        progress.setBodyFat(18.5);
        progress.setRecordedDate(
                LocalDate.of(2026, 8, 11)
        );

        ProgressResponse expectedResponse =
                new ProgressResponse();

        expectedResponse.setId(10L);
        expectedResponse.setUserId(1L);
        expectedResponse.setWeight(75.0);
        expectedResponse.setBodyFat(18.5);
        expectedResponse.setRecordedDate(
                LocalDate.of(2026, 8, 11)
        );

        when(userServiceClient.getUserById(1L))
                .thenReturn(user);

        when(modelMapper.map(
                request,
                Progress.class))
                .thenReturn(progress);

        when(progressRepository.save(progress))
                .thenReturn(progress);

        when(modelMapper.map(
                progress,
                ProgressResponse.class))
                .thenReturn(expectedResponse);

        // Act
        ProgressResponse result =
                progressService.saveProgress(request);

        // Assert
        assertNotNull(result);

        assertEquals(
                10L,
                result.getId()
        );

        assertEquals(
                1L,
                result.getUserId()
        );

        assertEquals(
                75.0,
                result.getWeight()
        );

        assertEquals(
                18.5,
                result.getBodyFat()
        );

        verify(userServiceClient)
                .getUserById(1L);

        verify(progressRepository)
                .save(progress);
    }

    @Test
    void saveProgress_WhenUserNotFound_ShouldThrowException() {

        // Arrange
        ProgressRequest request =
                new ProgressRequest();

        request.setUserId(100L);

        when(userServiceClient.getUserById(100L))
                .thenReturn(null);

        // Act + Assert
        assertThrows(
                ResourceNotFoundException.class,
                () ->
                        progressService
                                .saveProgress(request)
        );

        verify(userServiceClient)
                .getUserById(100L);
    }

    @Test
    void getProgressByUserId_ShouldReturnList() {

        // Arrange
        Progress progress1 =
                new Progress();

        progress1.setId(1L);
        progress1.setUserId(1L);
        progress1.setWeight(80.0);
        progress1.setBodyFat(20.0);

        Progress progress2 =
                new Progress();

        progress2.setId(2L);
        progress2.setUserId(1L);
        progress2.setWeight(78.0);
        progress2.setBodyFat(19.0);

        ProgressResponse response1 =
                new ProgressResponse();

        response1.setId(1L);
        response1.setUserId(1L);
        response1.setWeight(80.0);
        response1.setBodyFat(20.0);

        ProgressResponse response2 =
                new ProgressResponse();

        response2.setId(2L);
        response2.setUserId(1L);
        response2.setWeight(78.0);
        response2.setBodyFat(19.0);

        when(progressRepository.findByUserId(1L))
                .thenReturn(
                        List.of(progress1, progress2)
                );

        when(modelMapper.map(
                progress1,
                ProgressResponse.class))
                .thenReturn(response1);

        when(modelMapper.map(
                progress2,
                ProgressResponse.class))
                .thenReturn(response2);

        // Act
        List<ProgressResponse> result =
                progressService
                        .getProgressByUserId(1L);

        // Assert
        assertNotNull(result);

        assertEquals(
                2,
                result.size()
        );

        assertEquals(
                80.0,
                result.get(0).getWeight()
        );

        assertEquals(
                78.0,
                result.get(1).getWeight()
        );

        verify(progressRepository)
                .findByUserId(1L);
    }

    @Test
    void getProgressByUserId_WhenNoProgress_ShouldReturnEmptyList() {

        // Arrange
        when(progressRepository.findByUserId(1L))
                .thenReturn(List.of());

        // Act
        List<ProgressResponse> result =
                progressService
                        .getProgressByUserId(1L);

        // Assert
        assertNotNull(result);

        assertEquals(
                0,
                result.size()
        );

        verify(progressRepository)
                .findByUserId(1L);
    }

    @Test
    void updateProgress_ShouldUpdateSuccessfully() {

        // Arrange
        ProgressRequest request =
                new ProgressRequest();

        request.setUserId(1L);
        request.setWeight(72.0);
        request.setBodyFat(16.0);
        request.setRecordedDate(
                LocalDate.of(2026, 8, 11)
        );

        Progress existingProgress =
                new Progress();

        existingProgress.setId(10L);
        existingProgress.setUserId(1L);
        existingProgress.setWeight(75.0);
        existingProgress.setBodyFat(18.0);
        existingProgress.setRecordedDate(
                LocalDate.of(2026, 8, 1)
        );

        ProgressResponse expectedResponse =
                new ProgressResponse();

        expectedResponse.setId(10L);
        expectedResponse.setUserId(1L);
        expectedResponse.setWeight(72.0);
        expectedResponse.setBodyFat(16.0);
        expectedResponse.setRecordedDate(
                LocalDate.of(2026, 8, 11)
        );

        when(progressRepository.findById(10L))
                .thenReturn(
                        Optional.of(existingProgress)
                );

        when(progressRepository.save(existingProgress))
                .thenReturn(existingProgress);

        when(modelMapper.map(
                existingProgress,
                ProgressResponse.class))
                .thenReturn(expectedResponse);

        // Act
        ProgressResponse result =
                progressService.updateProgress(
                        10L,
                        request
                );

        // Assert
        assertNotNull(result);

        assertEquals(
                10L,
                result.getId()
        );

        assertEquals(
                72.0,
                result.getWeight()
        );

        assertEquals(
                16.0,
                result.getBodyFat()
        );

        assertEquals(
                LocalDate.of(2026, 8, 11),
                result.getRecordedDate()
        );

        verify(progressRepository)
                .findById(10L);

        verify(progressRepository)
                .save(existingProgress);
    }

    @Test
    void updateProgress_WhenNotFound_ShouldThrowException() {

        // Arrange
        ProgressRequest request =
                new ProgressRequest();

        request.setUserId(1L);
        request.setWeight(72.0);
        request.setBodyFat(16.0);

        when(progressRepository.findById(100L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                ResourceNotFoundException.class,
                () ->
                        progressService
                                .updateProgress(
                                        100L,
                                        request
                                )
        );

        verify(progressRepository)
                .findById(100L);
    }
}