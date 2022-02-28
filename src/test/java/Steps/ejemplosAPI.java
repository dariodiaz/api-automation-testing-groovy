package Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

import org.apache.http.auth.UsernamePasswordCredentials;


public class ejemplosAPI {

    private static RequestSpecification request;
    private Response response;
    private ValidatableResponse json;

    public void GETRequest() {
        given()
            .baseUri("https://api.github.com")
            .when()
            .get("/users/dariodiaz/repos")
            .getBody().toString();
    }

    public void POSTRequest() {
        given()
            .baseUri("https://api.github.com")
            .when()
            .post("", "");
    }

    public void PUTRequest() {
        given()
            .baseUri("https://api.github.com")
            .when()
            .put("", "");
    }

    public void DELETERequest() {
        given()
            .baseUri("https://api.github.com")
            .when()
            .delete();
    }

    public void SOAPRequest() {
        String xmlEnvelope = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:tem='http://tempuri.org/'>" +
            "<soapenv:Header/>" +
            "<soapenv:Body>" +
                "<tem:GetConversionRate>" +
                "<tem:CurrencyFrom>USD</tem:CurrencyFrom>" +
                "<tem:CurrencyTo>INR</tem:CurrencyTo>" +
                "<tem:RateDate>2018-12-07</tem:RateDate>" +
                "</tem:GetConversionRate>" +
            "</soapenv:Body>" +
        "</soapenv:Envelope>";
        given()
            .header("SOAPAction", "Define")
            .baseUri("https://api.blogEjemplo.com")
            .when()
            .body(xmlEnvelope)
            .post("/endpoint");
    }

    public void basicAuth(String username, String password) {
        given()
        .auth().basic(username, password)
        .when()
        .get("AUTH_ENDPOINT")
        .then()
        .assertThat().statusCode(200);
    }

    public void formAuth(String username, String password) {
        given()
        .auth().form(username, password)
        .when()
        .get("SERVICE")
        .then()
        .assertThat().statusCode(200);
    }
}
