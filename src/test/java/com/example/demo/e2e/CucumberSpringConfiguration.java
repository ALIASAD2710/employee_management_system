package com.example.demo.e2e;

import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.EmployeeManagementSystemApplication;

import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = {EmployeeManagementSystemApplication.class})
public class CucumberSpringConfiguration 
{
}
