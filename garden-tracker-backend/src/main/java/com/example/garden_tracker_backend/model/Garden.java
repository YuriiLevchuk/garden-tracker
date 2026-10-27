package com.example.garden_tracker_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "gardens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Garden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private User owner;
    @OneToMany(mappedBy = "garden")
    private List<Plant> plants;
    @OneToMany(mappedBy = "garden")
    private List<GardeningTask> gardeningTasks;
}
