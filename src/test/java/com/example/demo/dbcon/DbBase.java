package com.example.demo.dbcon;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@ActiveProfiles("ite2etests")
public abstract class DbBase {

	private static final CustomMySQLContainer mysqlContainer = CustomMySQLContainer.getInstance();

	@BeforeAll
	public static void startContainer() {
		mysqlContainer.start();
	}

	@AfterAll
	public static void stopContainer() {
		
	}
}


