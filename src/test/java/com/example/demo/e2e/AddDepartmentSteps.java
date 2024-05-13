package com.example.demo.e2e;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.controller.DepartmentWebController;
import com.example.demo.entity.Department;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddDepartmentSteps {

    @Autowired
    private DepartmentWebController departmentWebController;

    private Department department;

    @Given("I have a department with name {string}")
    public void i_have_a_department_with_name(String departmentName) {
        department = new Department();
        department.setName(departmentName);
    }

    @When("I add the department")
    public void i_add_the_department() {
    	departmentWebController.addDepartment(department);
    }

    @Then("The department should be added successfully")
    public void the_department_should_be_added_successfully() {
        assertNotNull(department, "Department should not be null");
        assertTrue(department.getId() > 0, "Department ID should be a positive integer");
    }

}
