package com.example.garden_tracker_backend.service;

import com.example.garden_tracker_backend.dto.UserRequest;
import com.example.garden_tracker_backend.dto.UserResponse;
import com.example.garden_tracker_backend.exception.EmailAlreadyExistsException;
import com.example.garden_tracker_backend.model.User;
import com.example.garden_tracker_backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.example.garden_tracker_backend.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserProvider userProvider = mock(UserProvider.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final UserService userService = new UserServiceImpl(userRepository, userProvider, passwordEncoder);

    @Test
    public void givenEmailNotTaken_whenCreateUser_thenReturnId() {
        var savedUser = User.builder()
                .id(1L)
                .build();
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(savedUser);
        var result = userService.createUser(new UserRequest(TEST_EMAIL, TEST_PASSWORD));
        assertEquals(savedUser.getId(), result);
        verify(passwordEncoder, times(1)).encode(TEST_PASSWORD);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void givenEmailTaken_whenCreateUser_thenThrow() {
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(new User()));
        assertThrows(EmailAlreadyExistsException.class, () -> {
            userService.createUser(new UserRequest(TEST_EMAIL, TEST_PASSWORD));
        });
        verify(userRepository, times(0)).save(any());
    }

    @Test
    public void whenGetCurrentUser_thenReturnUserResponse() {
        var user = User.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();
        when(userProvider.getCurrentUser()).thenReturn(user);
        UserResponse userResponse = userService.getCurrentUser();
        assertEquals(user.getId(), userResponse.id());
        assertEquals(user.getEmail(), userResponse.email());
    }
}
