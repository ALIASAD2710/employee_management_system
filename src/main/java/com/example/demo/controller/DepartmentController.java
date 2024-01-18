package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.dto.DepartmentDto;
import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;

@RestController
@RequestMapping("/department")
public class DepartmentController 
{
	
	private final DepartmentService departmentService;
	
	@Autowired
	public DepartmentController(DepartmentService departmentService)
	{
		this.departmentService = departmentService;
	}
	
	@PostMapping("/save")
	public ResponseEntity<DepartmentDto> saveDepartment(@RequestBody DepartmentDto departmentDto)
	{
			
		Department department = new Department(
				
				departmentDto.getId(),
				departmentDto.getName(),
				departmentDto.getEmployees()
				);
		Department saveDepartment = departmentService.saveDepartment(department);
		
		DepartmentDto saveDepartmentDto = new DepartmentDto(
				
				saveDepartment.getId(),
				saveDepartment.getName(),
				saveDepartment.getEmployees()
				);
		
		return new ResponseEntity<>(saveDepartmentDto, HttpStatus.CREATED);
	}
	

}
