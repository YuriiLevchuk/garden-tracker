package com.example.garden_tracker_backend.service;

import com.example.garden_tracker_backend.dto.PlantRequest;
import com.example.garden_tracker_backend.dto.PlantResponse;
import com.example.garden_tracker_backend.exception.PlantDoesNotExistException;
import com.example.garden_tracker_backend.model.Plant;
import com.example.garden_tracker_backend.repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantServiceImpl implements PlantService {

    private final PlantRepository plantRepository;
    private final GardenService gardenService;
    private final PlantTypeService plantTypeService;
    private final UserProvider userProvider;

    @Override
    public Long createPlant(PlantRequest plantRequest) {
        var plant = Plant.builder()
                .garden(gardenService.getGardenEntity(plantRequest.gardenId()))
                .plantType(plantTypeService.getPlantTypeEntity(plantRequest.plantTypeId()))
                .build();
        return plantRepository.save(plant).getId();
    }

    @Override
    public List<PlantResponse> getAllPlants(Long gardenId) {
        return plantRepository.findAllByGardenId(gardenId).stream()
                .map(p -> new PlantResponse(p.getId(), p.getPlantType().getName()))
                .toList();
    }

    @Override
    public void deletePlant(Long id) {
        if(plantRepository.findById(id).isEmpty()) {
            throw new PlantDoesNotExistException(id);
        }
        plantRepository.deleteById(id);
    }

    @Override
    public boolean isOwnedByCurrentUser(Long plantId) {
        return plantRepository.existsByIdAndGardenUser(plantId, userProvider.getCurrentUser());
    }
}
