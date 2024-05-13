Feature: Get All Employees
  As a user
  I want to retrieve all employees
  So that I can view the list of employees

  Scenario: Retrieve all employees
    Given There are employees in the system
    When I request to get all employees
    Then I should receive a list of employees
    And the response should contain employee details
