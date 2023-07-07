Feature: Login related scenarios

  Background:
    # Given user is navigated to HRMS application

  @smoke @login @sprint1 @test
  Scenario: Valid admin login
    # Don't add anything in the step after creating step definition
    # Given user is navigated to HRMS application
    When user enters valid admin username and password
    And user clicks on login button
    Then user is successfully logged in the application
    # Step declaration and then when you run feature, that is called step definition

  @employee @sprint1 @test
  Scenario: Valid ESS login
    # Given user is navigated to HRMS application
    When user enters valid ESS username and password
    And user clicks on login button
    Then user is successfully logged in the application

  @invalid @sprint1 @test
  Scenario: Invalid admin login
    # We added the Given steps to background
    When user enters invalid admin username and password
    And user clicks on login button
    Then error message is displayed

  @negative
  Scenario Outline: Negative login test
    When user enters "<username>" and "<password>" and verifying the "<error>" for the combinations
    Examples:
      | username | password | error |
      |admin     |fkfkkj    |Invalid credentials|
      |admin1    |Hum@nhrm123|Invalid credentials|
      |          |Hum@nhrm123|Username cannot be empty|
      |admin          |      |Password cannot be empty|

