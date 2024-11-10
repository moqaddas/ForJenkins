package APISteps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class APIStepsClass {

    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";
    RequestSpecification request;
    Response response;
    public static String employee_id;
    public static String token;

    @Given("a JWT bearer token is generated")
    public void a_jwt_bearer_token_is_generated() {
        request = given().
                header("Content-Type","application/json").
                body("{\n" +
                        "  \"email\": \"kaylinadmin321@kaylinworld.com\",\n" +
                        "  \"password\": \"Test@123\"\n" +
                        "}");

        response = request.when().post("/generateToken.php");

        token = "Bearer "+ response.jsonPath().getString("token");
        System.out.println(token);
    }

    @Given("a request is prepared for creating the employee")
    public void a_request_is_prepared_for_creating_the_employee() {
         request = given().
                header("Content-Type","application/json").
                header("Authorization",token).
                body("{\n" +
                        "  \"emp_firstname\": \"asana\",\n" +
                        "  \"emp_lastname\": \"lawrance\",\n" +
                        "  \"emp_middle_name\": \"ms\",\n" +
                        "  \"emp_gender\": \"F\",\n" +
                        "  \"emp_birthday\": \"1993-01-12\",\n" +
                        "  \"emp_status\": \"permanent\",\n" +
                        "  \"emp_job_title\": \"QA Manager\"\n" +
                        "}");
    }

    @When("a POST call is made to create the employee")
    public void a_post_call_is_made_to_create_the_employee() {
         response = request.when().post("/createEmployee.php");
    }

    @Then("the status code for this request is {int}")
    public void the_status_code_for_this_request_is(Integer int1) {
        response.then().assertThat().statusCode(int1);
    }

    @Then("the employee id {string} is stored and values are validated")
    public void the_employee_id_is_stored_and_values_are_validated(String empPath) {
        //hamcrest matchers

        //retrieve the employee id after creating the employee and store it in global variable

        //jsonPath() is the method which returns the value in string against the key specified
        employee_id = response.jsonPath().getString(empPath);
        System.out.println("The employee id is:::::: " + employee_id);
        response.then().assertThat().
                body("Employee.emp_firstname",equalTo("asana"));
    }
}
