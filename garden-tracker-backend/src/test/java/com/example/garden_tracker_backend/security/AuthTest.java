package com.example.garden_tracker_backend.security;

import com.example.garden_tracker_backend.IntegrationTest;
import com.example.garden_tracker_backend.model.User;
import com.example.garden_tracker_backend.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AuthTest extends IntegrationTest {

    private static final String AUTH_USER_EMAIL = "authuser@email.com";
    private static final String AUTH_USER_PASSWORD = "authuserpassword";
    protected static final String BASIC_CREDENTIALS = Base64.getEncoder()
            .encodeToString((AUTH_USER_EMAIL + ":" + AUTH_USER_PASSWORD).getBytes(StandardCharsets.UTF_8));
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTestClient client;
    protected String jwt;

    public AuthTest(UserRepository userRepository, PasswordEncoder passwordEncoder, RestTestClient client) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.client = client;
    }

    @BeforeAll
    public void beforeAll() {
        var user = User.builder()
                .email(AUTH_USER_EMAIL)
                .password(passwordEncoder.encode(AUTH_USER_PASSWORD))
                .build();
        userRepository.save(user);
    }

    @AfterAll
    public void afterAll() {
        userRepository.deleteAll();
    }

    @BeforeEach
    public void beforeEach() {
        this.jwt = client.post()
                .uri("/api/auth/login")
                .header("Authorization", "Basic " + BASIC_CREDENTIALS)
                .exchange()
                .returnResult(AuthResponse.class)
                .getResponseBody()
                .token();
    }
}
