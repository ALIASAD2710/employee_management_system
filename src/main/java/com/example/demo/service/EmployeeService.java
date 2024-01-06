package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.EmployeeRepository;
import com.example.demo.entity.Employee;

@Service
public class EmployeeService {
	
	@Autowired
    private EmployeeRepository employeeRepository;
	
	public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

}
