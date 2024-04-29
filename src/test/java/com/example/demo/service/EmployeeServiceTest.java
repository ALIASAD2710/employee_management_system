package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        // Create
        Employee mockEmployeeToSave = new Employee(1, "test1", "lastname1", null);
        Employee mockSavedEmployee = new Employee(2, "test2", "lastname2", null);

        // Mock
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockSavedEmployee);

        
        Employee savedEmployee = employeeService.saveEmployee(mockEmployeeToSave);

        // Verify 
        verify(employeeRepository, times(1)).save(mockEmployeeToSave);

        // Assert 
        assertEquals(mockSavedEmployee, savedEmployee);
    }
    
    
    @Test
    void testGetAllEmployee() 
	{
    	// Create
		List<Employee> employee = new ArrayList<>();
		employee.add(new Employee(1, "test1", "lastname1", null));
		employee.add(new Employee(2, "test2", "lastname2", null));
    	
        // Mock
        when(employeeRepository.findAll()).thenReturn(employee);

        
        List<Employee> employees = employeeService.getAllEmployee();

        // Assert
        assertEquals(2, employees.size());
    }
    
    
    @Test
    void getEmployeeById() {
    	// Create
        Employee mockEmployee = new Employee(1, "test1", "lastname1", null);
        when(employeeRepository.findById(1)).thenReturn(Optional.of(mockEmployee));

        
        Optional<Employee> result = employeeService.getEmployeeById(1);

        // Verify
        assertTrue(result.isPresent());
        assertEquals(mockEmployee, result.get());
        verify(employeeRepository, times(1)).findById(1);
    }
    
    

    
    @Test
    void deleteEmployeeById() {
        
        int id = 1;

        // Mock
        doNothing().when(employeeRepository).deleteById(id);

        
        employeeService.deleteEmployeeById(id);

        // Verify
        verify(employeeRepository, times(1)).deleteById(id);
    }
}


