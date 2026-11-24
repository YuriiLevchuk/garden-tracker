package com.example.garden_tracker_backend.controller;

import com.example.garden_tracker_backend.dto.PlantRequest;
import com.example.garden_tracker_backend.dto.PlantResponse;
import com.example.garden_tracker_backend.security.JwtAuthenticationFilter;
import com.example.garden_tracker_backend.service.PlantService;
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

import java.util.List;

import static com.example.garden_tracker_backend.TestConstants.*;
import static org.mockito.Mockito.when;

@WebMvcTest(value = PlantController.class, excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtAuthenticationFilter.class
))
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestTestClient
public class PlantControllerTest {

    @MockitoBean
    private PlantService plantService;
    private final RestTestClient client;
    private final ObjectMapper objectMapper;

    @Autowired
    public PlantControllerTest(RestTestClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    @Test
    public void givenValidRequest_whenCreatePlant_thenStatusCreated() {
        var request = new PlantRequest(1L, 1L);
        when(plantService.createPlant(request)).thenReturn(1L);
        var response = client.post()
                .uri("/api/plants")
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(request))
                .exchange();
        response
                .expectStatus().isCreated()
                .expectBody().json(String.valueOf(1L));
    }

    @Test
    public void givenInvalidRequest_whenCreatePlant_thenStatusBadRequest() {
        var request = new PlantRequest(-1L, 1L);
        var response = client.post()
                .uri("/api/plants")
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(request))
                .exchange();
        response
                .expectStatus().isBadRequest()
                .expectBody().jsonPath("message").isNotEmpty();
    }

    @Test
    public void givenValidGardenId_whenGetAllPlants_thenStatusOk() {
        var plants = List.of(
                new PlantResponse(1L, TEST_PLANT_TYPE_NAME)
        );
        when(plantService.getAllPlants(1L)).thenReturn(plants);
        var response = client.get()
                .uri("/api/plants?garden_id=1")
                .exchange();
        response
                .expectStatus().isOk()
                .expectBody().json(objectMapper.writeValueAsString(plants));
    }

    @Test
    public void givenNoGardenId_whenGetAllPlants_thenStatusBadRequest() {
        var response = client.get()
                .uri("/api/plants")
                .exchange();
        response
                .expectStatus().isBadRequest();
    }

    @Test
    public void givenNaNGardenId_whenGetAllPlants_thenStatusBadRequest() {
        var response = client.get()
                .uri("/api/plants?garden_id=invalid")
                .exchange();
        response
                .expectStatus().isBadRequest();
    }

    @Test
    public void givenNegativeGardenId_whenGetAllPlants_thenStatusBadRequest() {
        var response = client.get()
                .uri("/api/plants?garden_id=-1")
                .exchange();
        response
                .expectStatus().isBadRequest()
                .expectBody().jsonPath("message").isNotEmpty();
    }

    @Test
    public void givenValidPlantId_whenDeletePlant_thenStatusNoContent() {
        var response = client.delete()
                .uri("/api/plants/1")
                .exchange();
        response
                .expectStatus().isNoContent();
    }

    @Test
    public void givenNaNPlantId_whenDeletePlant_thenStatusBadRequest() {
        var response = client.delete()
                .uri("/api/plants/invalid")
                .exchange();
        response
                .expectStatus().isBadRequest();
    }

    @Test
    public void givenNegativePlantId_whenDeletePlant_thenStatusBadRequest() {
        var response = client.delete()
                .uri("/api/plants/-1")
                .exchange();
        response
                .expectStatus().isBadRequest()
                .expectBody().jsonPath("message").isNotEmpty();
    }
}
