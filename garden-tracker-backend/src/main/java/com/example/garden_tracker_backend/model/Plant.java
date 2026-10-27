package com.example.garden_tracker_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "plants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Garden garden;
    @ManyToOne
    private PlantType plantType;
    @ManyToMany(mappedBy = "plants")
    private List<GardeningTask> gardeningTasks;
}
