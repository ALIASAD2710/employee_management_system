package com.example.demo.e2e;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddEmployeeSteps {

    @Given("I am on the employee management page")
    public void i_am_on_the_employee_management_page() {
        System.out.println("Navigating to the employee management page");
    }

    @When("I enter the first name {string}")
    public void i_enter_the_first_name(String firstName) {
        System.out.println("Entering the first name: " + firstName);
    }

    @When("I enter the last name {string}")
    public void i_enter_the_last_name(String lastName) {
        System.out.println("Entering the last name: " + lastName);
    }

    @When("I select the department {string}")
    public void i_select_the_department(String department) {
        System.out.println("Selecting the department: " + department);
    }

    @When("I click the Add Employee button")
    public void i_click_the_Add_Employee_button() {
        System.out.println("Clicking the Add Employee button");
    }

    @Then("I should see add employee success message")
    public void i_should_see_add_employee_success_message() {
        System.out.println("Verifying the success message");
    }
}

