package Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Then("^I validate there is a value: (.+) in the response at (.+) endpoint$")
    public void validateValue(String expectedValue, String endpoint) {
        response = request
            .when()
            .get(endpoint);

            List<String> jsonResponse = response.jsonPath().getList("username");
            //Here we know where is the value we are looking for
            // String actualValue = jsonResponse.get(0);
            // assertEquals(expectedValue, actualValue);
            assertTrue(jsonResponse.contains(expectedValue), "El valor "+ expectedValue +" no esta en la lista");
    }

    @Then("^I can validate the nested value: (.+) in the response at (.+) endpoint$")
    public void validateNestedValue(String expectedValue, String endpoint) {
        response = request
            .when()
            .get(endpoint);

            String jsonResponse = response.jsonPath().getString("address.street");
            //Here we know where is the value we are looking for
            // String actualValue = jsonResponse.get(0);
            // assertEquals(expectedValue, actualValue);
            assertTrue(jsonResponse.contains(expectedValue), "La calle "+ expectedValue +" no esta dentro de la lista como valor anidado");
    }
}
