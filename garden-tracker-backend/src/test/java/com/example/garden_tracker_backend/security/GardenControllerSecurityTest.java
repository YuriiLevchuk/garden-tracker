package com.example.garden_tracker_backend.security;

import com.example.garden_tracker_backend.dto.GardenRequest;
import com.example.garden_tracker_backend.repository.GardenRepository;
import com.example.garden_tracker_backend.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
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

    private final RestTestClient client;
    private final ObjectMapper objectMapper;

    @Autowired
    public GardenControllerSecurityTest(UserRepository userRepository, PasswordEncoder passwordEncoder, RestTestClient client, ObjectMapper objectMapper) {
        super(userRepository, passwordEncoder, client);
        this.client = client;
        this.objectMapper = objectMapper;
    }

    @AfterAll
    public static void cleanUp(@Autowired GardenRepository gardenRepository) {
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
}
