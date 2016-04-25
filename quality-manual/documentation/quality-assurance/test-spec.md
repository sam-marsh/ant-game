# Test Specification

## Scope
The tests will cover all possible control flow in the program. This will be examined by [JaCoCo](http://eclemma.org/jacoco/), a code coverage library for Java. JaCoCo examines the bytecode of the program, and produces percentages of the statements covered by existing tests for each method, each class, and overall.

Tests shall aim to keep code coverage above 80% at all times. As discussed in the project plan, this will be managed by splitting the team into two groups for the development stage - one group to write code, and the other to write tests for it.

In addition, code coverage should aim to be 'above' 100% in areas: although not required, the members of the testing group will aim to assist the developers using test-driven development - i.e. by writing tests for a particular program submodule before the coders have built it.

Tests shall be primarily aimed at targeting the explicit functional requirements of the project. As such, each functional requirement shall generate multiple tests, aiming to strictly enforce that functional requirement.

Tests shall also enforce program behaviours described by the UML diagrams and sequence diagrams of the modelling phase.

The continuous integration software used in the remote Git repository, Travis CI, runs code coverage and automatics tests upon pushes to the repository (regression testing). The status of these operations, displayed in `README.md` in the root project directory, shall be monitored to ensure test coverage is up-to-date and that code is  in line with the requirements.

When a test fails, the programmers shall be alerted to the location of the error, pertinent details, and the related functional requirement. This will allow the developers to adjust the program to suit the user's requirements. Where the source of an error cannot be found, breakpoints shall be used for debugging.

Tests shall have extensive documentation. Unit test classes shall contain class-level documentation including a link to the class being tested. For example: 

`This class is testing: {@link antgame.core.Ant}`

In addition, each test method shall contain links to each method that is being tested. It shall also describe the functional requirement that the test is written to validate. For example:

`This method tests that an ant is not able to move onto an already-occupied cell: {@link antgame.core.Ant#move(int)}`

## Test Plan

### Test Phases

##### Phase 1 - Unit Testing

This phase is to be worked on concurrently while the programming sub-team produces code. As described above, code coverage should be maintained at above 80% at all times.

JUnit 4.2 shall be used as the testing suite on all the tester's platforms.

##### Phase 2 - Release Testing

This phase takes place after development is complete. As per the project plan, this should take approximately one week to complete thoroughly.

Release testing shall be undertaken by the entire team.

It shall involve running the entire system, interacting through the GUI, and examining the system's response to both typical and edge cases (e.g. attempting to load an empty ant brain, or adding no teams to a tournament).

## Overhead Software/Libraries

- JUnit 4.2 - external library, managed with Gradle
- JaCoCo - external library, managed with Gradle



## Test Procedures



## Test Results
