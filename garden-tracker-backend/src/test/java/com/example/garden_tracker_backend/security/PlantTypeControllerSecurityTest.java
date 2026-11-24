package com.example.garden_tracker_backend.security;

import com.example.garden_tracker_backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlantTypeControllerSecurityTest extends AuthTest {

    private final RestTestClient client;

    @Autowired
    public PlantTypeControllerSecurityTest(UserRepository userRepository, PasswordEncoder passwordEncoder, RestTestClient client) {
        super(userRepository, passwordEncoder, client);
        this.client = client;
    }

    @Test
    public void givenValidAuth_whenGetAllPlantTypes_thenStatusOk() {
        client.get()
                .uri("/api/plant-types")
                .header("Authorization", "Bearer " + jwt)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void givenNoAuth_whenGetAllPlantTypes_thenStatusUnauthorized() {
        client.get()
                .uri("/api/plant-types")
                .exchange()
                .expectStatus().isUnauthorized();
    }
}
