@dummyjson @functional @query-params
Feature: DummyJSON Products API - Query Parameters
  As a QA engineer
  I want to verify query parameter handling for DummyJSON Products API
  So that pagination, field selection, category filtering, and search work correctly

  @Scenario-1 @positive @smoke
  Scenario: GET products with limit and skip for pagination
    When user fetches products with limit 5 and skip 10
    Then the HTTP status code should be 200
    And the response should match the following assertions:
      | field          | matcher     | value |
      | products       | listSize    | 5     |
      | skip           | equalToInt  | 10    |
      | limit          | equalToInt  | 5     |
    And the response time should be less than 3000 milliseconds

  @Scenario-2 @positive
  Scenario: GET products with select to return specific fields only
    When user fetches products selecting fields "title,price"
    Then the HTTP status code should be 200
    And the response should match the following assertions:
      | field              | matcher          | value       |
      | products           | everyItemNotNull | title       |
      | products           | everyItemNotNull | price       |
      | products           | everyItemNull    | description |

  @Scenario-3 @positive
  Scenario: GET products by category
    When user fetches products in category "smartphones"
    Then the HTTP status code should be 200
    And the response should match the following assertions:
      | field              | matcher          | value       |
      | products           | notEmptyList     |             |
      | products.category  | everyItemEquals  | smartphones |

  @Scenario-4 @positive
  Scenario: Search products with query parameter
    When user searches DummyJSON products with query "phone"
    Then the HTTP status code should be 200
    And the response should match the following assertions:
      | field    | matcher      | value |
      | products | notEmptyList |       |
      | total    | greaterThan  | 0     |

  @Scenario-5 @negative
  Scenario: GET products with skip beyond total returns empty list
    When user fetches products with limit 5 and skip 9999
    Then the HTTP status code should be 200
    And the response should match the following assertions:
      | field    | matcher  | value |
      | products | listSize | 0     |

  @Scenario-6 @positive
  Scenario Outline: GET products with different pagination parameters
    When user fetches products with limit <limit> and skip <skip>
    Then the HTTP status code should be 200
    And the response should match the following assertions:
      | field    | matcher    | value  |
      | products | listSize   | <limit> |
      | skip     | equalToInt | <skip> |
      | limit    | equalToInt | <limit> |

    Examples:
      | limit | skip |
      | 3     | 0    |
      | 5     | 10   |
      | 10    | 20   |
      | 1     | 50   |
