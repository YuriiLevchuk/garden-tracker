package com.example.garden_tracker_backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

@SpringBootTest
@Testcontainers
class GardenTrackerBackendApplicationTests {

	@Container
	@ServiceConnection
	public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:16-alpine");

	@Test
	void contextLoads() {
	}

}
