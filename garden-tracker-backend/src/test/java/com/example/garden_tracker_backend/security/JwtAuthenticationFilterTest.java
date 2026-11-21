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
public class JwtAuthenticationFilterTest extends AuthTest {

    private final RestTestClient client;

    @Autowired
    public JwtAuthenticationFilterTest(UserRepository userRepository, PasswordEncoder passwordEncoder, RestTestClient client) {
        super(userRepository, passwordEncoder, client);
        this.client = client;
    }

    @Test
    public void givenValidJwt_whenSecuredEndpointCalled_thenStatus2xx() {
        client.get()
                .uri("/api/users")
                .header("Authorization", "Bearer " + jwt)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    public void givenNoAuth_whenSecuredEndpointCalled_thenStatus401() {
        client.get()
                .uri("/api/users")
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }

    @Test
    public void givenWrongAuth_whenSecuredEndpointCalled_thenStatus401() {
        client.get()
                .uri("/api/users")
                .header("Authorization", "Basic " + jwt)
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }

    @Test
    public void givenNoJwt_whenSecuredEndpointCalled_thenStatus401() {
        client.get()
                .uri("/api/users")
                .header("Authorization", "Bearer ")
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }

    @Test
    public void givenInvalidJwt_whenSecuredEndpointCalled_thenStatus401() {
        client.get()
                .uri("/api/users")
                .header("Authorization", "Bearer invalid")
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }
}
