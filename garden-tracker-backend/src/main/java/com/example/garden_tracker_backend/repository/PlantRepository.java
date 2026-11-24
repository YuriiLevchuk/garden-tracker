package com.example.garden_tracker_backend.repository;

import com.example.garden_tracker_backend.model.Plant;
import com.example.garden_tracker_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {

    List<Plant> findAllByGardenId(Long gardenId);
    boolean existsByIdAndGardenUser(Long id, User user);
}
