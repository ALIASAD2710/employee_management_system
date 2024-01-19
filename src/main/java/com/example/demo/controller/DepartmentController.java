package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/department")
public class DepartmentController 
{
	
	private final DepartmentService departmentService;
	
	public DepartmentController(DepartmentService departmentService)
	{
		this.departmentService = departmentService;
	}
	
	@PostMapping("/save")
	public ResponseEntity<Object> saveDepartment(@Valid @RequestBody Department department, BindingResult bindingResult)
	{
		if (bindingResult.hasErrors()) {
            // validation errors
            List<String> validationErrors = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .toList();  

            // Build a dynamic error message
            String errorMessage = "Validation errors: " + String.join(", ", validationErrors);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
		
		Department savedDepartment = departmentService.saveDepartment(department);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedDepartment);
	}
	
	
	
	
	

}
