Feature: Student Information

  Scenario: Verify user should able to access student application
    When    User sends a GET request to list endpoints
    Then    User must get response back with valid status code 200

  Scenario Outline: Create a new student & verify if the student is added
    When I create a new student by providing the information firstName "<firstName>" lastName "<lastName>" email "<email>" programme "<programme>" courses "<courses>"
    Then I verify that the student with "<email>" is created
    Examples:
      | firstName | lastName | email            | programme        | courses |
      | Harry     | Potter   | harry@gmail.com | Computer Science | JAVA    |


  Scenario Outline: Verify Student information can be updated
    When    I update student information firstName "<firstName>" lastName "<lastName>" email "<email>" programme "<programme>" courses "<courses>"
    Then    I verify that student information is updated with name "<firstName>" and "<courses>"
    Examples:
      | firstName | lastName | email            | programme        | courses  |
      | Henry     | Potter   | harry@gmail.com | Software Testing | Selenium |

  Scenario: Delete the single student data and verify its delete from database
    When I delete single student data
    Then I verify that same student data was deleted by getting data by studentId
