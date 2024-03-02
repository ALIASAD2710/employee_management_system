package com.example.demo.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.controller.EmployeeController;

@SpringBootTest
class EmployeeManagementSystemIntegrationTests 
{
	@Autowired
	EmployeeController employeeController;
	@Test
	void contextLoads() 
	{
		assertNotNull(employeeController);
	}

}