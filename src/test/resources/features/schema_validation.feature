@dummyjson @functional @schema-validation
Feature: DummyJSON Products API - JSON Schema Validation & Response Spec
  As a QA engineer
  I want to validate DummyJSON responses against JSON schemas and response specifications
  So that contract testing, response specs, and response time assertions are demonstrated

  @Scenario-1 @positive @smoke
  Scenario: GET single product response matches JSON schema
    When user fetches product with id 1
    Then the HTTP status code should be 200
    And the response should match the "dummyjson-product" JSON schema
    And the response time should be less than 3000 milliseconds

  @Scenario-2 @positive
  Scenario: GET all products list response matches JSON schema
    When user fetches all DummyJSON products
    Then the HTTP status code should be 200
    And the response should match the "dummyjson-products-list" JSON schema

  @Scenario-3 @positive @response-spec
  Scenario: Product list endpoint conforms to standard DummyJSON response spec
    When user fetches all DummyJSON products
    Then the response should conform to the standard DummyJSON response spec
    And the response time should be less than 3000 milliseconds
