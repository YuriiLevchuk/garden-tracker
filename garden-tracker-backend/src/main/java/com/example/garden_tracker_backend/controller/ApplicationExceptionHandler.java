package com.example.garden_tracker_backend.controller;

import com.example.garden_tracker_backend.exception.ApplicationException;
import com.example.garden_tracker_backend.exception.ExceptionResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<ExceptionResponse> handleApplicationException(ApplicationException e) {
        log.error("Application exception thrown: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("Constraint violation exception thrown: {}", e.getMessage());
        String[] messages = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toArray(String[]::new);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(String.join(", ", messages)));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Invalid method argument exception thrown: {}", e.getMessage());
        String[] messages = e.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toArray(String[]::new);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(String.join(", ", messages)));
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Void> handleServerException(RuntimeException e) {
        log.error("Server exception thrown: {}, stack trace: {}", e.getMessage(), e.getStackTrace());
        return ResponseEntity.badRequest().build();
    }
}
