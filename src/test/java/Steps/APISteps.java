package Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;


public class APISteps {

    private static RequestSpecification request;
    private Response response;
    private ValidatableResponse json;

    @Given("^I send a GET request to the (.+) URI$")
    public void sendGETRequest(String URI) {
        request = given()
                    .baseUri(URI)
                    .contentType(ContentType.JSON);
    }

    @Then("^I get a (\\d+) status code$")
    public void expectedStatusCode(int expectedStatusCode) {
        String endpoint = "/users/dariodiaz/repos";
        response = request.when()
            .get(endpoint);
            json = response.then().statusCode(expectedStatusCode);
    }

    @Then("^I validate there are (\\d+) items on the (.+) endpoint$")
    public void validateListOfUsers(int expectedUsers, String endpoint) {
        response = request
            .when()
            .get(endpoint);

            List<String> jsonResponse = response.jsonPath().getList("$");
            int actualUsers = jsonResponse.size();

            assertEquals(expectedUsers, actualUsers);
    }
}
