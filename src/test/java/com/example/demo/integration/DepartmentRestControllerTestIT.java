package com.example.demo.integration;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.example.demo.entity.Department;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class DepartmentRestControllerTestIT {

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
    void saveDepartment() {
        // Given
        Department departmentToSave = new Department(1, "HR", null);
        
        String url = "http://localhost:" + port + "/api/department/save";

        // When
        ResponseEntity<Department> responseEntity = restTemplate.postForEntity(url, departmentToSave, Department.class);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        
        Optional<Department> savedDepartmentOptional = departmentRepository.findById(responseEntity.getBody().getId());
        assertTrue(savedDepartmentOptional.isPresent());
        assertEquals("HR", savedDepartmentOptional.get().getName());
    }
    
    
    @Test
    void getAllDepartments() {
        // Given
        Department department1 = new Department(1, "HR", null);
        Department department2 = new Department(2, "Sales", null);
        departmentRepository.save(department1);
        departmentRepository.save(department2);

        String url = "http://localhost:" + port + "/api/department/getAll";

        // When
        ResponseEntity<List<Department>> responseEntity = restTemplate.exchange(
            url, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<Department>>() {});

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Department> departments = responseEntity.getBody();
        assertNotNull(departments);
        assertFalse(departments.isEmpty());
        assertEquals(2, departments.size());
        assertEquals("HR", departments.get(0).getName());
        assertEquals("Sales", departments.get(1).getName());
    }
    
    @Test
    void deleteDepartmentById() {
        // Given
        Department department = new Department(1, "HR", null);
        departmentRepository.save(department);
        String url = "http://localhost:" + port + "/api/department/delete/" + department.getId();

        // When
        restTemplate.delete(url);

        // Then
        assertFalse(departmentRepository.existsById(department.getId()));
    }

}
