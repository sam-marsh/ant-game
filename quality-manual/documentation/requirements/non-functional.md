# Requirements

## Non-functional

### Product

1. The software should be reasonably efficient, defined as following:
	1. Response time of the system when interacting with any component of the GUI should be negligible before the user is presented with the interaction’s result or with a loading screen.
	2. The GUI should not hang (that is, be unresponsive to user interaction) for more than one second after any user interaction.
	3. Parsing user files (ant-brain, ant-world) should take no longer than 5 seconds.
	4. The two-player game simulation should take no longer than 30 seconds for 300,000 rounds, not including artificially introduced  delays when displaying the game to the user.
2. The software shall be cross-platform across commonly used desktop operating systems (Windows 7+, Mac OS X 10.8.3+, Linux) that support Java 8.0.
3. The software shall not require internet or network access to run, and will have access to the full feature-set of the program offline.
4. The software should be reliable, defined as following:
	1. The software should crash no more than 0.1% of the time when parsing user files, even if the files are corrupt or not well-formed.
	2. The software should crash no more than 0.1% of the time when simulating a game or tournament.
	3. The GUI should be stable and responsive to user input above 99% of the time, including when a game is running. This applies only to systems with more than 400MB of free RAM.
5. The software should have a minimal learning curve for new users. That is, the GUI should have intuitive and clear controls that step the user(s) through each stage of the game without being required to follow external instructions (not including understanding the rules of the game and the development of a user’s ant-brain file).
6. The system shall use store no more than 1MB of settings data, not including ant-brain and ant-world user files.
7. The software shall use no more than 100MB of RAM, with the exception of a maximum of 400MB of RAM while executing/displaying a game.
8. If the program crashes, a log containing the stack trace of the error and any other pertinent details shall be written to the program’s working directory, titled “ant-game-crash-<current unix time>.log”.

### Organisation

1. All development and testing shall be undertaken in either the Netbeans IDE or IntelliJ IDE.
2. Team communication relevant to the technical aspects of the project (planning, development, testing) shall be recorded for future reference:
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
	4. For fragments of incomplete code which are pushed to the project’s GitHub repository or left to complete later, a single line comment starting with “//TODO: “ shall be used to briefly describe the task to complete.
7. A copy of all project documents and source code shall be stored independently on each team member’s personal computer, regularly pulled from the central team GitHub repository. In addition, an up-to-date backup of the GitHub repository shall be kept in one or more of the team’s personal Dropbox repositories.
8.  

### External

1. The software shall be delivered by Thursday 5 May at 4:00PM, by online submission to Study Direct.
2. All libraries and external material used in the project shall be documented and credited to the original author(s), as per the University of Sussex’s [plagiarism](http://www.sussex.ac.uk/s3/?id=35) policies.