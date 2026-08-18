package com.wipro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wipro.dto.HydrationRequest;
import com.wipro.dto.HydrationResponse;
import com.wipro.entity.HydrationLog;
import com.wipro.repository.HydrationLogRepository;

@ExtendWith(MockitoExtension.class)
class HydrationServiceImplTest {

    @Mock
    private HydrationLogRepository hydrationLogRepository;

    @InjectMocks
    private HydrationServiceImpl hydrationService;

    @Test
    void getTodayHydration_ShouldReturnExistingLog() {

        Long userId = 1L;
        LocalDate today = LocalDate.now();

        HydrationLog hydration =
                new HydrationLog();

        hydration.setId(10L);
        hydration.setUserId(userId);
        hydration.setLogDate(today);
        hydration.setWaterIntake(2000);
        hydration.setWaterGoal(3000);

        when(hydrationLogRepository
                .findByUserIdAndLogDate(userId, today))
                .thenReturn(Optional.of(hydration));

        HydrationResponse result =
                hydrationService.getTodayHydration(userId);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals(1L, result.getUserId());
        assertEquals(today, result.getDate());
        assertEquals(2000, result.getWaterIntake());
        assertEquals(3000, result.getWaterGoal());
        assertEquals(1000, result.getRemainingWater());
        assertEquals(66, result.getPercentage());

        verify(hydrationLogRepository, times(1))
                .findByUserIdAndLogDate(userId, today);
    }

    @Test
    void getTodayHydration_ShouldReturnDefault_WhenNoLogExists() {

        Long userId = 1L;
        LocalDate today = LocalDate.now();

        when(hydrationLogRepository
                .findByUserIdAndLogDate(userId, today))
                .thenReturn(Optional.empty());

        HydrationResponse result =
                hydrationService.getTodayHydration(userId);

        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        assertEquals(today, result.getDate());
        assertEquals(0, result.getWaterIntake());
        assertEquals(3000, result.getWaterGoal());
        assertEquals(3000, result.getRemainingWater());
        assertEquals(0, result.getPercentage());

        verify(hydrationLogRepository, times(1))
                .findByUserIdAndLogDate(userId, today);

        verify(hydrationLogRepository, never())
                .save(any(HydrationLog.class));
    }

    @Test
    void updateHydration_ShouldUpdateExistingLog() {

        Long userId = 1L;
        LocalDate today = LocalDate.now();

        HydrationLog existingLog =
                new HydrationLog();

        existingLog.setId(10L);
        existingLog.setUserId(userId);
        existingLog.setLogDate(today);
        existingLog.setWaterIntake(1000);
        existingLog.setWaterGoal(3000);

        HydrationRequest request =
                new HydrationRequest();

        request.setUserId(userId);
        request.setWaterIntake(2500);
        request.setWaterGoal(3000);

        when(hydrationLogRepository
                .findByUserIdAndLogDate(userId, today))
                .thenReturn(Optional.of(existingLog));

        when(hydrationLogRepository
                .save(any(HydrationLog.class)))
                .thenAnswer(
                        invocation ->
                                invocation.getArgument(0));

        HydrationResponse result =
                hydrationService.updateHydration(request);

        assertNotNull(result);
        assertEquals(2500, result.getWaterIntake());
        assertEquals(3000, result.getWaterGoal());
        assertEquals(500, result.getRemainingWater());
        assertEquals(83, result.getPercentage());

        verify(hydrationLogRepository, times(1))
                .findByUserIdAndLogDate(userId, today);

        verify(hydrationLogRepository, times(1))
                .save(existingLog);
    }

    @Test
    void updateHydration_ShouldCreateNewLog_WhenNoLogExists() {

        Long userId = 1L;
        LocalDate today = LocalDate.now();

        HydrationRequest request =
                new HydrationRequest();

        request.setUserId(userId);
        request.setWaterIntake(1500);
        request.setWaterGoal(3000);

        when(hydrationLogRepository
                .findByUserIdAndLogDate(userId, today))
                .thenReturn(Optional.empty());

        when(hydrationLogRepository
                .save(any(HydrationLog.class)))
                .thenAnswer(
                        invocation ->
                                invocation.getArgument(0));

        HydrationResponse result =
                hydrationService.updateHydration(request);

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(today, result.getDate());
        assertEquals(1500, result.getWaterIntake());
        assertEquals(3000, result.getWaterGoal());
        assertEquals(1500, result.getRemainingWater());
        assertEquals(50, result.getPercentage());

        verify(hydrationLogRepository, times(1))
                .findByUserIdAndLogDate(userId, today);

        verify(hydrationLogRepository, times(1))
                .save(any(HydrationLog.class));
    }

    @Test
    void updateHydration_ShouldCalculatePercentageNotAbove100() {

        Long userId = 1L;
        LocalDate today = LocalDate.now();

        HydrationRequest request =
                new HydrationRequest();

        request.setUserId(userId);
        request.setWaterIntake(4000);
        request.setWaterGoal(3000);

        when(hydrationLogRepository
                .findByUserIdAndLogDate(userId, today))
                .thenReturn(Optional.empty());

        when(hydrationLogRepository
                .save(any(HydrationLog.class)))
                .thenAnswer(
                        invocation ->
                                invocation.getArgument(0));

        HydrationResponse result =
                hydrationService.updateHydration(request);

        assertEquals(4000, result.getWaterIntake());
        assertEquals(0, result.getRemainingWater());
        assertEquals(100, result.getPercentage());
    }
}