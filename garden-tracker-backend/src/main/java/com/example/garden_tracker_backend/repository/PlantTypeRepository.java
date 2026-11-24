package com.example.garden_tracker_backend.repository;

import com.example.garden_tracker_backend.model.PlantType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantTypeRepository extends JpaRepository<PlantType, Long> {

}
