package com.example.garden_tracker_backend.validation;

import com.example.garden_tracker_backend.dto.PlantRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlantRequestValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void givenValidPlantRequest_whenValidate_thenNoViolation() {
        var plantRequest = new PlantRequest(1L, 1L);
        var violations = validator.validate(plantRequest);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void givenNullGardenId_whenValidate_thenViolate() {
        var plantRequest = new PlantRequest(null, 1L);
        var violations = validator.validate(plantRequest);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void givenNullPlantTypeId_whenValidate_thenViolate() {
        var plantRequest = new PlantRequest(1L, null);
        var violations = validator.validate(plantRequest);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void givenNegativeGardenId_whenValidate_thenViolate() {
        var plantRequest = new PlantRequest(-1L, 1L);
        var violations = validator.validate(plantRequest);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void givenNegativePlantTypeId_whenValidate_thenViolate() {
        var plantRequest = new PlantRequest(1L, -1L);
        var violations = validator.validate(plantRequest);

        assertFalse(violations.isEmpty());
    }
}
