package com.example.demo.e2e;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.dao.DepartmentRepository;
import com.example.demo.entity.Department;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GetAllDepartmentsStep {
	
	
	@Autowired
    private DepartmentRepository departmentRepository;

    private List<Department> departments = new ArrayList<>();

    @Given("There are departments in the system")
    public void there_are_departments_in_the_system() {
    	Department department = new Department();
    	department.setName("Hr");
    	departmentRepository.save(department);
    }

    @When("I request to get all departments")
    public void i_request_to_get_all_departments() {
    	departments.addAll(departmentRepository.findAll());
    }

    @Then("I should receive a list of departments")
    public void i_should_receive_a_list_of_departments() {
        assertNotNull(departments);
    }

    @Then("the response should contain departments details")
    public void the_response_should_contain_departments_details() {
        assertNotNull(departments);
        for (Department department : departments) {
            System.out.println("Department ID: " + department.getId() + ", Name: " + department.getName());
        }
    }

}
