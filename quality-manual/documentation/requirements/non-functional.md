# Requirements

## Non-functional

### Product

#### Reliability

1. The software should crash no more than 1 time in 1000 when parsing user files, even if the files are corrupt or not well-formed.
2. The software should crash no more than 1 time in 1000 of the time when simulating a game or tournament.
3. The GUI should be stable and responsive to user input at least 99% of the time, including when a game is running. This applies only to systems with more than 400MB of free RAM.
4. The software shall parse user files of up to 10,000 lines each without crashing more than 1 time in 1000.
5. Visualisations of games and tournaments should proceed with no visible defects (glitches) at least 99 times out of 100. 
6. If the program crashes, a log containing the stack trace of the error and any other pertinent details shall be
written to the program's working directory, titled "ant-game-crash-[current unix time].log".

#### Usability

1. The software should have a minimal learning curve for new users. That is, the GUI should have intuitive and clear controls that step the user(s) through each stage of the game without being required to follow external instructions (not including understanding the rules of the game and the development of a user’s ant-brain file).
2. All user interfaces shall be developed using the JavaFX API.
3. The software shall present a help menu, accessible at all times in the program, which upon clicking displays a popup user interface containing the searchable user documentation for the program.

#### Performance

1. Response time of the system when interacting with any component of the GUI should be negligible before the user is presented with the interaction’s result or with a loading screen.
2. The GUI should not hang (that is, be unresponsive to user interaction) for more than one second after any user interaction.
3. Parsing user files (ant-brain, ant-world) should take no longer than 5 seconds.
4. The two-player game simulation should take no longer than 30 seconds for 300,000 rounds, not including artificially introduced  delays when displaying the game to the user.

#### Security

1. The software shall not require internet or network access to run, and will have access to the full feature-set of the program offline without use of any network connections.
2. The system shall not purposefully access or alter any existing user or system files, with the exception of ant-brain and ant-world files chosen with explicit permission from the user (via selection from a file-chooser interface). 

#### Portability

1. The software shall be cross-platform across commonly used personal desktop operating systems (Windows 7+, Mac OS X 10.8.3+, Linux) that support Java 8.0.

#### Resources

1. The system should not store more than 100MB of non-volatile data, not including the user's ant-brain and ant-world files.
2. The software shall use no more than 100MB of RAM, with the exception of a maximum of 400MB of RAM while executing/displaying a game.

#### Deployment

1. A build of the program shall be delivered in executable JAR format, to allow the user to run the program without accessing the command line and without any other external configuration by the user.

### Organisation

1. All development and testing shall be undertaken in either the Netbeans IDE or IntelliJ IDE.
2. Team communication relevant to the product-related aspects of the project (planning, development, testing) shall be recorded for future reference:
	1. Facebook chat - message archive
	2. Slack chat - message archive
	3. In-person - meeting minutes
3. JUnit 4.12 shall be used as the testing library across all development and testing systems.
4. Gradle 2.9 shall be used as the build automaton system across all development and testing systems.
5. All project-related documents, source code, tests and builds shall be kept on the team’s GitHub repository, with new versions being committed and pushed to the repository on a regular basis and as soon as a major change/refactoring occurs.
6. The software’s source code shall be documented extensively, defined as follows:
	1. The [Javadoc comment structure](http://www.oracle.com/technetwork/java/javase/documentation/index-jsp-135444.html) shall be used to document every field, method and class.
	2. The structure of Javadoc documentation shall follow the [Oracle style guide](http://www.oracle.com/technetwork/java/javase/documentation/index-137868.html).
	3. Single-line comments shall be used inside methods to make the purpose of lines/blocks of code clear, when deemed appropriate by the coder(s).
	4. For fragments of incomplete code which are pushed to the project’s GitHub repository or left to complete later, a
    single line comment starting with "//TODO: " shall be used to briefly describe the task to complete.
7. A copy of all project documents and source code shall be stored independently on each team member’s personal computer, regularly pulled from the central team GitHub repository. In addition, an up-to-date backup of the GitHub repository shall be kept in one or more of the team’s personal Dropbox repositories.

### External

1. The software shall be delivered by Thursday 5 May at 4:00PM, by online submission to Study Direct.
2. All libraries and external material used in the project shall be documented and credited to the original author(s), as per the University of Sussex’s [plagiarism](http://www.sussex.ac.uk/s3/?id=35) policies.
