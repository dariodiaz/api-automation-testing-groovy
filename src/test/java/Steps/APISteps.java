package Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class APISteps {

    @Given("^I send a GET request to the endpoint$")
    public void sendGETRequest() {
        System.out.println("GET request sent");
    }

    @Then("^I get a list of (\\d+) users$")
    public void validateListOfUsers(int expectedUsers) {
        System.out.println("List of users received");
    }
}
