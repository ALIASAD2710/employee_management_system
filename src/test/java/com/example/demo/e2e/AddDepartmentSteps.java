package com.example.demo.e2e;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddDepartmentSteps {

    @Given("I am on the department management page")
    public void i_am_on_the_department_management_page() {
    	System.out.println("Navigating to the add department page");
    }

    @When("I enter the department name {string}")
    public void i_enter_the_department_name(String departmentName) {
    	System.out.println("Entering the first name: " + departmentName);
    }

    @When("I click the Add Department button")
    public void i_click_the_Add_Department_button() {
    	System.out.println("Clicking the Add Department button");
    }

    @Then("I should see add department success message")
    public void i_should_see_add_department_success_message() {
    	System.out.println("Verifying the success message");
    }
}
