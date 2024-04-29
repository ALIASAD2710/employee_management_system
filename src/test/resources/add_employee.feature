Feature: Add Employee

  Scenario: Adding a new employee
    Given I am on the employee management page
    When I enter the first name "Asad"
    And I enter the last name "Ali"
    And I select the department "IT"
    And I click the Add Employee button
    Then I should see add employee success message
