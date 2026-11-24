package com.example.garden_tracker_backend.controller;

import com.example.garden_tracker_backend.dto.GardenRequest;
import com.example.garden_tracker_backend.dto.GardenResponse;
import com.example.garden_tracker_backend.service.GardenService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gardens")
@RequiredArgsConstructor
public class GardenController {

    private final GardenService gardenService;

    @PostMapping
    public ResponseEntity<Long> createGarden(@Valid @RequestBody GardenRequest gardenRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gardenService.createGarden(gardenRequest));
    }

    @GetMapping
    public List<GardenResponse> getAllGardens() {
        return gardenService.getAllGardens();
    }

    @PreAuthorize("@gardenServiceImpl.isOwnedByCurrentUser(#gardenId)")
    @GetMapping("/{id}")
    public GardenResponse getGarden(@PathVariable(name = "id") @PositiveOrZero Long gardenId) {
        return gardenService.getGarden(gardenId);
    }
}
