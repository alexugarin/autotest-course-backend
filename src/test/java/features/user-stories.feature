@all
Feature: Calculation sum

    @Correct
    Scenario: Correct sum in 2 system
    When Send a GET request with parameters
    | Parameter name | Parameter Value |
    | a              | 10              |
    | b              | 11              |
    | numberSystem   | 2               |
    | operation      | +               |
    Then a new entry is saved in database and result = 101

    @Correct
    Scenario: Correct sums in 10 system
    When send a GET request with a=<a>, b=<b>, numberSystem=<numberSystem>, operation=<operation>
    Then get <result>
    Examples:
    |  a    |  b    |  numberSystem  |  operation |  result  |
    |  "2"  |  "3"  |  "10"          |  "+"       |  "5"     |
    |  "-2" |  "-3" |  "10"          |  "+"       |  "-5"    |
    |  "-2" |  "3"  |  "10"          |  "+"       |  "1"     |
    |  "2"  |  "-3" |  "10"          |  "+"       |  "-1"    |

    @Correct
    Scenario: Correct sum in 8 system
    When send a GET request for system with parameters 5 and 4 and 8 and +
    Then get result = 11

    @Correct
    Scenario: Find entries by date
    When send a find request with parameter startDate=2023-12-19, endDate=2023-12-21, numberSystem="10", operation="+"
    Then return a result numberA = 5; systemA = 10; numberB = 2; systemB = 10; operation = +
