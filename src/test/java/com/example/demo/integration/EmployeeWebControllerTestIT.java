package com.example.demo.integration;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.example.demo.dao.EmployeeRepository;
import com.example.demo.entity.Employee;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class EmployeeWebControllerTestIT {

	@Container
	private static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:5.7").withDatabaseName("mydb")
			.withUsername("root").withPassword("password").withReuse(true);

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private EmployeeRepository employeeRepository;

	@BeforeEach
	public void setUp() {
		employeeRepository.deleteAll();
	}

	@Autowired
	private DepartmentRepository departmentRepository;

	@BeforeEach
	public void setUpDepartments() {
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
	void getAllEmployees() {
		// Given
		Employee employee1 = new Employee();
		employee1.setFirstName("test1");
		employee1.setLastName("lastname1");

		employeeRepository.save(employee1);

		// When
		String url = "http://localhost:" + port + "/employees/";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		// Then
		System.out.println("Response Body: " + response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	void showAddEmployeeForm() {
		// When
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/employees/add",
				String.class);
		// Then
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void showEditEmployeeForm() {
		// Given
		Employee employee = new Employee(1, "test1", "lastname1", null);
		employeeRepository.save(employee);
		// When
		String url = "http://localhost:" + port + "/employees/edit/" + employee.getId();
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		// Then
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void deleteEmployee() {
	    // Given
	    Employee employee = new Employee(1, "test1", "lastname1", null);
	    employeeRepository.save(employee);	    
	    // When
	    String url = "http://localhost:" + port + "/employees/delete/" + employee.getId();
	    ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, Void.class);	   
	    if (responseEntity.getStatusCode().is3xxRedirection()) {
	        HttpHeaders headers = responseEntity.getHeaders();
	        String redirectUrl = headers.getLocation().toString();
	        responseEntity = restTemplate.exchange(redirectUrl, HttpMethod.GET, null, Void.class);
	    }	    
	    // Then
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    Optional<Employee> deletedEmployee = employeeRepository.findById(employee.getId());
	    assertFalse(deletedEmployee.isPresent());
	}


}
