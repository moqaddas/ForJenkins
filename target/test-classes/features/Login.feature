Feature: Login scenarios

  @login @smoke @regression @invalid
  Scenario: admin login
    #Given user is able to access HRMS application
    When user enters admin username and admin password
    And user clicks on login button
    Then user is navigated to dashbaord page


  @invalid
  Scenario: invalid admin login
    When user enters invalid username and password
    And user clicks on login button
    Then user can see error message

