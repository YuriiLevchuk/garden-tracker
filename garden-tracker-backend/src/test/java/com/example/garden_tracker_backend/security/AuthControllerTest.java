package com.example.garden_tracker_backend.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.mockito.Mockito.*;

@WebMvcTest(value = AuthController.class, excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtAuthenticationFilter.class
))
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestTestClient
public class AuthControllerTest {

    @MockitoBean
    private JwtService jwtService;
    private final RestTestClient client;

    @Autowired
    public AuthControllerTest(RestTestClient client) {
        this.client = client;
    }

    @Test
    public void whenLogin_thenStatusOk() {
        var token = "jwt";
        when(jwtService.createToken()).thenReturn(token);
        var response = client.post()
                .uri("/api/auth/login")
                .exchange();
        response
                .expectStatus().isOk()
                .expectBody().jsonPath("token").isEqualTo(token);
    }
}
