package com.example.garden_tracker_backend.controller;

import com.example.garden_tracker_backend.dto.UserRequest;
import com.example.garden_tracker_backend.dto.UserResponse;
import com.example.garden_tracker_backend.security.JwtAuthenticationFilter;
import com.example.garden_tracker_backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;
import tools.jackson.databind.ObjectMapper;

import static com.example.garden_tracker_backend.TestConstants.*;
import static org.mockito.Mockito.*;

@WebMvcTest(value = UserController.class, excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtAuthenticationFilter.class
))
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestTestClient
public class UserControllerTest {

    @MockitoBean
    private UserService userService;
    private final RestTestClient client;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserControllerTest(RestTestClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    @Test
    public void givenValidRequest_whenCreateUser_thenStatusCreated() {
        var request = new UserRequest(TEST_EMAIL, TEST_PASSWORD);
        when(userService.createUser(request)).thenReturn(1L);
        var response = client.post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(request))
                .exchange();
        response
                .expectStatus().isCreated()
                .expectBody().json(String.valueOf(1L));
    }

    @Test
    public void givenInvalidRequest_whenCreateUser_thenStatusBadRequest() {
        var request = new UserRequest("invalid", TEST_PASSWORD);
        var response = client.post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(request))
                .exchange();
        response
                .expectStatus().isBadRequest()
                .expectBody().jsonPath("message").isNotEmpty();
    }

    @Test
    public void whenGetCurrentUser_thenStatusOk() {
        var userResponse = new UserResponse(1L, TEST_EMAIL);
        when(userService.getCurrentUser()).thenReturn(userResponse);
        var response = client.get()
                .uri("/api/users")
                .exchange();
        response
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(userResponse.id())
                .jsonPath("email").isEqualTo(userResponse.email());
    }
}
