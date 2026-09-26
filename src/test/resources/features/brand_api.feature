@brand @functional
Feature: Brand API
  As a QA engineer
  I want to verify the Brands API endpoints
  So that brand listing is accurate and unsupported methods are rejected

  @Scenario-1 @positive @smoke
  Scenario: GET all brands returns a non-empty brand list
    When user sends a GET request to the brands list endpoint
    Then the response code should be 200
    And the response should contain a non-empty "brands" list
    And each brand should have id and brand fields

  @Scenario-2 @negative
  Scenario: PUT to the brands list endpoint is not supported
    When user sends a PUT request to the brands list endpoint
    Then the response code should be 405
    And the response message should be "This request method is not supported."
