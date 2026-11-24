package com.example.garden_tracker_backend.controller;

import com.example.garden_tracker_backend.dto.PlantTypeResponse;
import com.example.garden_tracker_backend.service.PlantTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/plant-types")
@RequiredArgsConstructor
public class PlantTypeController {

    private final PlantTypeService plantTypeService;

    @GetMapping
    public List<PlantTypeResponse> getAllPlantTypes() {
        return plantTypeService.getAllPlantTypes();
    }
}
