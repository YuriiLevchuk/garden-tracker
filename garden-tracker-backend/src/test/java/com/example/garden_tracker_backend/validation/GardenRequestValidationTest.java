package com.example.garden_tracker_backend.validation;

import com.example.garden_tracker_backend.dto.GardenRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.garden_tracker_backend.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class GardenRequestValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void givenValidGardenRequest_whenValidate_thenNoViolation() {
        var gardenRequest = new GardenRequest(TEST_GARDEN_NAME);
        var violations = validator.validate(gardenRequest);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void givenNullName_whenValidate_thenViolate() {
        var gardenRequest = new GardenRequest(null);
        var violations = validator.validate(gardenRequest);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void givenBlankName_whenValidate_thenViolate() {
        var gardenRequest = new GardenRequest(" ");
        var violations = validator.validate(gardenRequest);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void givenLongName_whenValidate_thenViolate() {
        var gardenRequest = new GardenRequest("a".repeat(50));
        var violations = validator.validate(gardenRequest);

        assertFalse(violations.isEmpty());
    }
}
