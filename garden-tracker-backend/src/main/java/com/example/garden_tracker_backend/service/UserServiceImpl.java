package com.example.garden_tracker_backend.service;

import com.example.garden_tracker_backend.dto.UserRequest;
import com.example.garden_tracker_backend.dto.UserResponse;
import com.example.garden_tracker_backend.exception.EmailAlreadyExistsException;
import com.example.garden_tracker_backend.model.User;
import com.example.garden_tracker_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserProvider userProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long createUser(UserRequest userRequest) {
        if(userRepository.findByEmail(userRequest.email()).isPresent()) {
            throw new EmailAlreadyExistsException(userRequest.email());
        }
        var user = User.builder()
                .email(userRequest.email())
                .password(passwordEncoder.encode(userRequest.password()))
                .build();
        return userRepository.save(user).getId();
    }

    @Override
    public UserResponse getCurrentUser() {
        return userRepository.findById(userProvider.getCurrentUser())
                .map(u -> new UserResponse(u.getId(), u.getEmail()))
                .orElseThrow(IllegalStateException::new);
    }
}
