package com.example.garden_tracker_backend.service;

import com.example.garden_tracker_backend.dto.PlantRequest;
import com.example.garden_tracker_backend.dto.PlantResponse;

import java.util.List;

public interface PlantService {

    Long createPlant(PlantRequest plantRequest);
    List<PlantResponse> getAllPlants(Long gardenId);
    void deletePlant(Long id);
    boolean isOwnedByCurrentUser(Long plantId);
}
