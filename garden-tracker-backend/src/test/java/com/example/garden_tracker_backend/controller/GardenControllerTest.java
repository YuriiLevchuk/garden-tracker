package com.example.garden_tracker_backend.controller;

import com.example.garden_tracker_backend.dto.GardenRequest;
import com.example.garden_tracker_backend.security.JwtAuthenticationFilter;
import com.example.garden_tracker_backend.service.GardenService;
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
import static org.mockito.Mockito.when;

@WebMvcTest(value = GardenController.class, excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtAuthenticationFilter.class
))
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestTestClient
public class GardenControllerTest {

    @MockitoBean
    private GardenService gardenService;
    private final RestTestClient client;
    private final ObjectMapper objectMapper;

    @Autowired
    public GardenControllerTest(RestTestClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    @Test
    public void givenValidRequest_whenCreateGarden_thenStatusCreated() {
        var request = new GardenRequest(TEST_GARDEN_NAME);
        when(gardenService.createGarden(request)).thenReturn(1L);
        var response = client.post()
                .uri("/api/gardens")
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(request))
                .exchange();
        response
                .expectStatus().isCreated()
                .expectBody().json(String.valueOf(1L));
    }

    @Test
    public void givenInvalidRequest_whenCreateGarden_thenStatusBadRequest() {
        var request = new GardenRequest(" ");
        var response = client.post()
                .uri("/api/gardens")
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(request))
                .exchange();
        response
                .expectStatus().isBadRequest()
                .expectBody().jsonPath("message").isNotEmpty();
    }
}
