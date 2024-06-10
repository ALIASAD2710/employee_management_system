package com.example.demo.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;


@SpringBootTest
class DepartmentWebControllerTest 
{
	
	@Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentWebController departmentWebController;

    @Test
    void showAllDepartments() throws Exception {
    	// Create
        Department department1 = new Department(1, "HR", null);
        Department department2 = new Department(2, "Sales", null);
        when(departmentService.getAllDepartments()).thenReturn(Arrays.asList(department1, department2));        
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(departmentWebController).build();

     // GET request
        mockMvc.perform(get("/departments/"))
                .andExpect(status().isOk())
                .andExpect(view().name("departments"))
                .andExpect(model().attributeExists("departments"))
                .andExpect(model().attribute("departments", Arrays.asList(department1, department2)));
    }
	
	@Test
    void showAddDepartmentForm() throws Exception {       
       
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(departmentWebController).build();

        // GET request
        mockMvc.perform(get("/departments/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-department")) 
                .andExpect(model().attributeExists("department"));
    }
	
	@Test
    void addDepartment() throws Exception {
		// Create
        Department department = new Department(1, "HR", null);
        
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(departmentWebController).build();

        // POST request
        mockMvc.perform(post("/departments/add")
                .flashAttr("department", department))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/departments/"));

        // Verify
        verify(departmentService, times(1)).saveDepartment(department);
    }
	
	
	@Test
    void showEditDepartmentForm() throws Exception {
		// Create
        Department department = new Department(1, "HR", null);
        when(departmentService.getDepartmentById(1)).thenReturn(Optional.of(department));
        
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(departmentWebController).build();

        // GET request
        mockMvc.perform(get("/departments/edit/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-department")) 
                .andExpect(model().attributeExists("department")) 
                .andExpect(model().attribute("department", department));
    }
	
	
	@Test
	void editDepartment() throws Exception {
	    // Create department object
	    Department department = new Department();
	    department.setName("Updated Department");
	    
	    int id = 1;

	    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(departmentWebController).build();

	    // POST request
	    mockMvc.perform(post("/departments/edit/{id}", id)
	            .flashAttr("department", department))
	            .andExpect(status().is3xxRedirection())
	            .andExpect(redirectedUrl("/departments/"));

	    // Verify
	    ArgumentCaptor<Department> departmentCaptor = ArgumentCaptor.forClass(Department.class);
	    verify(departmentService, times(1)).saveDepartment(departmentCaptor.capture());
	    
	    Department capturedDepartment = departmentCaptor.getValue();
	    //Assert
	    assertEquals(id, capturedDepartment.getId());
	    assertEquals("Updated Department", capturedDepartment.getName());
	}

	

	
	@Test
    void deleteDepartment() throws Exception {
        
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(departmentWebController).build();

        // GET request
        mockMvc.perform(get("/departments/delete/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/departments/"));

        // Verify
        verify(departmentService, times(1)).deleteDepartmentById(1);
    }

}
