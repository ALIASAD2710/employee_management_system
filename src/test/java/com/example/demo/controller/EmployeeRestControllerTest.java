package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class EmployeeRestControllerTest {
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
	void saveEmployee_200() throws Exception {
		// Create
		Employee mockEmployeeToSave = new Employee(1, "test1", "lastname1", null);
		Employee mockSavedEmployee = new Employee(2, "test2", "lastname2", null);

		// Mock
		when(employeeService.saveEmployee(any(Employee.class))).thenReturn(mockSavedEmployee);

		// Perform
		mockMvc.perform(post("/api/employees/save").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(mockEmployeeToSave))).andExpect(status().isCreated());

		// Verify
		verify(employeeService, times(1)).saveEmployee(any(Employee.class));
	}

	@Test
	void saveEmployee_400() throws Exception {
		// Create
		Employee mockEmployeeToSave = new Employee(1, null, null, null);

		// Mock
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);
		when(bindingResult.getAllErrors()).thenReturn(List.of(new ObjectError("employee", "Field cannot be null")));

		// Perform
		mockMvc.perform(post("/api/employees/save").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(mockEmployeeToSave))).andExpect(status().isBadRequest());

		// Verify
		verify(employeeService, never()).saveEmployee(any(Employee.class));
	}

	@Test
	void getAllEmployees() throws Exception {
		// Create
		List<Employee> employee = new ArrayList<>();
		employee.add(new Employee(1, "test1", "lastname1", null));
		employee.add(new Employee(2, "test2", "lastname2", null));

		// Mock
		when(employeeService.getAllEmployee()).thenReturn(employee);

		// Perform
		mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/getAll").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists());
	}

	@Test
	void getEmployeeById_200() throws Exception {
		// Create
		Employee mockEmployee = new Employee(1, "test1", "lastname1", null);
		when(employeeService.getEmployeeById(1)).thenReturn(Optional.of(mockEmployee));

		// Perform
		mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", 1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("test1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("lastname1"));

		// Verify
		verify(employeeService, times(1)).getEmployeeById(1);
	}

	@Test
	void getEmployeeById_400() throws Exception {
		// Create
		when(employeeService.getEmployeeById(1)).thenReturn(Optional.empty());

		// Perform
		mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", 1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());

		// Verify
		verify(employeeService, times(1)).getEmployeeById(1);
	}



	@Test
	void deleteEmployeeById() throws Exception {
		int employeeId = 1;

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/delete/{id}", employeeId)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());

		verify(employeeService, times(1)).deleteEmployeeById(employeeId);
	}

	

}
