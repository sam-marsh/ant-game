# Functional requirements

##### Game/1.1

| Specification | Details |
| -------- | --------- |
| **Requirement**     | Parse ant brain file. |
| **Description** | Checks if a user's custom ant brain (expected to have been provided in a file on disk) is valid according to the ant brain specification. If so, constructs an in-memory programmatic representation of the brain. |
| **Inputs** | A user-specified path to the ant-brain file.  |
| **Source** | A file in-memory that has been read from disk. |
| **Outputs** | A programmatic representation of the ant brain. |
| **Destination** | The GUI event loop. |
| **Actions** | Iterates through each line of the file, tokenising the line and validating each token. Builds a graph of ant-brain instructions which form a finite-state machine with transitions between instructions dependent on the conditions specified in the line. |
| **Notes** | - |

##### Game/1.2

| Specification | Details |
| -------- | --------- |
| **Requirement**     | Parse ant world file. |
| **Description** | Checks if a user's custom ant world (expected to have been provided in a file on disk) is valid according to the ant world specification. If so, constructs an in-memory programmatic representation of the world. |
| **Inputs** | A user-specified path to the ant-world file. |
| **Source** | A file in-memory that has been read from disk. |
| **Outputs** | A programmatic representation of the ant world. |
| **Destination** | The GUI event loop. |
| **Actions** | Iterates through each line of the file, tokenising the line and validating each token. Intepreting each token as a cell and building a hexagonal programmatic structure containing cells with properties specified in the file. |
| **Notes** | - |

##### Game/1.3

| Specification | Details |
| -------- | --------- |
| **Requirement**     | Visualise ant world. |
| **Description** | Uses a Swing GUI to show users a representation of the ant world in a game at a particular turn. | 
| **Inputs** | An ant world containing information about cells, including their location, their type, and what they contain (e.g. ants, food). |
| **Source** | A currently-running ant game. |
| **Outputs** | An image (or otherwise) showing the state of the game in a human-readable fashion, displayed using a Swing interface. |
| **Destination** | The game loop. |
| **Actions** | Loops through all cells in the game and draws them to screen, with an indication of the type of cell and what it contains specified by a colour (colours not yet specified, will be at the discretion of the programmers and testers). |
| **Notes** | - |

##### Game/1.4

| Specification | Details |
| -------- | --------- |
| **Requirement**     | Simulate a two-player game. |
| **Description** | Using two ant-brains and an ant-world, carry out a simulation of the game based on the ant finite-state machines and their environment. | 
| **Inputs** | Two different colonies (consisting of a colour, a team name and an ant-brain) and an ant-world. |
| **Source** | The GUI event loop. |
| **Outputs** | The final result of the game (which team won/lost). The final state of the world. |
| **Destination** | The GUI event loop. |
| **Actions** | For each turn (maximum 300,000), the ants on each cell shall be iterated over. Each ant will perform an action based on their ant-brain (specified as a finite-state machine and graph based on requirement `Game/1.1`). The state of a cell (and possibly other ants) will be updated based on the action of the ant. |
| **Notes** | - |

##### Game/1.5

| Specification | Details |
| -------- | --------- |
| **Requirement**     | Produce statistics for an ant game. |
| **Description** | By keeping track of the state of the world at all times (or having access to the history of the game), for each team the following statistics will be computed: <ul><li>Amount of food in anthill</li><li>Number of foe's ants killed</li><li>Number of team's ants left</li><li>Number of movements made</li><li>Number of markings left</li></ul>Further statistics can be added at the discretion of the programmers where convenient. |
| **Inputs** | The state of an ant-game at a particular turn (accumulative) or the full history of an ant-game at the end of the game. |
| **Source** | An ant game. |
| **Outputs** | The statistics as specified above. |
| **Destination** | The GUI statistics display screen. |
| **Actions** | Given the state of the game at a particular turn, all relevant statistics counters shall be updated. At the end of the game, the statistics shall be calculated and output. |
| **Notes** | - |

##### Game/1.6

