# Gradle

Gradle is a build automation system (see week 6 lectures). Gradle was chosen for this project mainly because it looks easy - and Eclipse, IntelliJ & Netbeans support importing Gradle projects without too much hassle (see [ide-setup.md](ide-setup.md)).

In the [root code directory](ant-game), there are two files 'gradlew' (for OS X and Linux) and 'gradlew.bat' (Windows). You can run these scripts in the command line to execute Gradle 'tasks'.

There are built-in tasks including (copy-and-pasted output from `./gradlew tasks`):

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

