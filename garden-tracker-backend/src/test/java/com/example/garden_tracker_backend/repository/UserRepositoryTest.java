package com.example.garden_tracker_backend.repository;

import com.example.garden_tracker_backend.IntegrationTest;
import com.example.garden_tracker_backend.model.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import static com.example.garden_tracker_backend.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class UserRepositoryTest extends IntegrationTest {

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    public void givenUserExists_whenFindByEmail_thenOptionalPresent() {
        var user = User.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();
        userRepository.save(user);

        var result = userRepository.findByEmail(user.getEmail());

        assertTrue(result.isPresent());
    }

    @Test
    public void givenUserNotExist_whenFindByEmail_thenOptionalEmpty() {
        var result = userRepository.findByEmail(TEST_EMAIL);

        assertFalse(result.isPresent());
    }
}
