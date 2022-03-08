package Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

import java.util.Base64;

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

    /*
        1 - Obtener el codigo del servicio original para obtener el token.
        2 - Obtener el token, intercambiando el codigo que obtuvimos.
        3 - Acceder al recurso protegido, con nuestro token.
    */
    public static String clientId = "";
    public static String redirectUri = "";
    public static String scope = "";
    public static String userName = "";
    public static String password = "";
    private static String grantType = "";

    public static String encode(String str1, String str2){
        return new String(Base64.getEncoder().encode((str1 + ":" + str2).getBytes()));
    }

    public static Response getCode() {
        String authorization = encode(userName, password);
        return given()
                .header("authorization", "Basic ", authorization)
                .contentType(ContentType.URLENC)
                .formParam("response_type", "code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", scope)
                .post("/oauth/authorize")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    public static String parseForOAuthCode(Response response) {
        return response.jsonPath().getString("code");
    }

    public static Response getToken(String authCode) {
        String authorization = encode(userName, password);
        return given()
                .header("authorization", "Basic ", authorization)
                .contentType(ContentType.URLENC)
                .formParam("response_type", authCode)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("grant_type", grantType)
                .post("/oauth/token")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    public static String parseForToken(Response loginResponse) {
        return loginResponse.jsonPath().getString("access_token");
    }

    public static void getFinalService(String accessToken) {
        given().auth()
            // .header("Authorization", "Bearer "+ accessToken)
            .oauth2(accessToken)
            .when()
            .get("/service")
            .then()
            .statusCode(200);
    }
}
