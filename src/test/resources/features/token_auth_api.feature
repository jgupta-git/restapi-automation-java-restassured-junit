@token @functional
Feature: Token-based Authentication API (DummyJSON)
  As a QA engineer
  I want to verify JWT token authentication flows
  So that login, token-protected access, and token refresh work correctly

  @Scenario-1 @positive @smoke
  Scenario: Login with valid credentials returns access and refresh tokens
    When user logs in to DummyJSON with username "emilys" and password "emilyspass"
    Then the HTTP status code should be 200
    And the response should contain a non-empty "accessToken" field
    And the response should contain a non-empty "refreshToken" field
    And the response should contain user details:
      | username  | emilys                          |
      | firstName | Emily                           |
      | lastName  | Johnson                         |

  @Scenario-2 @positive
  Scenario: Access protected endpoint with valid Bearer token
    Given user has logged in to DummyJSON with username "emilys" and password "emilyspass"
    When user requests the authenticated user profile
    Then the HTTP status code should be 200
    And the response should contain user details:
      | username  | emilys                          |
      | firstName | Emily                           |
      | lastName  | Johnson                         |
      | email     | emily.johnson@x.dummyjson.com   |

  @Scenario-3 @positive
  Scenario: Refresh token returns a new access token
    Given user has logged in to DummyJSON with username "emilys" and password "emilyspass"
    When user refreshes the auth session
    Then the HTTP status code should be 200
    And the response should contain a non-empty "accessToken" field
    And the response should contain a non-empty "refreshToken" field

  @Scenario-4 @negative
  Scenario: Login with invalid credentials returns error
    When user logs in to DummyJSON with username "emilys" and password "wrongpassword"
    Then the HTTP status code should be 400
    And the response should contain field "message" with value "Invalid credentials"

  @Scenario-5 @negative
  Scenario: Access protected endpoint without token returns 401
    When user requests the authenticated user profile without a token
    Then the HTTP status code should be 401
    And the response should contain field "message" with value "Access Token is required"

  @Scenario-6 @negative
  Scenario: Access protected endpoint with invalid token returns 500
    When user requests the authenticated user profile with token "invalid.jwt.token"
    Then the HTTP status code should be 500
