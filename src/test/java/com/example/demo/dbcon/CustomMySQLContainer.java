package com.example.demo.dbcon;

import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.time.Duration;

public class CustomMySQLContainer extends MySQLContainer<CustomMySQLContainer> {
	private static final String IMAGE_VERSION = "mysql:5.7";
	private static CustomMySQLContainer container;

	private CustomMySQLContainer() {
		super(IMAGE_VERSION);
		withDatabaseName("mydb");
		withUsername("root");
		withPassword("pass");
		setWaitStrategy(Wait.forListeningPort().withStartupTimeout(Duration.ofMinutes(2)));
	}

	public static CustomMySQLContainer getInstance() {
		if (container == null) {
			container = new CustomMySQLContainer();
		}
		return container;
	}

	@Override
	public void start() {
		super.start();
		System.setProperty("DB_URL", container.getJdbcUrl());
		System.setProperty("DB_USERNAME", container.getUsername());
		System.setProperty("DB_PASSWORD", container.getPassword());
		System.out.println("DB_URL: " + container.getJdbcUrl());
		System.out.println("DB_USERNAME: " + container.getUsername());
		System.out.println("DB_PASSWORD: " + container.getPassword());
	}

	@Override
	public void stop() {
		
	}
}
