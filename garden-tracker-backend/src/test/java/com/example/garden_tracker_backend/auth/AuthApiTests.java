package com.example.garden_tracker_backend.auth;

import com.example.garden_tracker_backend.repository.UserRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "app.jwt.secret=test-secret-that-is-at-least-32-characters")
@AutoConfigureMockMvc
@Testcontainers
class AuthApiTests {

    private static final String EMAIL = "gardener@example.com";
    private static final String PASSWORD = "correct-password";

    @Container
    @ServiceConnection
    static PostgreSQLContainer container = new PostgreSQLContainer("postgres:16-alpine");

    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository users;

    @BeforeEach
    void clearUsers() {
        users.deleteAll();
    }

    @Test
    void registerReturnsTokenAndStoresHashedPassword() throws Exception {
        register(EMAIL, PASSWORD)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token", not(emptyString())))
                .andExpect(jsonPath("$.user.email").value(EMAIL))
                .andExpect(jsonPath("$.user.password").doesNotExist());

        String stored = users.findByEmail(EMAIL).orElseThrow().getPassword();
        assertThat(stored).isNotEqualTo(PASSWORD).startsWith("$2");
    }

    @Test
    void registerRejectsDuplicateEmailIgnoringCase() throws Exception {
        register(EMAIL, PASSWORD).andExpect(status().isCreated());

        register("GARDENER@example.com", PASSWORD).andExpect(status().isConflict());
    }

    @Test
    void registerRejectsInvalidData() throws Exception {
        register("not-an-email", PASSWORD).andExpect(status().isBadRequest());
        register(EMAIL, "short").andExpect(status().isBadRequest());
        register(EMAIL, "ą".repeat(40)).andExpect(status().isBadRequest());
    }

    @Test
    void loginReturnsTokenForCorrectPassword() throws Exception {
        register(EMAIL, PASSWORD);

        login(EMAIL, PASSWORD)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", not(emptyString())))
                .andExpect(jsonPath("$.user.email").value(EMAIL));
    }

    @Test
    void loginRejectsWrongPasswordAndUnknownEmailTheSameWay() throws Exception {
        register(EMAIL, PASSWORD);

        login(EMAIL, "wrong-password")
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.detail").value("Invalid email or password"));
        login("nobody@example.com", PASSWORD)
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.detail").value("Invalid email or password"));
    }

    @Test
    void meRequiresValidToken() throws Exception {
        String body = register(EMAIL, PASSWORD).andReturn().getResponse().getContentAsString();
        String token = JsonPath.read(body, "$.token");
        int middleOfSignature = token.lastIndexOf('.') + 10;
        char replacement = token.charAt(middleOfSignature) == 'A' ? 'B' : 'A';
        String tampered = token.substring(0, middleOfSignature) + replacement + token.substring(middleOfSignature + 1);

        mvc.perform(get("/api/users/me")).andExpect(status().isUnauthorized());
        mvc.perform(get("/api/users/me").header("Authorization", "Bearer " + tampered))
                .andExpect(status().isUnauthorized());
        mvc.perform(get("/api/users/me").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(EMAIL));
    }

    private ResultActions register(String email, String password) throws Exception {
        return send("/api/auth/register", email, password);
    }

    private ResultActions login(String email, String password) throws Exception {
        return send("/api/auth/login", email, password);
    }

    private ResultActions send(String path, String email, String password) throws Exception {
        return mvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"%s\",\"password\":\"%s\"}".formatted(email, password)));
    }
}
