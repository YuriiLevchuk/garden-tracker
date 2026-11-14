package com.example.garden_tracker_backend.exception;

public class EmailAlreadyExistsException extends ApplicationException {

    public EmailAlreadyExistsException(String email) {
        super("Email already exists: " + email);
    }
}