| Specification | Details |
| -------- | --------- |
| **Requirement**     | Simulate a tournament. |
| **Description** | Plays ant games between multiple users, each with their own ant-brain specification. |
| **Inputs** | A list of teams (specified by a name and an ant-brain), and an arbitrary (but at least 1) number of ant-worlds. |
| **Source** | The GUI. |
| **Outputs** | The results of all matches, as well as the overall winner (the team with the highest number of wins). |
| **Destination** | The GUI tournament statistics display screen. |
| **Actions** | Iterates over all unique combinations `(team-1, team-2, world)` and simulates a game with that 3-tuple, with the first team playing as red and the second team playing as black. Keeps track of all wins, losses and statistics for each team. After all simulations, (which are *not* visualised to the user as per `Game/1.3` but rather the statistics are displayed) the overall winner is returned. |
| **Notes** | Iterating over all unique 3-tuples as described above will mean that every team will play every other team on every world as both red and black. |

##### Brain/2.1

| Specification | Details |
| -------- | --------- |
| **Requirement**     | Simulate a tournament. |
| **Description** | Plays ant games between multiple users, each with their own ant-brain specification. |
| **Inputs** | A list of teams (specified by a name and an ant-brain), and an arbitrary (but at least 1) number of ant-worlds. |
| **Source** | The GUI. |
| **Outputs** | The results of all matches, as well as the overall winner (the team with the highest number of wins). |
| **Destination** | The GUI tournament statistics display screen. |
| **Actions** | Iterates over all unique combinations `(team-1, team-2, world, colour)` and simulates a game with that 4-tuple. Keeps track of all wins, losses and statistics for each team. After all simulations, (which are *not* visualised to the user as per `Game/1.3` but rather the statistics are displayed) the overall winner is returned. |
| **Notes** | - |

| **Requirement** | **Rationale** |
| --- | --- |
| Ant brain is a finite state machine | Move from state to another |
| Ant brain is able to sense surroundings | Sense here, ahead, left ahead, right ahead |
| Move | Move to the sensed surroundings |

##### Ant

| **Requirement:** | **Rationale** |
| --- | --- |
| Ant has unique id | Id determines order in which ants take actions |
| Ant has an integer between 0-9999 | Integer represents current state of brain |
| Boolean has\_food field | Shows if ant carries food |
| Ant shall have colour | Denotes which team ant is a part of |
| Integer representing "resting" | Represents the number of turns ant rests |
| If ant is surrounded by 5 ants of other team | Ant dies |
| If cornered by 4 ants of other team | Ant doesn't die |
| Ant keeps track of direction | To know the current direction |
| Ant has several different actions | Sense surroundings, mark/unmark cell, pick up food, drop food, turn left/right, move to adjacent cell, flip |

##### World

| **Requirement** | **Rationale** |
| --- | --- |
| Ant world is an hexagonal grid | Each cell is a hexagon |
| Each cell can be rocky, clear or contain an ant hill | Ant cannot go to rocky cells and each others ant hills |
| Ant hills can't be adjacent to each other |   |
| Each cell can contain at most one ant, non-negative number of food between 0-9 and at most one set of chemical markers | Ants can't go to same cells as other ants, cells can contain food that ants can pick and ants can sense chemical markers |

##### GUI/3.1

| Specification | Details |
| -------- | --------- |
| **Requirement**     | Parse ant brain file. |
| **Description** | Checks if a user's custom ant brain (expected to have been provided in a file on disk) is valid according to the ant brain specification. If so, constructs an in-memory programmatic representation of the brain. |
| **Inputs** | A user-specified path to the ant-brain file.  |
| **Source** | A file in-memory that has been read from disk. |
| **Outputs** | A programmatic representation of the ant brain. |
| **Destination** | The GUI event loop. |
| **Actions** | Iterates through each line of the file, tokenising the line and validating each token. Builds a graph of ant-brain instructions which form a finite-state machine with transitions between instructions dependent on the conditions specified in the line. |
| **Notes** | - |

| **Requirement** | **Rationale** |
| --- | --- |
| Displays world including cell content | Allows player to see the world |
| Scales with world dimensions (x,y) |   |
| Swing GUI interface |   |
| UI will show the state of the game every n frames | Customisable by the user |
