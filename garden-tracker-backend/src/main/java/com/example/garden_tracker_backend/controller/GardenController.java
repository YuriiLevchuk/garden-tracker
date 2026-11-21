package com.example.garden_tracker_backend.controller;

import com.example.garden_tracker_backend.dto.GardenRequest;
import com.example.garden_tracker_backend.service.GardenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gardens")
@RequiredArgsConstructor
public class GardenController {

    private final GardenService gardenService;

    @PostMapping
    public ResponseEntity<Long> createGarden(@Valid @RequestBody GardenRequest gardenRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gardenService.createGarden(gardenRequest));
    }
}
