# Acceptance Criteria

## Introduction

This document describes the final validation and verification stage. This acceptance criteria specification primarily focuses on
the user and release testing phase of the project, rather than the development testing that is described in the test specification
document.

The two main procedures to be considered in ensuring the acceptance of the complete product will be:
- Validation: ensuring that all components of the program satisfy the *user* requirements.
- Verification: ensuring that all components of the program satisfy the *functional* and *non-functional* requirements.

## Test Environment

For this final testing stage, the test environment shall be made as 'realistic' as possible. That is, unless debugging,
the tests shall be carried out in a program environment that is similar to the environment in which the client will use the program.
These program environments are specified in the non-functional requirements (see supported systems in the non-functional requirements
document). This will essentially cover user and release testing, wherein the program shall be used by the team as if they
were a user.

In particular, the system should be tested on each of the three main operating systems as identified in the non-functional requirements.

## Acceptance Tests

The acceptance tests shall involve using the program with the client-provided data files (using various combinations of ant-brains
and ant-worlds). The system should be 'stress-tested', meaning:
- Non-conventional behaviour - experimenting with the GUI performing relatively uncommon actions (e.g. running a game without specifying an ant-brain, specifying no teams in a tournament, etc.) to check that the program does not fall into an inconsistent state.
- Large tasks - for example, testing the system with large ant brain and world files.

In addition to the above, the user requirements shall be individually checked against the program by each member of the team. The
non-functional requirements should also be checked by each member of the team, from a user's perspective.


### Verification Acceptance Tests

#### Game

##### Test 1: Parse an Ant Brain

Prerequisites: A user-specified path to the ant-brain file.

Test to perform: Checks if a user's custom ant brain (expected to have been provided in a file on disk) is valid according to the ant brain specification. If so, constructs an in-memory programmatic representation of the brain.

Expected result: A programmatic representation of the ant brain. It iterates through each line of the file, tokenising the line and validating each token. Builds a graph of ant-brain instructions which form a finite-state machine with transitions between instructions dependent on the conditions specified in the line.

##### Test 2: Simulate A 2 Player Game

Prerequisites: Two different colonies (consisting of a colour, a team name and an ant-brain) and an ant-world.

Test to perform: a simulation of the game based on the ant finite-state machines and their environment.

Expected Result: For each turn (maximum 300,000), the ants on each cell shall be iterated over. Each ant will perform an action based on their ant-brain. The state of a cell (and possibly other ants) will be updated based on the action of the ant.

##### Test 3: Produce statistics for a game

Prerequisites: The state of an ant-game at a particular turn (accumulative) or the full history of an ant-game at the end of the game.

Test to perform: for each team the following statistics will be computed:

- Amount of food in anthill
- Number of foe's ants killed
- Number of team's ants left
- Number of movements made
- Number of markings left

Expected Result: Given the state of the game at a particular turn, all relevant statistics counters shall be updated. At the end of the game, the statistics shall be calculated and output.

##### Test 4: Simulate a tournament

Prerequisites: A list of teams (specified by a name and an ant-brain), and an arbitrary (but at least 1) number of ant-worlds.

Test to perform: Plays ant games between multiple users, each with their own ant-brain specification.

Expected Result: Iterates over all unique combinations (team-1, team-2, world) and simulates a game with that 3-tuple, with the first team playing as red and the second team playing as black. Keeps track of all wins, losses and statistics for each team. After all simulations, the overall winner is returned.


#### Ant

##### Test 1: Sensing a Cell

Test to perform: Performs a sense operation on a particular cell of a particular condition (cell and condition specified by the ant-brain instruction).

Expected Result: The ant's current instruction stores information about the direction to sense in and the condition to sense for. The direction is one of here, ahead, left-ahead or right-ahead. The ant queries the world to determine the cell in the relevant direction, and checks the condition on that cell. If the condition holds for that cell, it transitions to the success state. If not, it transitions to the fail-state.

##### Test 2: Marking a Cell

Test to perform: Performs a mark operation on the current cell, placing a chemical marker as specified by the current ant-brain instruction.

Expected Result: Tells the world to mark the ant's current cell with a particular integer. Transitions to the next state.

##### Test 3: Unmarking a Cell

Test to perform: Performs an unmark operation on the current cell, removing a chemical marker as specified by the current ant-brain instruction.

Expected Result: Tells the world to unmark the ant's current cell, removing the marker with a particular identifier. Transitions to the next state.


##### Test 4: Picking up food

Test to perform: Picks up food from the current cell, transitioning to one of two ant-brain states depending on whether the pick-up operation was successful.

Expected Result: Queries the world to see if the current cell has food. If not, transitions to the fail-state. Otherwise ets the internal state of the ant to has-food = true, as per requirement Ant/Misc.4. Removes a food particle from the current cell. Transitions to the success state.


##### Test 5: Dropping food

Test to perform: Drops food into the current cell, transitioning to the next state as specified in the ant-brain finite state machine.

Expected Result: Gets the current cell from the world context and adds a food particle to it. Transitions to the next state.


##### Test 6: Turning Direction

Test to perform: Causes the internal direction attribute of the ant (as specified in Ant/Misc.7) to change based on the current turn instruction's specification.

Expected Result: The direction specified by the instruction can be one of left or right. This shall cause the direction of the ant to 'rotate' - i.e. left will cause the ant to rotate one edge anticlockwise in its hexagonal cell, and vice-versa for right. The ant will then transition into the next state.

##### Test 7: Moving To a Cell

Test to perform: Causes the ant to move forward based on its direction into an adjacent cell (if possible).

Expected Result: Based on the ant's current direction and the ant's current cell, the world can be queried for the cell in front of the ant. If the cell is free (not rocky and does not contain another ant), the ant shall remove itself from the current cell and add itself to the relevant adjacent cell. It shall then update its own resting attribute to indicate to the game controller that it must now rest for 14 moves. Then it will transition to the success state. If not free, the ant shall transition to the fail-state.

##### Test 8: Flip

Test to perform:  Causes the ant to transition into one of two states depending on the result of a pseudo-random generated integer.

Expected Result: Using the system's random number generator, the ant shall produce a random integer between 0..(n-1), where n is the integer specified by the flip instruction. If 0, the ant shall transition to state-1. Otherwise, it shall transition to state-2.


#### World

##### Test 1: Parse ant world file

Prerequisites: A user-specified path to the ant-world file.

Test to perform: Checks if a user's custom ant world (expected to have been provided in a file on disk) is valid according to the ant world specification. If so, constructs an in-memory programmatic representation of the world.

Expected result: Iterates through each line of the file, tokenising the line and validating each token. Intepreting each token as a cell and building a hexagonal programmatic structure containing cells with properties specified in the file.


#### GUI

Test 1: Visualise an ant world

Test to perform: Uses a Swing GUI to show users a representation of the ant world in a game at a particular turn.

Expected result: Loops through all cells in the game and draws them to screen, with an indication of the type of cell and what it contains specified by a colour (colours not yet specified, will be at the discretion of the programmers and testers).
