Feature: Mastermind

  Scenario Outline: combination mismatch
    Given a combination set to 'RJOO'
    When user type '<COMBINATION>'
    Then number of correct pieces: <CORRECT_NUM>
    But number of misplaced pieces: <MISPLACED_NUM>

    Examples:
      | COMBINATION | CORRECT_NUM | MISPLACED_NUM |
      | BBBB        | 0           | 0             |
      | OORJ        | 0           | 4             |
      | RRRR        | 1           | 0             |
      | OOOO        | 2           | 0             |
      | RJOO        | 4           | 0             |