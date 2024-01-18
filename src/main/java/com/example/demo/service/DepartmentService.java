package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dao.DepartmentRepository;
import com.example.demo.entity.Department;

@Service
public class DepartmentService {
	
	
	private final DepartmentRepository departmentRepository;
	
	
	public DepartmentService(DepartmentRepository departmentRepository)
	{
		this.departmentRepository = departmentRepository;
	}

	public Department saveDepartment(Department department) 
	{
		
		return departmentRepository.save(department);
	}

}
