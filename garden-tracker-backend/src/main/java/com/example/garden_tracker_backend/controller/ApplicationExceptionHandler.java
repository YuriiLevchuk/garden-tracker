package com.example.garden_tracker_backend.controller;

import com.example.garden_tracker_backend.exception.ApplicationException;
import com.example.garden_tracker_backend.exception.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<ExceptionResponse> handleApplicationException(ApplicationException e) {
        log.error("Application exception thrown: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(value = HandlerMethodValidationException.class)
    public ResponseEntity<ExceptionResponse> handleHandlerMethodValidationException(HandlerMethodValidationException e) {
        log.error("Handler method validation exception thrown: {}", e.getMessage());
        List<String> messages = new ArrayList<>();
        for(var p : e.getParameterValidationResults()) {
            for(var err : p.getResolvableErrors()) {
                messages.add(p.getMethodParameter().getParameterName() + ": " + err.getDefaultMessage());
            }
        }
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
}
