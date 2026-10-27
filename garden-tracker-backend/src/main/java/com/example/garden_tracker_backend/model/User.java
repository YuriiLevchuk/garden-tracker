package com.example.garden_tracker_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @OneToMany(mappedBy = "owner")
    private List<Garden> gardens;
    @OneToMany(mappedBy = "user")
    private List<GardeningTask> gardeningTasks;
}
