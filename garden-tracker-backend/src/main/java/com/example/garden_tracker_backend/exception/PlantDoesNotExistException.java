package com.example.garden_tracker_backend.exception;

public class PlantDoesNotExistException extends ApplicationException {

    public PlantDoesNotExistException(Long id) {
        super(String.format("Plant with id %s does not exist", id));
    }
}
