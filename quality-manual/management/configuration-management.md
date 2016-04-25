# Configuration Management

## Communication & Version Control

The main communication channel will be Slack where discussions will take place, 
whereas for information sharing of technical documents Github will be used as it 
includes version control procedures making it easier to make changes. There is a strict 
requirement for each member to record documents they upload and edit, consisting of the 
document name, author, auditor, version number and last modification date. This requirement 
will be satisfied by using the Git version control system. When a document is uploaded or edited, 
it is automatically communicated across in Slack. Edited and uploaded documents shall be 
reviewed at the next meeting before being marked as completed by the team as further changes 
may be required. If changes are required this shall be communicated in Slack or in a team meeting 
and will be later implemented. Team communication relevant to the product-related aspects of the project (planning, development, testing) shall be recorded for future reference:
	1. Facebook chat - message archive
	2. Slack chat - message archive
	3. In-person - meeting minutes

## Development

The below is relevant configuration information which has been extracted from the non-functional requirements document.

All development and testing shall be undertaken in either the Netbeans IDE, Eclipse IDE, or IntelliJ IDE.
JUnit 4.12 shall be used as the testing library across all development and testing systems.
Gradle 2.5+ shall be used as the build automaton system across all development and testing systems.
All project-related documents, source code, tests and builds shall be kept on the team’s GitHub repository, with new versions being committed and pushed to the repository on a regular basis and as soon as a major change/refactoring occurs.
A copy of all project documents and source code shall be stored independently on each team member’s personal computer, regularly pulled from the central team GitHub repository. In addition, an up-to-date backup of the GitHub repository shall be kept in one or more of the team’s personal Dropbox repositories. The Jacoco code coverage analysis tool shall be used by the testing/validation team to ensure maximum code coverage. The Jacoco and JUnit tools are automatically set up using Gradle and the gradle build file, which is stored
on the central repository.

## Build Automaton

Gradle is a build automation system (see week 6 lectures). Gradle was chosen for this project primarily because it is relatively
simple. In addition, Eclipse IntelliJ and Netbeans support importing Gradle projects without too much hassle.

In the root code directory, there are two files 'gradlew' (for OS X and Linux) and 'gradlew.bat' (Windows).
These scripts can be run in the command line to execute Gradle 'tasks'.

There are built-in tasks including (from `./gradlew tasks`):

##### Build tasks
- assemble - Assembles the outputs of this project.
- build - Assembles and tests this project.
- buildDependents - Assembles and tests this project and all projects that depend on it.
- buildNeeded - Assembles and tests this project and all projects it depends on.
- classes - Assembles classes 'main'.
- clean - Deletes the build directory.
- jar - Assembles a jar archive containing the main classes.
- testClasses - Assembles classes 'test'.

##### Build Setup tasks
- init - Initializes a new Gradle build. [incubating]
- wrapper - Generates Gradle wrapper files. [incubating]

##### Documentation tasks
- javadoc - Generates Javadoc API documentation for the main source code.

##### Help tasks
- components - Displays the components produced by root project 'ant-game'. [incubating]
- dependencies - Displays all dependencies declared in root project 'ant-game'.
- dependencyInsight - Displays the insight into a specific dependency in root project 'ant-game'.
- help - Displays a help message.
- model - Displays the configuration model of root project 'ant-game'. [incubating]
- projects - Displays the sub-projects of root project 'ant-game'.
- properties - Displays the properties of root project 'ant-game'.
- tasks - Displays the tasks runnable from root project 'ant-game'.

##### Verification tasks
- check - Runs all checks.
- test - Runs the unit tests.

## IDE Setup

This document gives a quick overview of how to set up the code part of the project in each of the common Java IDEs. The setup hopefully be relatively simple, since all IDEs have automatic setup for the build tool used (Gradle).

### Netbeans

Netbeans will require an install of the Gradle plugin. Tools -> Plugins, search for 'Gradle Support', install plugin.
This allows Netbeans to open Gradle files as projects: File -> Open Project, browse to the code root folder (the one which contains build.gradle) and open it.

Then for example to run all the tests, right click on the project, then 'tasks -> test'.

### Eclipse

File -> Import -> Gradle Project, browse to the code root folder (the one which contains build.gradle) and open it.

Then down the bottom of the screen there is a Gradle Tasks panel, where you can run automatic tasks to build, test etc.

### IntelliJ

File -> New -> Project from Existing Sources, browse to the code root folder (the one which contains build.gradle) and open it.

Then a Gradle Tasks panel will become accessible, where there are automatic tasks available to build, test etc.
