package com.example.garden_tracker_backend.service;

import com.example.garden_tracker_backend.dto.PlantTypeResponse;
import com.example.garden_tracker_backend.exception.PlantTypeDoesNotExistException;
import com.example.garden_tracker_backend.model.PlantType;
import com.example.garden_tracker_backend.repository.PlantTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantTypeServiceImpl implements PlantTypeService {

    private final PlantTypeRepository plantTypeRepository;

    @Override
    public List<PlantTypeResponse> getAllPlantTypes() {
        return plantTypeRepository.findAll().stream()
                .map(p -> new PlantTypeResponse(p.getId(), p.getName()))
                .toList();
    }

    @Override
    public PlantType getPlantTypeEntity(Long id) {
        return plantTypeRepository.findById(id)
                .orElseThrow(() -> new PlantTypeDoesNotExistException(id));
    }
}
