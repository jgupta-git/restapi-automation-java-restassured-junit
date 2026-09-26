@dummyjson @functional @sorting
Feature: DummyJSON Products API - Sorting
  As a QA engineer
  I want to verify sorting functionality for DummyJSON Products API
  So that products can be sorted by different fields in ascending and descending order

  @positive @smoke
  Scenario Outline: GET products sorted by <field> in <order> order
    When user fetches products sorted by "<field>" in "<order>" order
    Then the HTTP status code should be 200
    And the response should match the following assertions:
      | field    | matcher      | value |
      | products | notEmptyList |       |
    And the products should be sorted by "<field>" in "<order>" order
    And the response time should be less than 3000 milliseconds

    Examples:
      | field  | order |
      | price  | asc   |
      | price  | desc  |
      | rating | desc  |
