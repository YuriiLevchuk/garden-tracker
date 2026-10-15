package com.example.garden_tracker_backend;

import org.springframework.boot.SpringApplication;

public class TestGardenTrackerBackendApplication {

	public static void main(String[] args) {
		SpringApplication.from(GardenTrackerBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
