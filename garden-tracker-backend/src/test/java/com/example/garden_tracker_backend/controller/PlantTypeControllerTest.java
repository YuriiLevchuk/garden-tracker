package com.example.garden_tracker_backend.controller;

import com.example.garden_tracker_backend.dto.PlantTypeResponse;
import com.example.garden_tracker_backend.security.JwtAuthenticationFilter;
import com.example.garden_tracker_backend.service.PlantTypeService;
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
import static org.mockito.Mockito.*;

@WebMvcTest(value = PlantTypeController.class, excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtAuthenticationFilter.class
))
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestTestClient
public class PlantTypeControllerTest {

    @MockitoBean
    private PlantTypeService plantTypeService;
    private final RestTestClient client;
    private final ObjectMapper objectMapper;

    @Autowired
    public PlantTypeControllerTest(RestTestClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    @Test
    public void whenGetAllPlantTypes_thenStatusOk() {
        var plantTypes = List.of(new PlantTypeResponse(1L, TEST_PLANT_TYPE_NAME));
        when(plantTypeService.getAllPlantTypes()).thenReturn(plantTypes);
        var response = client.get()
                .uri("/api/plant-types")
                .exchange();
        response
                .expectStatus().isOk()
                .expectBody().json(objectMapper.writeValueAsString(plantTypes));
    }
}
