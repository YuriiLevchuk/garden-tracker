package com.example.garden_tracker_backend.auth;

import com.example.garden_tracker_backend.model.User;

/** User data that is safe to send to the client; never includes the password. */
public record UserResponse(Long id, String email) {

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getEmail());
    }
}
