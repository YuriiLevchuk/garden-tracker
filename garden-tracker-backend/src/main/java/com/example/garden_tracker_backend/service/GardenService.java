package com.example.garden_tracker_backend.service;

import com.example.garden_tracker_backend.dto.GardenRequest;
import com.example.garden_tracker_backend.dto.GardenResponse;
import com.example.garden_tracker_backend.model.Garden;

import java.util.List;

public interface GardenService {

    Long createGarden(GardenRequest gardenRequest);
    List<GardenResponse> getAllGardens();
    Garden getGardenEntity(Long id);
    boolean isOwnedByCurrentUser(Long gardenId);
    GardenResponse getGarden(Long id);
}
