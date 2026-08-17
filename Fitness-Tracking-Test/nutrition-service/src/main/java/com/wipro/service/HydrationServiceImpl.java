package com.wipro.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.wipro.dto.HydrationRequest;
import com.wipro.dto.HydrationResponse;
import com.wipro.entity.HydrationLog;
import com.wipro.repository.HydrationLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HydrationServiceImpl
        implements HydrationService {

    private final HydrationLogRepository hydrationLogRepository;

    @Override
    public HydrationResponse getTodayHydration(
            Long userId) {

        HydrationLog hydration =
                hydrationLogRepository
                        .findByUserIdAndLogDate(
                                userId,
                                LocalDate.now())
                        .orElseGet(() -> {

                            HydrationLog newLog =
                                    new HydrationLog();

                            newLog.setUserId(userId);
                            newLog.setLogDate(LocalDate.now());
                            newLog.setWaterIntake(0);
                            newLog.setWaterGoal(3000);

                            return newLog;
                        });

        return mapToResponse(hydration);
    }

    @Override
    public HydrationResponse updateHydration(
            HydrationRequest request) {

        LocalDate today = LocalDate.now();

        HydrationLog hydration =
                hydrationLogRepository
                        .findByUserIdAndLogDate(
                                request.getUserId(),
                                today)
                        .orElseGet(() -> {

                            HydrationLog newLog =
                                    new HydrationLog();

                            newLog.setUserId(
                                    request.getUserId());

                            newLog.setLogDate(today);

                            return newLog;
                        });

        hydration.setWaterIntake(
                request.getWaterIntake());

        hydration.setWaterGoal(
                request.getWaterGoal());

        HydrationLog saved =
                hydrationLogRepository.save(
                        hydration);

        return mapToResponse(saved);
    }

    private HydrationResponse mapToResponse(
            HydrationLog hydration) {

        HydrationResponse response =
                new HydrationResponse();

        response.setId(
                hydration.getId());

        response.setUserId(
                hydration.getUserId());

        response.setDate(
                hydration.getLogDate());

        response.setWaterIntake(
                hydration.getWaterIntake());

        response.setWaterGoal(
                hydration.getWaterGoal());

        int intake =
                hydration.getWaterIntake() == null
                        ? 0
                        : hydration.getWaterIntake();

        int goal =
                hydration.getWaterGoal() == null
                        ? 3000
                        : hydration.getWaterGoal();

        response.setRemainingWater(
                Math.max(goal - intake, 0));

        int percentage =
                goal > 0
                        ? (int) (((double) intake / goal) * 100)
                        : 0;

        response.setPercentage(
                Math.min(percentage, 100));

        return response;
    }
}