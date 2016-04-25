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
and will be later implemented.

## Build Automaton

Gradle is a build automation system (see week 6 lectures). Gradle was chosen for this project primarily because it is relatively
simple. In addition, Eclipse IntelliJ and Netbeans support importing Gradle projects without too much hassle.

In the root code directory, there are two files 'gradlew' (for OS X and Linux) and 'gradlew.bat' (Windows).
You can run these scripts in the command line to execute Gradle 'tasks'.

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

This document gives a quick overview of how to set up the code part of the project in your IDE. Should hopefully be relatively simple, since all IDEs have automatic setup for the build tool we are using (Gradle).
First you'll need to pull the project from GitHub.

### Netbeans

Netbeans will require you to install the Gradle plugin. Tools -> Plugins, search for 'Gradle Support', install plugin.
This allows Netbeans to open Gradle files as projects: File -> Open Project, browse to the code root folder (the one which contains build.gradle) and open it.

Then for example to run all the tests, you right click on the project, then 'tasks -> test'.

### Eclipse

File -> Import -> Gradle Project, browse to the code root folder (the one which contains build.gradle) and open it.

Then down the bottom of the screen there is a Gradle Tasks panel, where you can run automatic tasks to build, test etc.

### IntelliJ

File -> New -> Project from Existing Sources, browse to the code root folder (the one which contains build.gradle) and open it.

Then you can add a Gradle Tasks panel, where you can run automatic tasks to build, test etc.
