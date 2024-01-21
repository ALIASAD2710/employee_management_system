package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController 
{
	
	private final EmployeeService employeeService;
	
	
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

	
    
    @PostMapping("/save")
    public ResponseEntity<Object> saveEmployee(@Valid @RequestBody Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // validation errors
            List<String> validationErrors = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .toList();  

            // error message
            String errorMessage = "Validation errors: " + String.join(", ", validationErrors);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        Employee savedEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    
    @GetMapping("/getAll")
    public ResponseEntity<List<Employee>> getAllDepartments() 
	{
        List<Employee> employee = employeeService.getAllEmployee();
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
	
}
