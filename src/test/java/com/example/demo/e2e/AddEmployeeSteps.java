package com.example.demo.e2e;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.controller.EmployeeWebController;
import com.example.demo.entity.Employee;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddEmployeeSteps {

    @Autowired
    private EmployeeWebController employeeWebController;

    private Employee employee;

    @Given("I have an employee with name {string} and last name {string}")
    public void i_have_an_employee_with_name_and_last_name(String firstName, String lastName) {
        employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
    }

    @When("I add the employee")
    public void i_add_the_employee() {
        employeeWebController.addEmployee(employee);
    }

    @Then("The employee should be added successfully")
    public void the_employee_should_be_added_successfully() {
        assertNotNull(employee.getId(), "Employee ID should not be null");
    }
}

