Feature: Get All Departments
  As a user
  I want to retrieve all departments
  So that I can view the list of departments

  Scenario: Retrieve all departments
    Given There are departments in the system
    When I request to get all departments
    Then I should receive a list of departments
    And the response should contain departments details
