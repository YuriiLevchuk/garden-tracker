package com.example.garden_tracker_backend.repository;

import com.example.garden_tracker_backend.model.Garden;
import com.example.garden_tracker_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GardenRepository extends JpaRepository<Garden, Long> {

    List<Garden> findGardensByUser(User owner);
    boolean existsByNameAndUser(String name, User user);
}
