package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import org.springframework.validation.FieldError;

import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class DepartmentRestControllerTest {

	private MockMvc mockMvc;

	@Mock
	DepartmentService departmentService;

	@InjectMocks
	private DepartmentController departmentController;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
	}

	@Test
	void saveDepartment_200() throws Exception 
	{
		// Create
		Department mockDepartmentToSave = new Department(1, "HR", null);
		Department mockSavedDepartment = new Department(2, "Sales", null);
		// Mock
		when(departmentService.saveDepartment(any(Department.class))).thenReturn(mockSavedDepartment);
		// Perform a POST request
		mockMvc.perform(post("/api/department/save").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(mockDepartmentToSave))).andExpect(status().isCreated());
		// Verify
		verify(departmentService, times(1)).saveDepartment(any(Department.class));
	}

	@Test
	void saveDepartment_400() throws Exception {
		// Create
		Department mockDepartmentToSave = new Department(1, null, null);

		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		when(bindingResult.getFieldErrors())
				.thenReturn(List.of(new FieldError("department", "name", "Please write your department name")));
		// POST request
		mockMvc.perform(post("/api/department/save").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(mockDepartmentToSave)))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value("Validation errors"))
				.andExpect(jsonPath("$.details[0].field").value("name"))
				.andExpect(jsonPath("$.details[0].errorMessage").value("Please write your department name"));
		// Verify
		verify(departmentService, never()).saveDepartment(any(Department.class));
	}

	@Test
	void saveDepartment_500() throws Exception {
		// Create
		Department mockDepartmentToSave = new Department(1, "HR", null);

		when(departmentService.saveDepartment(any(Department.class))).thenThrow(new RuntimeException("Error"));
		// Perform a POST request
		mockMvc.perform(post("/api/department/save").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(mockDepartmentToSave)))
				.andExpect(status().isInternalServerError());
		// Verify
		verify(departmentService, times(1)).saveDepartment(any(Department.class));
	}

	@Test
	void getAllDepartments() throws Exception {

		List<Department> department = new ArrayList<>();
		department.add(new Department(1, "HR", null));
		department.add(new Department(2, "Sale", null));

		// Mock
		when(departmentService.getAllDepartments()).thenReturn(department);

		// Perform GET request and assertions
		mockMvc.perform(MockMvcRequestBuilders.get("/api/department/getAll").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists());
	}

	@Test
	void getDepartmentById_200() throws Exception {
		// Create
		Department mockdepartmentToSave = new Department(1, "HR", new ArrayList<>());

		// Mock
		when(departmentService.getDepartmentById(1)).thenReturn(Optional.of(mockdepartmentToSave));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/department/{id}", 1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("HR"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.employees").isArray());

		// Verify
		verify(departmentService, times(1)).getDepartmentById(1);
	}

	@Test
	void getDepartmentById_400() throws Exception {

		// Mock
		when(departmentService.getDepartmentById(1)).thenReturn(Optional.empty());

		mockMvc.perform(MockMvcRequestBuilders.get("/api/department/{id}", 1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());

		// Verify
		verify(departmentService, times(1)).getDepartmentById(1);

	}

	@Test
	void deleteDepartmentById() throws Exception {
		int id = 1;
		// Mock
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/department/delete/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
		// Verify
		verify(departmentService, times(1)).deleteDepartmentById(id);
	}

}
