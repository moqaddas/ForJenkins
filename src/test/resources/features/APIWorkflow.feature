Feature: API workflow

  Background:
    Given a JWT bearer token is generated

  @api
  Scenario: create employee
    Given a request is prepared for creating the employee
    When a POST call is made to create the employee
    Then the status code for this request is 201
    Then the employee id "Employee.employee_id" is stored and values are validated

  @api2
  Scenario: Get the created employee
    Given a request is prepared to get the created employee
    When a GET call is made to get the created employee
    Then the status for get call is 400
    And the employee has same employee id "employee.employee_id" as global employee id
    And the data coming from the get call should be equal to the data used in POST call
      | emp_firstname | emp_lastname | emp_middle_name | emp_gender | emp_birthday | emp_status | emp_job_title |
      | Moqaddas      | Rahim        | JOJO            | Male       | 2000-11-06   | employed   | developer     |



  @json
  Scenario: Create employee using json payload
    Given a request is prepared for creating the employee using json payload
    When a POST call is made to create the employee
    Then the status code for this request is 201
    Then the employee id "Employee.employee_id" is stored and values are validated

  @dynamic
  Scenario: Create employee using more dynamic json payload
    Given a request is prepared using data "Moqaddas" , "Rahim" , "JOJO" , "M" , "2000-11-06" , "employed" , "developer"
    When a POST call is made to create the employee
    Then the status code for this request is 201
    Then the employee id "Employee.employee_id" is stored and values are validated



