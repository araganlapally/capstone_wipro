package com.wipro.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.wipro.entity.FitnessProfile;
import com.wipro.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FitnessProfileRepositoryTest {

    @Autowired
    private FitnessProfileRepository fitnessProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUserId_ShouldReturnProfile_WhenProfileExists() {

        User user = User.builder()
                .fullName("John Doe")
                .email("john.doe@example.com")
                .password("password123")
                .role("USER")
                .build();

        User savedUser = userRepository.save(user);

        FitnessProfile profile = FitnessProfile.builder()
                .age(25)
                .height(175.0)
                .weight(70.0)
                .goal("WEIGHT_LOSS")
                .gender("MALE")
                .user(savedUser)
                .build();

        fitnessProfileRepository.save(profile);

        Optional<FitnessProfile> result =
                fitnessProfileRepository
                        .findByUserId(savedUser.getId());

        assertTrue(result.isPresent());

        assertEquals(
                25,
                result.get().getAge());

        assertEquals(
                175.0,
                result.get().getHeight());

        assertEquals(
                70.0,
                result.get().getWeight());

        assertEquals(
                "WEIGHT_LOSS",
                result.get().getGoal());

        assertEquals(
                savedUser.getId(),
                result.get().getUser().getId());
    }

    @Test
    void findByUserId_ShouldReturnEmpty_WhenProfileDoesNotExist() {

        Optional<FitnessProfile> result =
                fitnessProfileRepository
                        .findByUserId(999L);

        assertFalse(result.isPresent());
    }
}