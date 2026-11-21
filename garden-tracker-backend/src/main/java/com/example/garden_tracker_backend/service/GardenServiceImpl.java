package com.example.garden_tracker_backend.service;

import com.example.garden_tracker_backend.dto.GardenRequest;
import com.example.garden_tracker_backend.exception.GardenAlreadyExistsException;
import com.example.garden_tracker_backend.model.Garden;
import com.example.garden_tracker_backend.repository.GardenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GardenServiceImpl implements GardenService {

    private final GardenRepository gardenRepository;
    private final UserProvider userProvider;

    @Override
    public Long createGarden(GardenRequest gardenRequest) {
        var user = userProvider.getCurrentUser();
        if(gardenRepository.existsByNameAndUser(gardenRequest.name(), user)) {
            throw new GardenAlreadyExistsException(gardenRequest.name());
        }
        var garden = Garden.builder()
                .name(gardenRequest.name())
                .user(user)
                .build();
        return gardenRepository.save(garden).getId();
    }
}
