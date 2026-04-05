Feature: Pause and Resume Lights
  Scenario: Resume a paused light
    Given Light 1 is paused
    When The user clicks Resume
    Then Light 1 should not be paused

  Scenario: Pause a working light
    Given Light 1 is not paused
    When The user clicks Pause
    Then Light 1 should be paused