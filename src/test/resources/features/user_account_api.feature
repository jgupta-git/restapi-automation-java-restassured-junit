@account @functional
Feature: User Account API
  As a QA engineer
  I want to verify the CRUD operations for user accounts
  So that account creation, retrieval, update, and deletion work correctly

  @Scenario-1 @positive @smoke
  Scenario: POST to create a new user account
    Given any existing account with email "jg_qa_create@testmail.com" and password "Pass1234!" is cleaned up
    When I create a user account with the following details:
      | name           | TestUser                     |
      | email          | jg_qa_create@testmail.com    |
      | password       | Pass1234!                    |
      | title          | Mr                           |
      | birth_date     | 15                           |
      | birth_month    | 6                            |
      | birth_year     | 1990                         |
      | firstname      | Test                         |
      | lastname       | User                         |
      | company        | TestCorp                     |
      | address1       | 123 Test Street              |
      | address2       | Suite 100                    |
      | country        | United States                |
      | zipcode        | 10001                        |
      | state          | New York                     |
      | city           | New York                     |
      | mobile_number  | 5551234567                   |
    Then the response code should be 201
    And the response message should be "User created!"

  @Scenario-2 @positive
  Scenario: GET user account detail by email
    Given a user account has been created with:
      | name           | JaneDoe                      |
      | email          | jg_qa_getuser@testmail.com   |
      | password       | Secret456!                   |
      | title          | Mrs                          |
      | birth_date     | 22                           |
      | birth_month    | 3                            |
      | birth_year     | 1985                         |
      | firstname      | Jane                         |
      | lastname       | Doe                          |
      | company        | JaneCorp                     |
      | address1       | 456 Oak Avenue               |
      | address2       | Apt 2B                       |
      | country        | Canada                       |
      | zipcode        | M5V 2T6                      |
      | state          | Ontario                      |
      | city           | Toronto                      |
      | mobile_number  | 4165551234                   |
    When I get the user account by email "jg_qa_getuser@testmail.com"
    Then the response code should be 200
    And the user detail should match:
      | email     | jg_qa_getuser@testmail.com |
      | firstname | Jane                       |
      | lastname  | Doe                        |
      | company   | JaneCorp                   |
      | address1  | 456 Oak Avenue             |
      | country   | Canada                     |
      | state     | Ontario                    |
      | city      | Toronto                    |

  @Scenario-3 @positive
  Scenario: PUT to update an existing user account
    Given a user account has been created with:
      | name           | UpdateMe                       |
      | email          | jg_qa_update@testmail.com      |
      | password       | Update789!                     |
      | title          | Mr                             |
      | birth_date     | 10                             |
      | birth_month    | 11                             |
      | birth_year     | 1992                           |
      | firstname      | Before                         |
      | lastname       | Update                         |
      | company        | OldCorp                        |
      | address1       | 789 Pine Road                  |
      | address2       |                                |
      | country        | United States                  |
      | zipcode        | 90210                          |
      | state          | California                     |
      | city           | Los Angeles                    |
      | mobile_number  | 3105559876                     |
    When I update the user account "jg_qa_update@testmail.com" with password "Update789!" and:
      | name           | UpdateMe                       |
      | title          | Mr                             |
      | birth_date     | 10                             |
      | birth_month    | 11                             |
      | birth_year     | 1992                           |
      | firstname      | After                          |
      | lastname       | Changed                        |
      | company        | NewCorp                        |
      | address1       | 789 Pine Road                  |
      | address2       |                                |
      | country        | United States                  |
      | zipcode        | 90210                          |
      | state          | California                     |
      | city           | Los Angeles                    |
      | mobile_number  | 3105559876                     |
    Then the response code should be 200
    And the response message should be "User updated!"
    When I get the user account by email "jg_qa_update@testmail.com"
    Then the user detail should match:
      | firstname | After   |
      | lastname  | Changed |
      | company   | NewCorp |

  @Scenario-4 @positive
  Scenario: DELETE to remove a user account
    Given a user account has been created with:
      | name           | DeleteMe                       |
      | email          | jg_qa_delete@testmail.com      |
      | password       | Delete000!                     |
      | title          | Miss                           |
      | birth_date     | 1                              |
      | birth_month    | 1                              |
      | birth_year     | 2000                           |
      | firstname      | Delete                         |
      | lastname       | Me                             |
      | company        | GoneCorp                       |
      | address1       | 999 Last Lane                  |
      | address2       |                                |
      | country        | India                          |
      | zipcode        | 110001                         |
      | state          | Delhi                          |
      | city           | New Delhi                      |
      | mobile_number  | 9876543210                     |
    When I delete the user account with email "jg_qa_delete@testmail.com" and password "Delete000!"
    Then the response code should be 200
    And the response message should be "Account deleted!"

  @Scenario-5 @negative
  Scenario: GET user account after deletion returns not found
    Given a user account has been created with:
      | name           | GhostUser                      |
      | email          | jg_qa_ghost@testmail.com       |
      | password       | Ghost999!                      |
      | title          | Mr                             |
      | birth_date     | 5                              |
      | birth_month    | 5                              |
      | birth_year     | 1995                           |
      | firstname      | Ghost                          |
      | lastname       | User                           |
      | company        | GhostCorp                      |
      | address1       | 000 Nowhere Blvd               |
      | address2       |                                |
      | country        | United States                  |
      | zipcode        | 00000                          |
      | state          | Texas                          |
      | city           | Houston                        |
      | mobile_number  | 7135550000                     |
    And the user account with email "jg_qa_ghost@testmail.com" and password "Ghost999!" has been deleted
    When I get the user account by email "jg_qa_ghost@testmail.com"
    Then the response code should be 404
