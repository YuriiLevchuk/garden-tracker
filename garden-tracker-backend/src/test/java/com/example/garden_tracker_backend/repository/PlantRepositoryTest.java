package com.example.garden_tracker_backend.repository;

import com.example.garden_tracker_backend.IntegrationTest;
import com.example.garden_tracker_backend.model.Garden;
import com.example.garden_tracker_backend.model.Plant;
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
public class PlantRepositoryTest extends IntegrationTest {

    private final GardenRepository gardenRepository;
    private final PlantRepository plantRepository;
    private final UserRepository userRepository;

    @Autowired
    public PlantRepositoryTest(GardenRepository gardenRepository, PlantRepository plantRepository, UserRepository userRepository) {
        this.gardenRepository = gardenRepository;
        this.plantRepository = plantRepository;
        this.userRepository = userRepository;
    }

    @Test
    public void givenGardenHasPlants_whenFindAllByGardenId_thenListNotEmpty() {
        var garden = Garden.builder()
                .name(TEST_GARDEN_NAME)
                .build();
        var plant = Plant.builder()
                .garden(garden)
                .build();
        gardenRepository.save(garden);
        plantRepository.save(plant);
        var result = plantRepository.findAllByGardenId(garden.getId());
        assertEquals(1, result.size());
    }

    @Test
    public void givenGardenHasNoPlants_whenFindAllByGardenId_thenListEmpty() {
        var garden = Garden.builder()
                .name(TEST_GARDEN_NAME)
                .build();
        gardenRepository.save(garden);
        var result = plantRepository.findAllByGardenId(garden.getId());
        assertTrue(result.isEmpty());
    }

    @Test
    public void givenPlantExists_whenExistsByIdAndGardenUser_thenReturnTrue() {
        var user = User.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();
        userRepository.save(user);
        var garden = Garden.builder()
                .name(TEST_GARDEN_NAME)
                .user(user)
                .build();
        gardenRepository.save(garden);
        var plant = Plant.builder()
                .garden(garden)
                .build();
        plantRepository.save(plant);
        var result = plantRepository.existsByIdAndGardenUser(plant.getId(), user);
        assertTrue(result);
    }

    @Test
    public void givenPlantNotExist_whenExistsByIdAndGardenUser_thenReturnFalse() {
        var user = User.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();
        userRepository.save(user);
        var result = plantRepository.existsByIdAndGardenUser(1L, user);
        assertFalse(result);
    }

    @Test
    public void givenPlantExistWithDifferentUser_whenExistsByIdAndGardenUser_thenReturnFalse() {
        var user1 = User.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();
        var user2 = User.builder()
                .email("user2@email.com")
                .password(TEST_PASSWORD)
                .build();
        userRepository.saveAll(List.of(user1, user2));
        var garden = Garden.builder()
                .name(TEST_GARDEN_NAME)
                .user(user1)
                .build();
        gardenRepository.save(garden);
        var plant = Plant.builder()
                .garden(garden)
                .build();
        plantRepository.save(plant);
        var result = plantRepository.existsByIdAndGardenUser(plant.getId(), user2);
        assertFalse(result);
    }
}
