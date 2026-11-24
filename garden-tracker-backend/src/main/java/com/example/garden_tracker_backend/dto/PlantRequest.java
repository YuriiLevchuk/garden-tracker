package com.example.garden_tracker_backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record PlantRequest(
        @NotNull
        @PositiveOrZero
        Long gardenId,
        @NotNull
        @PositiveOrZero
        Long plantTypeId
) {
}
