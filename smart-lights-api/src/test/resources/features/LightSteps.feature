Feature: Test The Turn on/off button login
  Scenario: Turn off an active light
    Given The user opens the Devices page
    Then Light 1 exists with status ON
    When User toggles the light
    Then The light state should become OFF


  Scenario: Turn off an active light
    Given Light 1 exists with status OFF
    When User toggles the light
    Then The light state should become ON



Feature: Devices page
  Scenario: User opens the Devices page nad sees the lights
    Given The user opens the Devices page
    Then The user should be in the Devices page
    And The user should see the lights list