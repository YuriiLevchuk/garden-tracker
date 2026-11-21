package com.example.garden_tracker_backend.exception;

public class GardenAlreadyExistsException extends ApplicationException {

    public GardenAlreadyExistsException(String name) {
        super(String.format("Garden with name %s already exists", name));
    }
}
