package Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;


public class APISteps {

    private static RequestSpecification request;
    private Response response;
    private ValidatableResponse json;

    @Given("^I send a GET request to the endpoint$")
    public void sendGETRequest() {
        request = given().relaxedHTTPSValidation().log().all()
            .param("", "");
    }

    @Then("^I get a list of (\\d+) users$")
    public void expectedStatusCode(int expectedStatusCode) {
        String endpoint = "";
        response = request.when()
            .get(endpoint);
            json = response.then().statusCode(expectedStatusCode);
    }

    @Then("^I get a list of (\\d+) users$")
    public void validateListOfUsers(int expectedUsers) {
        System.out.println("List of users received");
    }
}
