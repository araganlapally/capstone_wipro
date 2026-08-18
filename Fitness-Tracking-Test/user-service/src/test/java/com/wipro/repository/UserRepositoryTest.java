package com.wipro.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.wipro.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail_ShouldReturnUser_WhenEmailExists() {

        User user = User.builder()
                .fullName("John Doe")
                .email("john.doe@example.com")
                .password("password123")
                .role("USER")
                .build();

        userRepository.save(user);

        Optional<User> result =
                userRepository.findByEmail(
                        "john.doe@example.com");

        assertTrue(result.isPresent());
        assertEquals(
                "john.doe@example.com",
                result.get().getEmail());
        assertEquals(
                "John Doe",
                result.get().getFullName());
    }

    @Test
    void findByEmail_ShouldReturnEmpty_WhenEmailDoesNotExist() {

        Optional<User> result =
                userRepository.findByEmail(
                        "notfound@example.com");

        assertFalse(result.isPresent());
    }

    @Test
    void existsByEmail_ShouldReturnTrue_WhenEmailExists() {

        User user = User.builder()
                .fullName("Jane Smith")
                .email("jane.smith@example.com")
                .password("password123")
                .role("USER")
                .build();

        userRepository.save(user);

        boolean exists =
                userRepository.existsByEmail(
                        "jane.smith@example.com");

        assertTrue(exists);
    }

    @Test
    void existsByEmail_ShouldReturnFalse_WhenEmailDoesNotExist() {

        boolean exists =
                userRepository.existsByEmail(
                        "unknown@example.com");

        assertFalse(exists);
    }
}