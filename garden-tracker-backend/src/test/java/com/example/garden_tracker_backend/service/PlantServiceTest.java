package com.example.garden_tracker_backend.service;

import com.example.garden_tracker_backend.dto.PlantRequest;
import com.example.garden_tracker_backend.dto.PlantResponse;
import com.example.garden_tracker_backend.exception.PlantDoesNotExistException;
import com.example.garden_tracker_backend.model.Garden;
import com.example.garden_tracker_backend.model.Plant;
import com.example.garden_tracker_backend.model.PlantType;
import com.example.garden_tracker_backend.model.User;
import com.example.garden_tracker_backend.repository.PlantRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.example.garden_tracker_backend.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlantServiceTest {

    private final PlantRepository plantRepository = mock(PlantRepository.class);
    private final GardenService gardenService = mock(GardenService.class);
    private final PlantTypeService plantTypeService = mock(PlantTypeService.class);
    private final UserProvider userProvider = mock(UserProvider.class);
    private final PlantService plantService = new PlantServiceImpl(plantRepository, gardenService, plantTypeService, userProvider);

    @Test
    public void whenCreatePlant_thenReturnId() {
        var plantRequest = new PlantRequest(1L, 1L);
        var plant = Plant.builder()
                .id(1L)
                .build();
        when(gardenService.getGardenEntity(plantRequest.gardenId())).thenReturn(new Garden());
        when(plantTypeService.getPlantTypeEntity(plantRequest.plantTypeId())).thenReturn(new PlantType());
        when(plantRepository.save(any())).thenReturn(plant);
        var result = plantService.createPlant(plantRequest);
        assertEquals(plant.getId(), result);
        verify(plantRepository, times(1)).save(any());
    }

    @Test
    public void whenGetAllPlants_thenReturnPlantResponseList() {
        var plantType = PlantType.builder()
                .name(TEST_PLANT_TYPE_NAME)
                .build();
        var plant = Plant.builder()
                .plantType(plantType)
                .build();
        var gardenId = 1L;
        when(plantRepository.findAllByGardenId(gardenId)).thenReturn(List.of(plant));
        List<PlantResponse> result = plantService.getAllPlants(gardenId);
        assertEquals(1, result.size());
        assertEquals(plant.getId(), result.get(0).id());
        assertEquals(plant.getPlantType().getName(), result.get(0).plantType());
    }

    @Test
    public void givenPlantExists_whenDeletePlant_thenRepositoryCalled() {
        var plant = Plant.builder()
                .id(1L)
                .build();
        when(plantRepository.findById(plant.getId())).thenReturn(Optional.of(plant));
        plantService.deletePlant(plant.getId());
        verify(plantRepository, times(1)).deleteById(plant.getId());
    }

    @Test
    public void givenPlantNotExist_whenDeletePlant_thenThrow() {
        var plantId = 1L;
        when(plantRepository.findById(plantId)).thenReturn(Optional.empty());
        assertThrows(PlantDoesNotExistException.class, () -> {
            plantService.deletePlant(plantId);
        });
        verify(plantRepository, times(0)).deleteById(plantId);
    }

    @Test
    public void whenIsOwnedByCurrentUser_thenRepositoryCalled() {
        var plantId = 1L;
        var user = new User();
        when(userProvider.getCurrentUser()).thenReturn(user);
        when(plantRepository.existsByIdAndGardenUser(plantId, user)).thenReturn(true);
        var result = plantService.isOwnedByCurrentUser(plantId);
        assertTrue(result);
        verify(plantRepository, times(1)).existsByIdAndGardenUser(plantId, user);
    }
}
