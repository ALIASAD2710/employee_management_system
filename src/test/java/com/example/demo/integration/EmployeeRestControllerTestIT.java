package com.example.demo.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.demo.dao.DepartmentRepository;
import com.example.demo.dao.EmployeeRepository;
import com.example.demo.entity.Employee;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class EmployeeRestControllerTestIT 
{
	
	@Container
    private static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:5.7")
            .withDatabaseName("mydb")
            .withUsername("root")
            .withPassword("password")
            .withReuse(true);

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
    void saveEmployee() {
        // Given
        Employee employeeToSave = new Employee(1,"test1", "lastname1", null);

        String url = "http://localhost:" + port + "/api/employees/save";

        // When
        ResponseEntity<Employee> responseEntity = restTemplate.postForEntity(url, employeeToSave, Employee.class);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

       
        Optional<Employee> savedEmployeeOptional = employeeRepository.findById(responseEntity.getBody().getId());
        assertTrue(savedEmployeeOptional.isPresent());
        assertEquals("test1", savedEmployeeOptional.get().getFirstName());
    }

    @Test
    void getAllEmployees() {
        // Given
        Employee employee1 = new Employee(1,"test1", "lastname1", null);
        employeeRepository.save(employee1);

        String url = "http://localhost:" + port + "/api/employees/getAll";

        // When
        ResponseEntity<List<Employee>> responseEntity = restTemplate.exchange(
            url, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<Employee>>() {});

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Employee> employees = responseEntity.getBody();
        assertNotNull(employees);
        assertFalse(employees.isEmpty());
        assertEquals(1, employees.size());
        assertEquals("test1", employees.get(0).getFirstName());
    }
    @Test
    void deleteEmployeeById() {
        // Given
        Employee employee = new Employee(1,"test1", "lastname1", null);
        employeeRepository.save(employee);
        String url = "http://localhost:" + port + "/api/employees/delete/" + employee.getId();

        // When
        restTemplate.delete(url);

        // Then
        assertFalse(employeeRepository.existsById(employee.getId()));
    }

}
