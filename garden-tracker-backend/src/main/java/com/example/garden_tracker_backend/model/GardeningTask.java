package com.example.garden_tracker_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "gardening_tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GardeningTask {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "realization_time")
    private Instant realizationTime;
    @ManyToOne
    private User user;
    @ManyToOne
    private Garden garden;
    @ManyToOne
    @JoinColumn(name = "gardening_task_type")
    private GardeningTaskType gardeningTaskType;
    @ManyToMany
    @JoinTable(name = "gardening_task_plant",
            joinColumns = @JoinColumn(name = "gardening_task_id"),
            inverseJoinColumns = @JoinColumn(name = "plant_id"))
    private List<Plant> plants;
}
