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
    void saveEmployee() throws Exception {
        // Create a mock employee to be saved
        Employee mockEmployeeToSave = new Employee(1, "test1", "lastname1", null);
        Employee mockSavedEmployee = new Employee(2, "test2", "lastname2", null);

        // Mock employeeService's saveEmployee method
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(mockSavedEmployee);

        // Perform a POST request
        mockMvc.perform(post("/employees/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockEmployeeToSave)))
                .andExpect(status().isCreated());

        // Verify 
        verify(employeeService, times(1)).saveEmployee(any(Employee.class));
    }
	
	
	@Test
    void saveEmployee_withValidationErrors() throws Exception 
	{
        // Create a mock employee with validation errors
        Employee mockEmployeeToSave = new Employee(1, null, null, null);

        // Mock BindingResult with errors
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(List.of(new ObjectError("employee", "Field cannot be null")));

        // POST request
        mockMvc.perform(post("/employees/save").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockEmployeeToSave))).andExpect(status().isBadRequest());

        // Verify 
        verify(employeeService, never()).saveEmployee(any(Employee.class));
    }
	
	
	@Test
    void getAllEmployees() throws Exception 
    {
    	
    	List<Employee> employee = new ArrayList<>();
    	employee.add(new Employee(1, "test1", "lastname1", null));
    	employee.add(new Employee(2, "test2", "lastname2", null));
    	
        // Mock
        when(employeeService.getAllEmployee()).thenReturn(employee);

        // Perform GET request and assertions
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists());
    }
}
