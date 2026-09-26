@product @functional
Feature: Product API
  As a QA engineer
  I want to verify the Products API endpoints
  So that product listing and search behave correctly

  @Scenario-1 @positive @smoke
  Scenario: GET all products returns a non-empty product list
    When I send a GET request to the products list endpoint
    Then the response code should be 200
    And the response should contain a non-empty "products" list
    And each product should have id, name, price, and category fields

  @Scenario-2 @negative
  Scenario: POST to the products list endpoint is not supported
    When I send a POST request to the products list endpoint
    Then the response code should be 405
    And the response message should be "This request method is not supported."

  @Scenario-3 @positive
  Scenario: Search for products by keyword returns matching results
    When I search for products with keyword "top"
    Then the response code should be 200
    And the response should contain a non-empty "products" list

  @Scenario-4 @positive
  Scenario: Search for products with a specific keyword
    When I search for products with keyword "tshirt"
    Then the response code should be 200
    And the response should contain a "products" list

  @Scenario-5 @negative
  Scenario: Search without the search_product parameter returns 400
    When I search for products without the search parameter
    Then the response code should be 400
    And the response message should be "Bad request, search_product parameter is missing in POST request."
