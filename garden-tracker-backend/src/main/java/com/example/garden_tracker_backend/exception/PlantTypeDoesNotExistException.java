package com.example.garden_tracker_backend.exception;

public class PlantTypeDoesNotExistException extends ApplicationException {

    public PlantTypeDoesNotExistException(Long id) {
        super(String.format("Plant type with id %s does not exist", id));
    }
}
