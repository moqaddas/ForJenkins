package APISteps;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import utils.APIConstants;
import utils.APIPayloadConstants;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class APIStepsClass {

    // Why: Set the base URI for all RestAssured requests.
    // Where: It’s applied globally within this class.
    // When: This URI is used whenever making API calls.
    // How: Assigning the base URL to RestAssured's baseURI field.
    // What: The base URL for the API endpoint.
    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";

    // Why: Declare variables for making and handling requests and responses.
    // Where: These variables are used throughout this class.
    // When: Used in different methods when preparing and sending requests.
    // How: By initializing and assigning RequestSpecification and Response objects.
    // What: Variables to hold request specifications and responses.
    RequestSpecification request;
    Response response;

    // Why: To store employee ID and token across multiple method calls.
    // Where: These variables are accessible globally within this class.
    // When: Used whenever referencing the employee ID and token.
    // How: Declaring them as static so they persist and are accessible across instances.
    // What: Static variables to hold employee ID and authentication token.
    public static String employee_id;
    public static String token;

    @Given("a JWT bearer token is generated")
    public void a_jwt_bearer_token_is_generated() {
        // Prepare the request with necessary headers and body to generate JWT token
        // Why: Prepare a request to generate a JWT token.
        // Where: Request is configured within this method.
        // When: When the method is called as part of the test.
        // How: Setting the request headers and body.
        // What: Request to generate a JWT token using email and password.
        request = given()
                .header(APIConstants.HEADER_CONTENT_TYPE_KEY, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .body("{\n" +
                        "    \"email\": \"Moqaddas76@gmail.com\",\n" +
                        "    \"password\": \"984mnf\"\n" +
                        "}");

        // Why: Send the prepared request to get the token.
        // Where: This POST request is sent to the generated token endpoint.
        // When: Immediately after the request is prepared.
        // How: Using RestAssured's when().post() method.
        // What: The response containing the JWT token.
        response = request.when().post(APIConstants.GENERATE_TOKEN);

        // Why: Extract and store the token for future use.
        // Where: The token is extracted from the response JSON.
        // When: Right after receiving the response.
        // How: Using JSON Path to get the token from the response.
        // What: The authentication token prefixed with "Bearer".
        token = "Bearer " + response.jsonPath().getString("token");

        // Why: Print the token for debugging purposes.
        // Where: In the console/output.
        // When: After the token is extracted.
        // How: Using System.out.println() to print.
        // What: The token value.
        System.out.println(token);
    }

    @Given("a request is prepared for creating the employee using json payload")
    public void a_request_is_prepared_for_creating_the_employee_using_json_payload() {
        // Why: Prepare a request to create an employee.
        // Where: Configure the request within this method.
        // When: When this step is executed as part of the test.
        // How: Setting headers and the JSON payload for the request.
        // What: Request containing the employee creation data.

        request = given()
                .header(APIConstants.HEADER_CONTENT_TYPE_KEY, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .header(APIConstants.HEADER_AUTHORIZATION_KEY, token)
                .body(APIPayloadConstants.createEmployeeJsonPayload());
    }

    @Given("a request is prepared for creating the employee")
    public void a_request_is_prepared_for_creating_the_employee() {

        // Why: Prepare a request with a different method to create an employee.
        // Where: Configure the request within this method.
        // When: When this step is executed as part of the test.
        // How: Setting headers and another type of payload.
        // What: Request containing the employee creation data.
        request = given()
                .header(APIConstants.HEADER_CONTENT_TYPE_KEY, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .header(APIConstants.HEADER_AUTHORIZATION_KEY, token)
                .body(APIPayloadConstants.createEmployeePayload());
    }

    @When("a POST call is made to create the employee")
    public void a_post_call_is_made_to_create_the_employee() {

        // Why: Send the prepared request to create the employee.
        // Where: The API endpoint for creating employees.
        // When: After the request is prepared.
        // How: Using RestAssured's when().post() method.
        // What: The response from the POST request.
        response = request.when().post(APIConstants.CREATE_EMPLOYEE);
    }

    @Then("the status code for this request is {int}")
    public void the_status_code_for_this_request_is(Integer int1) {

        // Why: Validate the status code of the response.
        // Where: In the test assertion.
        // When: After receiving the response.
        // How: Using RestAssured's then().assertThat().statusCode() method.
        // What: The expected status code to compare with the actual status code.
        response.then().assertThat().statusCode(int1);
    }

    @Then("the employee id {string} is stored and values are validated")
    public void the_employee_id_is_stored_and_values_are_validated(String employee_id) {

        // Why: Extract the employee ID from the response.
        // Where: From the response JSON.
        // When: After the employee is created.
        // How: Using JSON Path to get the employee ID.
        // What: The employee ID.
        APIStepsClass.employee_id = response.jsonPath().getString(employee_id);

        // Print the employee ID for debugging purposes
        System.out.println("The employee id is:::::: " + APIStepsClass.employee_id);

        // Validate the employee's first name in the response matches the expected value
        response.then().assertThat().body("Employee.emp_firstname", equalTo("Moqaddas"));
    }

    @Given("a request is prepared to get the created employee")
    public void a_request_is_prepared_to_get_the_created_employee() {

        // Why: Validate that the employee's first name matches the expected value.
        // Where: In the response body.
        // When: After creating the employee.
        // How: Using RestAssured's body() method with a matcher.
        // What: Expected value of the employee's first name.
        request = given()
                .header(APIConstants.HEADER_CONTENT_TYPE_KEY, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .header(APIConstants.HEADER_AUTHORIZATION_KEY, token)
                .queryParam("employee_id", employee_id);
    }

    @When("a GET call is made to get the created employee")
    public void a_get_call_is_made_to_get_the_created_employee() {

        // Why: Prepare a request to get the created employee's details.
        // Where: Configure the request within this method.
        // When: When this step is executed as part of the test.
        // How: Setting headers and the employee ID as a query parameter.
        // What: Request to retrieve the employee's details.
        response = request.when().get(APIConstants.GET_ONE_EMPLOYEE);
    }

    @Then("the status for get call is {int}")
    public void the_status_for_get_call_is(Integer statusCode) {
        // Assert that the response status code for the GET call matches the expected status code
        response.then().assertThat().statusCode(statusCode);
    }

    @Then("the employee has same employee id {string} as global employee id")
    public void the_employee_has_same_employee_id_as_global_employee_id(String employee_id) {
        // Extract the employee ID from GET response
        String temporaryEmpId = response.jsonPath().getString(employee_id);

        // Compare the extracted employee ID with the stored employee ID
        Assert.assertEquals(APIStepsClass.employee_id, temporaryEmpId);
    }

    @Then("the data coming from the get call should be equal to the data used in POST call")
    public void the_data_coming_from_the_get_call_should_be_equal_to_the_data_used_in_post_call(io.cucumber.datatable.DataTable dataTable) {

        // Why: Convert the data table from the feature file to a list of maps.
        // Where: Extracted from the Cucumber feature file passed to this method.
        // When: When this step is executed as part of the test.
        // How: Using asMaps() to convert the data table.
        // What: The expected data in a list of maps.
        List<Map<String, String>> expectedData = dataTable.asMaps();

        // Why: Extract the employee data from the response.
        // Where: From the JSON body of the GET response.
        // When: After the GET request retrieves the employee data.
        // How: Using JSON Path to get the "employee" object from the response.
        // What: The actual data in a map.
        Map<String, String> actualData = response.jsonPath().get("employee");

        // Why: Compare each key-value pair from the expected data with the actual data.
        // Where: Iterate through each map in the list of expected data.
        // When: After converting the data table and extracting the actual data.
        // How: Using nested loops to compare each value.
        // What: Ensure all values match between expected and actual data.
        // Iterate over each employee's data in the expected data set
        for (Map<String, String> employeeData : expectedData) {
            // Get the set of keys for the current employee's data
            Set<String> keys = employeeData.keySet();
            // Iterate over each key in the employee's data
            for (String key : keys) {
                // Get the expected value for the current key
                String expectedValue = employeeData.get(key);
                // Get the actual value for the current key from the actual data
                String actualValue = actualData.get(key);
                // Assert that the expected value is equal to the actual value
                assert expectedValue.equals(actualValue);
            }
        }
    }
    @Given("a request is prepared using data {string} , {string} , {string} , {string} , {string} , {string} , {string}")
    public void a_request_is_prepared_using_data(String firstname, String lastname, String middlename, String gender, String birthday, String status, String jobtitle) {

        // Why: Prepare a request with dynamically generated JSON payload for creating an employee.
        // Where: Configure the request within this method.
        // When: When this step is executed as part of the test.
        // How: Setting headers and using a method to create the JSON payload dynamically.
        // What: Request containing the employee creation data with dynamic values.
        request = given()
                .header(APIConstants.HEADER_CONTENT_TYPE_KEY, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .header(APIConstants.HEADER_AUTHORIZATION_KEY, token)
                .body(APIPayloadConstants.createEmployeeJsonPayloadDynamic(firstname, lastname, middlename, gender, birthday, status, jobtitle));
    }
}
