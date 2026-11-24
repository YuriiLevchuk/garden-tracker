package com.example.garden_tracker_backend.service;

import com.example.garden_tracker_backend.dto.PlantTypeResponse;
import com.example.garden_tracker_backend.exception.PlantTypeDoesNotExistException;
import com.example.garden_tracker_backend.model.PlantType;
import com.example.garden_tracker_backend.repository.PlantTypeRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.example.garden_tracker_backend.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlantTypeServiceTest {

    private final PlantTypeRepository plantTypeRepository = mock(PlantTypeRepository.class);
    private final PlantTypeService plantTypeService = new PlantTypeServiceImpl(plantTypeRepository);

    @Test
    public void whenGetAllPlantTypes_thenReturnPlantTypeResponseList() {
        var plantType = PlantType.builder()
                .id(1L)
                .name(TEST_PLANT_TYPE_NAME)
                .build();
        when(plantTypeRepository.findAll()).thenReturn(List.of(plantType));
        List<PlantTypeResponse> result = plantTypeService.getAllPlantTypes();
        assertEquals(1, result.size());
        assertEquals(plantType.getId(), result.get(0).id());
        assertEquals(plantType.getName(), result.get(0).name());
    }

    @Test
    public void givenPlantTypeExists_whenGetPlantTypeEntity_thenReturnPlantTypeEntity() {
        var plantType = PlantType.builder()
                .id(1L)
                .name(TEST_PLANT_TYPE_NAME)
                .build();
        when(plantTypeRepository.findById(plantType.getId())).thenReturn(Optional.of(plantType));
        var result = plantTypeService.getPlantTypeEntity(plantType.getId());
        assertEquals(plantType, result);
    }

    @Test
    public void givenPlantTypeNotExist_whenGetPlantTypeEntity_thenThrow() {
        var plantTypeId = 1L;
        when(plantTypeRepository.findById(plantTypeId)).thenReturn(Optional.empty());
        assertThrows(PlantTypeDoesNotExistException.class, () -> {
            plantTypeService.getPlantTypeEntity(plantTypeId);
        });
    }
}
