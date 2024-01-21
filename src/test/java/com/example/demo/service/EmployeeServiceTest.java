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

import com.example.demo.dao.EmployeeRepository;
import com.example.demo.entity.Employee;

@SpringBootTest
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void saveEmployee() 
    {
        // Create a mock employee to be saved
        Employee mockEmployeeToSave = new Employee(1, "test1", "lastname1", null);
        Employee mockSavedEmployee = new Employee(2, "test2", "lastname2", null);

        // Mock employeeRepository's save method
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockSavedEmployee);

        // Call the saveEmployee method
        Employee savedEmployee = employeeService.saveEmployee(mockEmployeeToSave);

        // Verify 
        verify(employeeRepository, times(1)).save(mockEmployeeToSave);

        // Assert 
        assertEquals(mockSavedEmployee, savedEmployee);
    }
    
    
    @Test
    void testGetAllEmployee() 
	{
		List<Employee> employee = new ArrayList<>();
		employee.add(new Employee(1, "test1", "lastname1", null));
		employee.add(new Employee(2, "test2", "lastname2", null));
    	
        // Mock
        when(employeeRepository.findAll()).thenReturn(employee);

        
        List<Employee> employees = employeeService.getAllEmployee();

        // Assert
        assertEquals(2, employees.size());
    }
}


