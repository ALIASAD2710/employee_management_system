package com.example.demo.service;

import java.util.List;
import java.util.Optional;

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

	public List<Department> getAllDepartments() 
	{
		
		return departmentRepository.findAll();
	}
	
	
	public Optional<Department> getDepartmentById(int id) {
		return departmentRepository.findById(id);
	}
	
	public void deleteDepartmentById(int id) {
		departmentRepository.deleteById(id);
	}

}
