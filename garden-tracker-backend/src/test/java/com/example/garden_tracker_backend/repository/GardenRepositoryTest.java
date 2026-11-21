package com.example.garden_tracker_backend.repository;

import com.example.garden_tracker_backend.IntegrationTest;
import com.example.garden_tracker_backend.model.Garden;
import com.example.garden_tracker_backend.model.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.List;

import static com.example.garden_tracker_backend.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class GardenRepositoryTest extends IntegrationTest {

    private final GardenRepository gardenRepository;
    private final UserRepository userRepository;

    @Autowired
    public GardenRepositoryTest(GardenRepository gardenRepository, UserRepository userRepository) {
        this.gardenRepository = gardenRepository;
        this.userRepository = userRepository;
    }

    @Test
    public void givenUserHasGardens_whenFindGardensByUser_thenListNotEmpty() {
        var user = User.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();
        var garden = Garden.builder()
                .name(TEST_GARDEN_NAME)
                .user(user)
                .build();
        userRepository.save(user);
        gardenRepository.save(garden);
        var result = gardenRepository.findGardensByUser(user);
        assertEquals(1, result.size());
    }

    @Test
    public void givenUserHasNoGardens_whenFindGardensByUser_thenListEmpty() {
        var user = User.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();
        userRepository.save(user);
        var result = gardenRepository.findGardensByUser(user);
        assertTrue(result.isEmpty());
    }

    @Test
    public void givenGardenExists_whenExistsByNameAndUser_thenReturnTrue() {
        var user = User.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();
        var garden = Garden.builder()
                .name(TEST_GARDEN_NAME)
                .user(user)
                .build();
        userRepository.save(user);
        gardenRepository.save(garden);
        var result = gardenRepository.existsByNameAndUser(TEST_GARDEN_NAME, user);
        assertTrue(result);
    }

    @Test
    public void givenGardenNotExist_whenExistsByNameAndUser_thenReturnFalse() {
        var user = User.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();
        userRepository.save(user);
        var result = gardenRepository.existsByNameAndUser(TEST_GARDEN_NAME, user);
        assertFalse(result);
    }

    @Test
    public void givenGardenExistsWithDifferentUser_whenExistsByNameAndUser_thenReturnFalse() {
        var user1 = User.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();
        var user2 = User.builder()
                .email("user2@email.com")
                .password(TEST_PASSWORD)
                .build();
        var garden = Garden.builder()
                .name(TEST_GARDEN_NAME)
                .user(user1)
                .build();
        userRepository.saveAll(List.of(user1, user2));
        gardenRepository.save(garden);
        var result = gardenRepository.existsByNameAndUser(TEST_GARDEN_NAME, user2);
        assertFalse(result);
    }
}
