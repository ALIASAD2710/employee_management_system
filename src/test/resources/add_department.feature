Feature: Add Department

  Scenario: Adding a new department
    Given I am on the department management page
    When I enter the department name "IT"
    And I click the Add Department button
    Then I should see add department success message
