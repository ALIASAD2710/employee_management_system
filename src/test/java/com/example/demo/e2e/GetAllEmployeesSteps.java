package com.example.demo.e2e;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.dao.EmployeeRepository;
import com.example.demo.entity.Employee;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GetAllEmployeesSteps {

    @Autowired
    private EmployeeRepository employeeRepository;

    private List<Employee> employees = new ArrayList<>();

    @Given("There are employees in the system")
    public void there_are_employees_in_the_system() {
        Employee employee1 = new Employee();
        employee1.setFirstName("Asad");
        employee1.setLastName("Ali");
        employeeRepository.save(employee1);
    }

    @When("I request to get all employees")
    public void i_request_to_get_all_employees() {
        employees.addAll(employeeRepository.findAll());
    }

    @Then("I should receive a list of employees")
    public void i_should_receive_a_list_of_employees() {
        assertNotNull(employees);
    }

    @Then("the response should contain employee details")
    public void the_response_should_contain_employee_details() {
        assertNotNull(employees);
        for (Employee employee : employees) {
            System.out.println("Employee ID: " + employee.getId() + ", Name: " + employee.getFirstName() + " " + employee.getLastName());
        }
    }
}
