package com.example.garden_tracker_backend.service;

import com.example.garden_tracker_backend.dto.GardenRequest;
import com.example.garden_tracker_backend.dto.GardenResponse;
import com.example.garden_tracker_backend.exception.GardenAlreadyExistsException;
import com.example.garden_tracker_backend.exception.GardenDoesNotExistException;
import com.example.garden_tracker_backend.model.Garden;
import com.example.garden_tracker_backend.repository.GardenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<GardenResponse> getAllGardens() {
        return gardenRepository.findGardensByUser(userProvider.getCurrentUser()).stream()
                .map(g -> new GardenResponse(g.getId(), g.getName()))
                .toList();
    }

    @Override
    public Garden getGardenEntity(Long id) {
        return gardenRepository.findById(id)
                .orElseThrow(() -> new GardenDoesNotExistException(id));
    }

    @Override
    public boolean isOwnedByCurrentUser(Long gardenId) {
        return gardenRepository.existsByIdAndUser(gardenId, userProvider.getCurrentUser());
    }

    @Override
    public GardenResponse getGarden(Long id) {
        return gardenRepository.findById(id)
                .map(g -> new GardenResponse(g.getId(), g.getName()))
                .orElseThrow(() -> new GardenDoesNotExistException(id));
    }
}
