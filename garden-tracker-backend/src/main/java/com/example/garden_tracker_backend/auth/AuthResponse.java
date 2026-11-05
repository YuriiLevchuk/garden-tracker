package com.example.garden_tracker_backend.auth;

/** Response to a successful registration or login. */
public record AuthResponse(String token, UserResponse user) {
}
