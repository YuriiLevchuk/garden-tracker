package com.example.garden_tracker_backend.security;

import com.example.garden_tracker_backend.dto.PlantRequest;
import com.example.garden_tracker_backend.model.Garden;
import com.example.garden_tracker_backend.model.Plant;
import com.example.garden_tracker_backend.model.User;
import com.example.garden_tracker_backend.repository.GardenRepository;
import com.example.garden_tracker_backend.repository.PlantRepository;
import com.example.garden_tracker_backend.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.client.RestTestClient;
import tools.jackson.databind.ObjectMapper;

import static com.example.garden_tracker_backend.TestConstants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlantControllerSecurityTest extends AuthTest {

    private static User DIFFERENT_USER = null;
    private final RestTestClient client;
    private final ObjectMapper objectMapper;
    private final GardenRepository gardenRepository;
    private final UserRepository userRepository;
    private final PlantRepository plantRepository;

    @Autowired
    public PlantControllerSecurityTest(UserRepository userRepository, PasswordEncoder passwordEncoder, RestTestClient client, ObjectMapper objectMapper, GardenRepository gardenRepository, PlantRepository plantRepository) {
        super(userRepository, passwordEncoder, client);
        this.client = client;
        this.objectMapper = objectMapper;
        this.gardenRepository = gardenRepository;
        this.userRepository = userRepository;
        this.plantRepository = plantRepository;
    }

    @BeforeAll
    public void createDifferentUser() {
        var user = User.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();
        userRepository.save(user);
        DIFFERENT_USER = user;
    }

    @AfterEach
    public void afterEach() {
        plantRepository.deleteAll();
        gardenRepository.deleteAll();
    }

    @Test
    public void givenValidAuthAndGardenBelongsToUser_whenCreatePlant_thenStatusCreated() {
        var garden = Garden.builder()
                .name(TEST_GARDEN_NAME)
                .user(AUTH_USER)
                .build();
        gardenRepository.save(garden);
        client.post()
                .uri("/api/plants")
                .header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(new PlantRequest(garden.getId(), 1L)))
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void givenNoAuth_whenCreatePlant_thenStatusUnauthorized() {
        client.post()
                .uri("/api/plants")
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(new PlantRequest(1L, 1L)))
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void givenGardenDoesNotExist_whenCreatePlant_thenStatusForbidden() {
        client.post()
                .uri("/api/plants")
                .header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(new PlantRequest(1L, 1L)))
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    public void givenGardenBelongsToDifferentUser_whenCreatePlant_thenStatusForbidden() {
        var garden = Garden.builder()
                .name(TEST_GARDEN_NAME)
                .user(DIFFERENT_USER)
                .build();
        gardenRepository.save(garden);
        client.post()
                .uri("/api/plants")
                .header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(new PlantRequest(1L, 1L)))
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    public void givenValidAuthAndGardenBelongsToUser_whenGetAllPlants_thenStatusOk() {
        var garden = Garden.builder()
                .name(TEST_GARDEN_NAME)
                .user(AUTH_USER)
                .build();
        gardenRepository.save(garden);
        client.get()
                .uri("/api/plants?garden_id=" + garden.getId())
                .header("Authorization", "Bearer " + jwt)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void givenNoAuth_whenGetAllPlants_thenStatusUnauthorized() {
        client.get()
                .uri("/api/plants?garden_id=1")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void givenGardenDoesNotExist_whenGetAllPlants_thenStatusForbidden() {
        client.get()
                .uri("/api/plants?garden_id=1")
                .header("Authorization", "Bearer " + jwt)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    public void givenGardenBelongsToDifferentUser_whenGetAllPlants_thenStatusForbidden() {
        var garden = Garden.builder()
                .name(TEST_GARDEN_NAME)
                .user(DIFFERENT_USER)
                .build();
        gardenRepository.save(garden);
        client.get()
                .uri("/api/plants?garden_id=" + garden.getId())
                .header("Authorization", "Bearer " + jwt)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    public void givenValidAuthAndPlantBelongsToUser_whenDeletePlant_thenStatusNoContent() {
        var garden = Garden.builder()
                .name(TEST_GARDEN_NAME)
                .user(AUTH_USER)
                .build();
        gardenRepository.save(garden);
        var plant = Plant.builder()
                .garden(garden)
                .build();
        plantRepository.save(plant);
        client.delete()
                .uri("/api/plants/" + plant.getId())
                .header("Authorization", "Bearer " + jwt)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void givenNoAuth_whenDeletePlant_thenStatusUnauthorized() {
        client.delete()
                .uri("/api/plants/1")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void givenPlantDoesNotExist_whenDeletePlant_thenStatusForbidden() {
        client.delete()
                .uri("/api/plants/1")
                .header("Authorization", "Bearer " + jwt)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    public void givenPlantBelongsToDifferentUser_whenDeletePlant_thenStatusForbidden() {
        var garden = Garden.builder()
                .name(TEST_GARDEN_NAME)
                .user(DIFFERENT_USER)
                .build();
        gardenRepository.save(garden);
        var plant = Plant.builder()
                .garden(garden)
                .build();
        plantRepository.save(plant);
        client.delete()
                .uri("/api/plants/" + plant.getId())
                .header("Authorization", "Bearer " + jwt)
                .exchange()
                .expectStatus().isForbidden();
    }
}
