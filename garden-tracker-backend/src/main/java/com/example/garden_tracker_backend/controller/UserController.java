package com.example.garden_tracker_backend.controller;

import com.example.garden_tracker_backend.dto.UserRequest;
import com.example.garden_tracker_backend.dto.UserResponse;
import com.example.garden_tracker_backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Long> createUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequest));
    }

    @GetMapping
    public UserResponse getCurrentUser() {
        return userService.getCurrentUser();
    }
}
