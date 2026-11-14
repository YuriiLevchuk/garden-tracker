package com.example.garden_tracker_backend.service;

import com.example.garden_tracker_backend.dto.UserRequest;
import com.example.garden_tracker_backend.dto.UserResponse;

public interface UserService {

    Long createUser(UserRequest userRequest);
    UserResponse getCurrentUser();
}
