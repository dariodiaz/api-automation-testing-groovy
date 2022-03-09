package Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BraveNewCoinAPISteps {

    private static RequestSpecification request;
    private Response response;
    private ValidatableResponse json;

    @Given("^I have a valid API Key for the (.+) URI$")
    public void iSetTheRequestParams(String URI) {
        request = given()
                .header("X-RapidAPI-Key", "5e826ba7c3msh65fff8bcfff0b24p1da6a8jsn0b435566d9d2")
                .header("X-RapidAPI-Host", "bravenewcoin.p.rapidapi.com")
                .contentType(ContentType.JSON)
                .baseUri(URI)
                .log().all();
    }

    @When("^I send a POST request with a valid body to the (.+) endpoint$")
    public void sendPOSTRequest(String endpoint) {
        String requestBody = "{\n" +
                "  \"audience\": \"https://api.bravenewcoin.com\",\n" +
                "  \"client_id\":  \"oCdQoZoI96ERE9HY3sQ7JmbACfBf55RY\",\n" +
                "  \"grant_type\": \"client_credentials\"\n" + "}";
        response = request.when().body(requestBody).post(endpoint).prettyPeek();
    }

    @Then("^I can validate I receive a validate token in the response$")
    public void validateToken() {
        json = response.then().statusCode(200);
    }
}
