package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

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
            // Extract field-specific validation errors
            List<String> validationErrors = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());

            // Build a dynamic error message
            String errorMessage = "Validation errors: " + String.join(", ", validationErrors);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        Employee savedEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }
}
