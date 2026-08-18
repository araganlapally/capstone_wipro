package com.wipro.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.wipro.entity.HydrationLog;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class HydrationLogRepositoryTest {

    @Autowired
    private HydrationLogRepository hydrationLogRepository;

    @Test
    void findByUserIdAndLogDate_ShouldReturnHydrationLog() {

        HydrationLog hydration = new HydrationLog();

        hydration.setUserId(1L);
        hydration.setLogDate(LocalDate.now());
        hydration.setWaterIntake(2000);
        hydration.setWaterGoal(3000);

        hydrationLogRepository.save(hydration);

        Optional<HydrationLog> result =
                hydrationLogRepository.findByUserIdAndLogDate(
                        1L,
                        LocalDate.now());

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getUserId());
        assertEquals(2000, result.get().getWaterIntake());
        assertEquals(3000, result.get().getWaterGoal());
    }
}