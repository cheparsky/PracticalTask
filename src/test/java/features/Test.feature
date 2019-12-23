Feature: Test feature

  Scenario: Verify that the most voted Language Dependence level is presented on the game's page.
    Given that user is located on Main Page
    When user click on the first user in Forums section
    And click on owned Board Games
    And click on the first game in User Collection
    Then we get value of most voted Language Dependence Level using API
    And check if the value of most voted Language Dependence Level is as expected