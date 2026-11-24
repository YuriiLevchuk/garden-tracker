package com.example.garden_tracker_backend.security;

import com.example.garden_tracker_backend.dto.GardenRequest;
import com.example.garden_tracker_backend.model.Garden;
import com.example.garden_tracker_backend.model.User;
import com.example.garden_tracker_backend.repository.GardenRepository;
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
public class GardenControllerSecurityTest extends AuthTest {

    private static User DIFFERENT_USER = null;
    private final RestTestClient client;
    private final ObjectMapper objectMapper;
    private final GardenRepository gardenRepository;
    private final UserRepository userRepository;

    @Autowired
    public GardenControllerSecurityTest(UserRepository userRepository, PasswordEncoder passwordEncoder, RestTestClient client, ObjectMapper objectMapper, GardenRepository gardenRepository) {
        super(userRepository, passwordEncoder, client);
        this.client = client;
        this.objectMapper = objectMapper;
        this.gardenRepository = gardenRepository;
        this.userRepository = userRepository;
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
        gardenRepository.deleteAll();
    }

    @Test
    public void givenNoAuth_whenCreateGarden_thenStatusUnauthorized() {
        var gardenRequest = new GardenRequest(TEST_GARDEN_NAME);
        client.post()
                .uri("/api/gardens")
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(gardenRequest))
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void givenValidAuth_whenCreateGarden_thenStatusCreated() {
        var gardenRequest = new GardenRequest(TEST_GARDEN_NAME);
        client.post()
                .uri("/api/gardens")
                .header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(gardenRequest))
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void givenNoAuth_whenGetAllGardens_thenStatusUnauthorized() {
        client.get()
                .uri("/api/gardens")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void givenValidAuth_whenGetAllGardens_thenStatusOk() {
        client.get()
                .uri("/api/gardens")
                .header("Authorization", "Bearer " + jwt)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void givenValidAuthAndGardenBelongsToUser_whenGetGarden_thenStatusOk() {
        var garden = Garden.builder()
                .name(TEST_GARDEN_NAME)
                .user(AUTH_USER)
                .build();
        gardenRepository.save(garden);
        client.get()
                .uri("/api/gardens/" + garden.getId())
                .header("Authorization", "Bearer " + jwt)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void givenNoAuth_whenGetGarden_thenStatusUnauthorized() {
        client.get()
                .uri("/api/gardens/1")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void givenGardenDoesNotExist_whenGetGarden_thenStatusForbidden() {
        client.get()
                .uri("/api/gardens/1")
                .header("Authorization", "Bearer " + jwt)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    public void givenGardenBelongsToDifferentUser_whenGetGarden_thenStatusForbidden() {
        var garden = Garden.builder()
                .name(TEST_GARDEN_NAME)
                .user(DIFFERENT_USER)
                .build();
        gardenRepository.save(garden);
        client.get()
                .uri("/api/gardens/" + garden.getId())
                .header("Authorization", "Bearer " + jwt)
                .exchange()
                .expectStatus().isForbidden();
    }
}
