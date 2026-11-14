package com.example.garden_tracker_backend.security;

import com.example.garden_tracker_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
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
@Transactional
public class AuthControllerSecurityTest extends AuthTest {

    private final RestTestClient client;

    @Autowired
    public AuthControllerSecurityTest(UserRepository userRepository, PasswordEncoder passwordEncoder, RestTestClient client) {
        super(userRepository, passwordEncoder, client);
        this.client = client;
    }

    @Test
    public void givenValidAuth_whenLogin_thenStatusOk() {
        client.post()
                .uri("/api/auth/login")
                .header("Authorization", "Basic " + BASIC_CREDENTIALS)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void givenNoAuth_whenLogin_thenStatus401() {
        client.post()
                .uri("/api/auth/login")
                .exchange()
                .expectStatus().isUnauthorized();
    }
}
