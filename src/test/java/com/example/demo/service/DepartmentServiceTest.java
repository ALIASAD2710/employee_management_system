package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dao.DepartmentRepository;
import com.example.demo.entity.Department;

@SpringBootTest
class DepartmentServiceTest 
{
	
	@Mock
	private DepartmentRepository departmentRepository;
	
	@InjectMocks
	private DepartmentService departmentService;
	
	@Test
	void saveDepartment()
	{
		
		// create 
		Department mockDepartmentToSave = new Department(1, "HR", null);
		Department mockSaveddepartment = new Department(2, "Sales", null);
		
		//when Mock
		when(departmentRepository.save(any(Department.class))).thenReturn(mockSaveddepartment);
		
		// call to save
		Department savedDepartment = departmentService.saveDepartment(mockDepartmentToSave);
		
		//verify
		verify(departmentRepository, times(1)).save(mockDepartmentToSave);
		
		//Assert
		assertEquals(mockSaveddepartment, savedDepartment);
		
	}
	
	
	@Test
    void getAllDepartments() 
	{
		List<Department> department = new ArrayList<>();
    	department.add(new Department(1, "HR", null));
    	department.add(new Department(2, "Sale", null));
    	
        // Mock
        when(departmentRepository.findAll()).thenReturn(department);

        
        List<Department> departments = departmentService.getAllDepartments();

        // Assert
        assertEquals(2, departments.size());
    }
	
	
	
}
