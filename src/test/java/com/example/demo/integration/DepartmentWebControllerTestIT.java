package com.example.demo.integration;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.http.HttpHeaders;

import com.example.demo.dao.DepartmentRepository;
import com.example.demo.entity.Department;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class DepartmentWebControllerTestIT {

	@Container
	private static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:5.7").withDatabaseName("mydb")
			.withUsername("root").withPassword("password").withReuse(true);

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private DepartmentRepository departmentRepository;

	@BeforeEach
	public void setUp() {
		departmentRepository.deleteAll();
	}

	@BeforeAll
	public static void beforeAll() {
		mysql.start();
	}

	@AfterAll
	public static void afterAll() {
		mysql.stop();
	}

	@Test
	void showAllDepartments() {
		// Given
		Department department1 = new Department(1, "HR", null);
		departmentRepository.save(department1);
		
		//When
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/departments/",
				String.class);
		
		//Then
		System.out.println("Response Body: " + response.getBody());

		assertEquals(HttpStatus.OK, response.getStatusCode());

		
		assertTrue(response.getBody().contains(department1.getName()));

	}

	@Test
	void showAddDepartmentForm() 
	{
		//When
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/departments/add",
				String.class);
		//Then
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void deleteDepartment() {
	    // Given
	    Department department = new Department(1, "HR", null);
	    departmentRepository.save(department);	    
	    System.out.println("department.getId(): " + department.getId());	    
	    // When
	    String url = "http://localhost:" + port + "/departments/delete/" + department.getId();	    
	    System.out.println("url: " + url);
	    ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, Void.class);
	    
	    if (responseEntity.getStatusCode().is3xxRedirection()) {
	        HttpHeaders headers = responseEntity.getHeaders();
	        String redirectUrl = headers.getLocation().toString();
	        responseEntity = restTemplate.exchange(redirectUrl, HttpMethod.GET, null, Void.class);
	    }	    
	    // Then
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    Optional<Department> deletedDepartment = departmentRepository.findById(department.getId());
	    assertFalse(deletedDepartment.isPresent());
	}



}
