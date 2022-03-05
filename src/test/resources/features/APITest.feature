Feature: Ejemplo de Request
    @API
    Scenario: Prueba GET al endpoint.
    Given I send a GET request to the endpoint
    Then I get a 200 status code