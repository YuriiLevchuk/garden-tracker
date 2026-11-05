package com.example.garden_tracker_backend.repository;

import com.example.garden_tracker_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/** Database access for application users. */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
