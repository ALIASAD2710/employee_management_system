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
    void saveDepartment() throws Exception 
    {
    	
    	// Create a mock department to be saved
    	 Department mockdepartmentToSave = new Department(1, "HR", null);
         Department mockSaveddepartment = new Department(2, "Sales", null);

      // Mock 
         when(departmentService.saveDepartment(any(Department.class))).thenReturn(mockSaveddepartment);

      // Perform a POST request
         mockMvc.perform(post("/department/save")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(new ObjectMapper().writeValueAsString(mockdepartmentToSave)))
                 .andExpect(status().isCreated());

      // Verify 
         verify(departmentService, times(1)).saveDepartment(any(Department.class));
    }
    
    @Test
    void saveDepartment_withValidationErrors() throws Exception 
	{
        // Create a mock department with validation errors
        Department mockdepartmentToSave = new Department(1, null, null);

        // Mock BindingResult with errors
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(List.of(new ObjectError("department", "Field cannot be null")));

        // POST request
        mockMvc.perform(post("/department/save").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockdepartmentToSave))).andExpect(status().isBadRequest());

        // Verify 
        verify(departmentService, never()).saveDepartment(any(Department.class));
    }
    
    
    @Test
    void getAllDepartments() throws Exception 
    {
    	
    	List<Department> department = new ArrayList<>();
    	department.add(new Department(1, "HR", null));
    	department.add(new Department(2, "Sale", null));
    	
        // Mock
        when(departmentService.getAllDepartments()).thenReturn(department);

        // Perform GET request and assertions
        mockMvc.perform(MockMvcRequestBuilders.get("/department/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists());
    }
}
