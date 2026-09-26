@dummyjson @functional @hamcrest
Feature: DummyJSON Products API - Hamcrest Matchers & Chained Assertions
  As a QA engineer
  I want to validate DummyJSON product responses using Hamcrest matchers
  So that fluent chained body assertions and collection matchers are demonstrated

  @Scenario-1 @positive @smoke
  Scenario: GET single product and validate with chained Hamcrest assertions
    When user fetches product with id 1
    Then the HTTP status code should be 200
    And the response should match the following assertions:
      | field       | matcher     | value |
      | id          | notNull     |       |
      | title       | notEmpty    |       |
      | description | notNull     |       |
      | price       | greaterThan | 0     |
      | rating      | between     | 0,5   |
      | stock       | notNull     |       |
      | brand       | notNull     |       |
      | thumbnail   | notNull     |       |
      | tags        | notEmptyList|       |

  @Scenario-2 @positive
  Scenario: GET all products and validate collection with Hamcrest matchers
    When user fetches all DummyJSON products
    Then the HTTP status code should be 200
    And the response should match the following assertions:
      | field    | matcher        | value |
      | total    | greaterThan    | 0     |
      | products | notEmptyList   |       |
      | products | everyItemNotNull | id    |
      | products | everyItemNotNull | title |
      | products | everyItemNotNull | price |
