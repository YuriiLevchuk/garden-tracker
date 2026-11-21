package com.example.garden_tracker_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GardenRequest(
        @NotBlank
        @Size(max = 20)
        String name
) {
}
