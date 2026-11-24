package com.example.garden_tracker_backend.service;

import com.example.garden_tracker_backend.dto.GardenRequest;
import com.example.garden_tracker_backend.dto.GardenResponse;
import com.example.garden_tracker_backend.exception.GardenAlreadyExistsException;
import com.example.garden_tracker_backend.exception.GardenDoesNotExistException;
import com.example.garden_tracker_backend.model.Garden;
import com.example.garden_tracker_backend.model.User;
import com.example.garden_tracker_backend.repository.GardenRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

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

    @Test
    public void whenGetAllGardens_thenReturnGardenResponseList() {
        var user = new User();
        var garden = Garden.builder()
                .id(1L)
                .name(TEST_GARDEN_NAME)
                .build();
        when(userProvider.getCurrentUser()).thenReturn(user);
        when(gardenRepository.findGardensByUser(user)).thenReturn(List.of(garden));
        List<GardenResponse> result = gardenService.getAllGardens();
        assertEquals(1, result.size());
        assertEquals(garden.getId(), result.get(0).id());
        assertEquals(garden.getName(), result.get(0).name());
    }

    @Test
    public void givenGardenExists_whenGetGardenEntity_thenReturnGardenEntity() {
        var garden = Garden.builder()
                .id(1L)
                .name(TEST_GARDEN_NAME)
                .build();
        when(gardenRepository.findById(garden.getId())).thenReturn(Optional.of(garden));
        var result = gardenService.getGardenEntity(garden.getId());
        assertEquals(garden, result);
    }

    @Test
    public void givenGardenNotExist_whenGetGardenEntity_thenThrow() {
        var gardenId = 1L;
        when(gardenRepository.findById(gardenId)).thenReturn(Optional.empty());
        assertThrows(GardenDoesNotExistException.class, () -> {
            gardenService.getGardenEntity(gardenId);
        });
    }

    @Test
    public void whenIsOwnedByCurrentUser_thenRepositoryCalled() {
        var gardenId = 1L;
        var user = new User();
        when(userProvider.getCurrentUser()).thenReturn(user);
        when(gardenRepository.existsByIdAndUser(gardenId, user)).thenReturn(true);
        var result = gardenService.isOwnedByCurrentUser(gardenId);
        assertTrue(result);
        verify(gardenRepository, times(1)).existsByIdAndUser(gardenId, user);
    }

    @Test
    public void givenGardenExists_whenGetGarden_thenReturnGardenResponse() {
        var garden = Garden.builder()
                .id(1L)
                .name(TEST_GARDEN_NAME)
                .build();
        when(gardenRepository.findById(garden.getId())).thenReturn(Optional.of(garden));
        GardenResponse result = gardenService.getGarden(garden.getId());
        assertEquals(garden.getId(), result.id());
        assertEquals(garden.getName(), result.name());
    }

    @Test
    public void givenGardenNotExist_whenGetGarden_thenThrow() {
        var gardenId = 1L;
        when(gardenRepository.findById(gardenId)).thenReturn(Optional.empty());
        assertThrows(GardenDoesNotExistException.class, () -> {
            gardenService.getGarden(gardenId);
        });
    }
}
