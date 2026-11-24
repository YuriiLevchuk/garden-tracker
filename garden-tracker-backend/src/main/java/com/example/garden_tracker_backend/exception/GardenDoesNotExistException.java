package com.example.garden_tracker_backend.exception;

public class GardenDoesNotExistException extends ApplicationException {

    public GardenDoesNotExistException(Long id) {
        super(String.format("Garden with id %s does not exist", id));
    }
}
