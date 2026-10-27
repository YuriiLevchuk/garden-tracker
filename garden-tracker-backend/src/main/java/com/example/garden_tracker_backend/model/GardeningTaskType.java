package com.example.garden_tracker_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "gardening_task_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GardeningTaskType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private Category category;
    @OneToMany(mappedBy = "gardeningTaskType")
    private List<GardeningTask> gardeningTasks;
}
