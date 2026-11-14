package com.example.garden_tracker_backend.security;

import com.example.garden_tracker_backend.dto.UserRequest;
import com.example.garden_tracker_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
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
@Transactional
public class UserControllerSecurityTest extends AuthTest {

    private final RestTestClient client;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserControllerSecurityTest(UserRepository userRepository, PasswordEncoder passwordEncoder, RestTestClient client, ObjectMapper objectMapper) {
        super(userRepository, passwordEncoder, client);
        this.client = client;
        this.objectMapper = objectMapper;
    }

    @Test
    public void whenCreateUser_thenStatusCreated() {
        var userRequest = new UserRequest(TEST_EMAIL, TEST_PASSWORD);
        client.post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(userRequest))
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void givenValidAuth_whenGetCurrentUser_thenStatusOk() {
        client.get()
                .uri("/api/users")
                .header("Authorization", "Bearer " + jwt)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void givenNoAuth_whenGetCurrentUser_thenStatusUnauthorized() {
        client.get()
                .uri("/api/users")
                .exchange()
                .expectStatus().isUnauthorized();
    }
}
