package com.example.garden_tracker_backend.service;

import com.example.garden_tracker_backend.dto.GardenRequest;

public interface GardenService {

    Long createGarden(GardenRequest gardenRequest);
}
