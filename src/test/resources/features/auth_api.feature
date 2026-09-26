@auth @functional
Feature: Authentication API
  As a QA engineer
  I want to verify the Verify Login API endpoints
  So that authentication behaves correctly for valid, invalid, and missing credentials

  Background:
    Given a test user account exists with email "jg_qa_auth@testmail.com" and password "AuthPass123!"

  @Scenario-1 @positive @smoke
  Scenario: Verify login with valid credentials returns User exists
    When I verify login with email "jg_qa_auth@testmail.com" and password "AuthPass123!"
    Then the response code should be 200
    And the response message should be "User exists!"

  @Scenario-2 @negative
  Scenario: Verify login without email parameter returns 400
    When I verify login without the email parameter using password "AuthPass123!"
    Then the response code should be 400
    And the response message should be "Bad request, email or password parameter is missing in POST request."

  @Scenario-3 @negative
  Scenario: DELETE to verify login endpoint is not supported
    When I send a DELETE request to the verify login endpoint
    Then the response code should be 405
    And the response message should be "This request method is not supported."

  @Scenario-4 @negative
  Scenario: Verify login with invalid credentials returns User not found
    When I verify login with email "nonexistent@fake.com" and password "wrongpass"
    Then the response code should be 404
    And the response message should be "User not found!"
