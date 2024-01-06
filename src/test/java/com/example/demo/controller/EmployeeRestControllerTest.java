package com.example.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;

@SpringBootTest
public class EmployeeRestControllerTest {
	private MockMvc mockMvc;

	@Mock
	private EmployeeService employeeService;

	@InjectMocks
	private EmployeeController employeeController;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
	}

	@Test
	void saveEmployee() throws Exception {
		// Create a mock employee to be saved
		Employee mockEmployeeToSave = new Employee(1, "test1", "lastname1", null);
		Employee mockSavedEmployee = new Employee(1, "test2", "lastname2", null);

		// Mock employeeService's saveEmployee method
		when(employeeService.saveEmployee(any(Employee.class))).thenReturn(mockSavedEmployee);

		// Perform a POST request
		mockMvc.perform(post("/employees/save").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(mockEmployeeToSave))).andExpect(status().isOk());

		// Verify that the saveEmployee
		verify(employeeService, times(1)).saveEmployee(any(Employee.class));
	}
}
