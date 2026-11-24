package com.example.garden_tracker_backend.controller;

import com.example.garden_tracker_backend.dto.PlantRequest;
import com.example.garden_tracker_backend.dto.PlantResponse;
import com.example.garden_tracker_backend.service.PlantService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plants")
@RequiredArgsConstructor
public class PlantController {

    private final PlantService plantService;

    @PreAuthorize("@gardenServiceImpl.isOwnedByCurrentUser(#plantRequest.gardenId())")
    @PostMapping
    public ResponseEntity<Long> createPlant(@Valid @RequestBody PlantRequest plantRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(plantService.createPlant(plantRequest));
    }

    @PreAuthorize("@gardenServiceImpl.isOwnedByCurrentUser(#gardenId)")
    @GetMapping
    public List<PlantResponse> getAllPlants(@RequestParam(name = "garden_id") @PositiveOrZero Long gardenId) {
        return plantService.getAllPlants(gardenId);
    }

    @PreAuthorize("@plantServiceImpl.isOwnedByCurrentUser(#plantId)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlant(@PathVariable(name = "id") @PositiveOrZero Long plantId) {
        plantService.deletePlant(plantId);
        return ResponseEntity.noContent().build();
    }
}
