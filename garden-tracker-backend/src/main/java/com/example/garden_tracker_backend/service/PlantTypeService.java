package com.example.garden_tracker_backend.service;

import com.example.garden_tracker_backend.dto.PlantTypeResponse;
import com.example.garden_tracker_backend.model.PlantType;

import java.util.List;

public interface PlantTypeService {

    List<PlantTypeResponse> getAllPlantTypes();
    PlantType getPlantTypeEntity(Long id);
}
