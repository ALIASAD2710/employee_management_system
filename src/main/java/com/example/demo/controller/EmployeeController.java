package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
	public ResponseEntity<?> saveEmployee(@Valid @RequestBody Employee employee, BindingResult bindingResult) {
	    if (bindingResult.hasErrors()) {
	        // Handle validation errors
	        return ResponseEntity.badRequest().body("Validation errors");
	    }

	    Employee savedEmployee = employeeService.saveEmployee(employee);
	    return ResponseEntity.ok(savedEmployee);
	}
}
