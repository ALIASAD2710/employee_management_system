Feature: Add Department
  As a user
  I want to add a department
  So that I can manage the list of departments

  Scenario: Add a new department
    Given I have a department with name "IT"
    When I add the department
    Then The department should be added successfully
   