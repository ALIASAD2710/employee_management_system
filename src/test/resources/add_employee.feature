Feature: Add Employee
  As a user
  I want to add an employee
  So that I can manage the list of employees

  Scenario: Add a new employee
    Given I have an employee with name "Asad" and last name "Khan"
    When I add the employee
    Then The employee should be added successfully
    