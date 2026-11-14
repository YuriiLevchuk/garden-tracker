package com.example.garden_tracker_backend.validation;

import com.example.garden_tracker_backend.dto.UserRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.garden_tracker_backend.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserRequestValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void givenValidUserRequest_whenValidate_thenNoViolation() {
        var userRequest = new UserRequest(TEST_EMAIL, TEST_PASSWORD);
        var violations = validator.validate(userRequest);

        assertTrue(violations.isEmpty());
    }

    @Test
    void givenNullEmail_whenValidate_thenViolate() {
        var userRequest = new UserRequest(null, TEST_PASSWORD);
        var violations = validator.validate(userRequest);

        assertFalse(violations.isEmpty());
    }

    @Test
    void givenNullPassword_whenValidate_thenViolate() {
        var userRequest = new UserRequest(TEST_EMAIL, null);
        var violations = validator.validate(userRequest);

        assertFalse(violations.isEmpty());
    }

    @Test
    void givenBlankEmail_whenValidate_thenViolate() {
        var userRequest = new UserRequest(" ", TEST_PASSWORD);
        var violations = validator.validate(userRequest);

        assertFalse(violations.isEmpty());
    }

    @Test
    void givenBlankPassword_whenValidate_thenViolate() {
        var userRequest = new UserRequest(TEST_EMAIL, " ");
        var violations = validator.validate(userRequest);

        assertFalse(violations.isEmpty());
    }

    @Test
    void givenInvalidEmail_whenValidate_thenViolate() {
        var userRequest = new UserRequest("invalid", TEST_PASSWORD);
        var violations = validator.validate(userRequest);

        assertFalse(violations.isEmpty());
    }

    @Test
    void givenShortPassword_whenValidate_thenViolate() {
        var userRequest = new UserRequest(TEST_EMAIL, "abcdef");
        var violations = validator.validate(userRequest);

        assertFalse(violations.isEmpty());
    }
}
