package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.example.demo.dao.EmployeeRepository;
import com.example.demo.entity.Employee;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;

	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}
	@Transactional(readOnly = true)
	public List<Employee> getAllEmployee() {
		return employeeRepository.findAll();
	}
	@Transactional(readOnly = true)
	public Optional<Employee> getEmployeeById(int id) {
		return employeeRepository.findById(id);
	}


	public void deleteEmployeeById(int id) {
		employeeRepository.deleteById(id);
	}
	
	
}
