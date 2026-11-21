package com.example.garden_tracker_backend.service;

import com.example.garden_tracker_backend.dto.GardenRequest;
import com.example.garden_tracker_backend.exception.GardenAlreadyExistsException;
import com.example.garden_tracker_backend.model.Garden;
import com.example.garden_tracker_backend.model.User;
import com.example.garden_tracker_backend.repository.GardenRepository;
import org.junit.jupiter.api.Test;

import static com.example.garden_tracker_backend.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GardenServiceTest {

    private final GardenRepository gardenRepository = mock(GardenRepository.class);
    private final UserProvider userProvider = mock(UserProvider.class);
    private final GardenService gardenService = new GardenServiceImpl(gardenRepository, userProvider);

    @Test
    public void givenNameNotTaken_whenCreateGarden_thenReturnId() {
        var user = new User();
        when(userProvider.getCurrentUser()).thenReturn(user);
        when(gardenRepository.existsByNameAndUser(TEST_GARDEN_NAME, user)).thenReturn(false);
        var garden = Garden.builder()
                .id(1L)
                .user(user)
                .build();
        when(gardenRepository.save(any())).thenReturn(garden);
        var result = gardenService.createGarden(new GardenRequest(TEST_GARDEN_NAME));
        assertEquals(garden.getId(), result);
        verify(userProvider, times(1)).getCurrentUser();
        verify(gardenRepository, times(1)).existsByNameAndUser(TEST_GARDEN_NAME, user);
        verify(gardenRepository, times(1)).save(any());
    }

    @Test
    public void givenNameTaken_whenCreateGarden_thenThrow() {
        var user = new User();
        when(userProvider.getCurrentUser()).thenReturn(user);
        when(gardenRepository.existsByNameAndUser(TEST_GARDEN_NAME, user)).thenReturn(true);
        assertThrows(GardenAlreadyExistsException.class, () -> {
           gardenService.createGarden(new GardenRequest(TEST_GARDEN_NAME));
        });
        verify(userProvider, times(1)).getCurrentUser();
        verify(gardenRepository, times(1)).existsByNameAndUser(TEST_GARDEN_NAME, user);
        verify(gardenRepository, times(0)).save(any());
    }
}
