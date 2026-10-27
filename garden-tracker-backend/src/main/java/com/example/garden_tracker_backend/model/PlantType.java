package com.example.garden_tracker_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "plant_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlantType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "plantType")
    private List<Plant> plants;
}
