# Requirements

## Functional

### Game

##### Game/1

| Specification | Details |
| -------- | --------- |
| **Requirement**     | Simulate a two-player game. |
| **Description** | Using two ant-brains and an ant-world, carry out a simulation of the game based on the ant finite-state machines and their environment. | 
| **Inputs** | Two different colonies (consisting of a colour, a team name and an ant-brain) and an ant-world. |
| **Source** | The GUI event loop. |
| **Outputs** | The final result of the game (which team won/lost). The final state of the world. |
| **Destination** | The GUI event loop. |
| **Actions** | For each turn (maximum 300,000), the ants on each cell shall be iterated over. Each ant will perform an action based on their ant-brain (specified as a finite-state machine and graph). The state of a cell (and possibly other ants) will be updated based on the action of the ant. The winner of the game is determined by the colony with the most food in their anthill (that is, the number of food particles in their anthill's cells). Food carried by ants is ignored in this score counting. |
| **Notes** | - |

##### Game/2

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

##### Game/3

| Specification | Details |
| -------- | --------- |
| **Requirement**     | Simulate a tournament. |
| **Description** | Plays ant games between multiple users, each with their own ant-brain specification. |
| **Inputs** | A list of teams (specified by a name and an ant-brain), and an arbitrary (but at least 1) number of ant-worlds. |
| **Source** | The GUI. |
| **Outputs** | The results of all matches, as well as the overall winner (the team with the highest number of wins). |
| **Destination** | The GUI tournament statistics display screen. |
| **Actions** | Iterates over all unique combinations `(team-1, team-2, world)` and simulates a game with that 3-tuple, with the first team playing as red and the second team playing as black. Keeps track of all wins, losses and statistics for each team. After all simulations, (which are *not* visualised to the user as per `Game/1.3` but rather the statistics are displayed) the overall winner is returned. The final results shall be calculated as follows: a team shall earn 2 points for a win, and 1 point for a draw. The score shall be tallied at the end, with the team having the highest score being the winner. If there is a draw, the tournament shall be repeated with the highest-scoring half of teams. |
| **Notes** | Iterating over all unique 3-tuples as described above will mean that every team will play every other team on every world as both red and black. The number of worlds used for a tournament shall be specified by the user when interacting with the GUI tournament setup view. |

##### Game/4

| Specification | Details |
| -------- | --------- |
| **Requirement**     | Parse an ant brain file. |
| **Description** | Checks if a user's custom ant brain (expected to have been provided in a file on disk) is valid according to the ant brain specification. If so, constructs an in-memory programmatic representation of the brain. |
| **Inputs** | A user-specified path to the ant-brain file.  |
| **Source** | A file in-memory that has been read from disk. |
| **Outputs** | A programmatic representation of the ant brain. |
| **Destination** | The GUI event loop. |
| **Actions** | Iterates through each line of the file, tokenising the line and validating each token. Builds a graph of ant-brain instructions which form a finite-state machine with transitions between instructions dependent on the conditions specified in the line. |
| **Notes** | The specification of an ant-brain file is further below: see `Parsing Specifications/Ant Brain`. |

##### Miscellaneous

| Identifier | Requirement | Rationale |
| --- | --- | --- |
| **Game/Misc.1** | At the start of a game, all ants shall face east. | User-requested. |
| **Game/Misc.2** | Ant's brains shall be initialised to state 0. | User requested. |
| **Game/Misc.3** | Each anthill cell shall be initially populated with a single ant of the anthill cell's colour. | User-requested. |
| **Game/Misc.4** | Ants shall be allocated identifiers in increasing numerical order, in top-to-bottom left-to-right order based on their position in the ant world. | User-requested. |

### Ant

##### Ant/1

| Specification | Details |
| -------- | --------- |
| **Requirement** | Sense a cell. |
| **Description** | Performs a sense operation on a particular cell of a particular condition (cell and condition specified by the ant-brain instruction). |
| **Inputs** | Ant-brain instruction context, access to the cell in question. |
| **Source** | The game controller. |
| **Outputs** |  A boolean result that informs the game controller which state the ant-brain has transitioned to (either the success state or failure-state, as specified in the original ant-brain file). |
| **Destination** | The game control loop. |
| **Actions** | The ant's current instruction stores information about the direction to sense in and the condition to sense for. The direction is one of `here`, `ahead`, `left-ahead` or `right-ahead`. The ant queries the world to determine the cell in the relevant direction, and checks the condition on that cell. If the condition holds for that cell, it transitions to the `success` state. If not, it transitions to the `fail`-state. |
| **Notes** | The predicates available to an ant for sense operations are `friend` (whether the given cell contains an ant of the same team), `foe` (whether the given cell contains an ant of the opposing team), `friend_with_food` (as with `friend`, but also carrying food), `foe_with_food` (as with `foe`, but also carrying food), `rock` (whether the cell type is rocky), `marker n` (whether the cell is marked with a marker, represented by an integer `n`, of the same team), `foe_marker` (whether the cell has marked by a foe, with *any* integer marker identifier), `home` (whether the cell is part of the anthill of that ants team), and `foe_home` (whether the cell is part of the opponent's anthill). |

##### Ant/2

| Specification | Details |
| -------- | --------- |
| **Requirement** | Mark a cell. |
| **Description** | Performs a mark operation on the current cell, placing a chemical marker as specified by the current ant-brain instruction. |
| **Inputs** | The world context, the ant-brain instruction context. |
| **Source** | The game controller. |
| **Outputs** |  No output required. |
| **Destination** | The game control loop. |
| **Actions** | Tells the world to mark the ant's current cell with a particular integer. Transitions to the next state. |
| **Notes** | The marker must be in the range `0..5`. |

##### Ant/3

| Specification | Details |
| -------- | --------- |
| **Requirement** | Unmark a cell. |
| **Description** | Performs an unmark operation on the current cell, removing a chemical marker as specified by the current ant-brain instruction. |
| **Inputs** | The world context, the ant-brain instruction context. |
| **Source** | The game controller. |
| **Outputs** |  No output required. |
| **Destination** | The game control loop. |
| **Actions** | Tells the world to unmark the ant's current cell, removing the marker with a particular identifier. Transitions to the next state. |
| **Notes** | The marker must be in the range `0..5`. |

##### Ant/4

| Specification | Details |
| -------- | --------- |
| **Requirement** | Pick up food. |
| **Description** | Picks up food from the current cell, transitioning to one of two ant-brain states depending on whether the pick-up operation was successful. |
| **Inputs** | The world context, the ant-brain instruction context. |
| **Source** | The game controller. |
| **Outputs** | A boolean result that informs the game controller which state the ant-brain has transitioned to (either the success state or failure-state, as specified in the original ant-brain file). |
| **Destination** | The game control loop. |
| **Actions** | Queries the world to see if the current cell has food. If not, transitions to the fail-state. Otherwise ets the internal state of the ant to `has-food = true`, as per requirement `Ant/Misc.4`. Removes a food particle from the current cell. Transitions to the success state. |
| **Notes** | - |

##### Ant/5

| Specification | Details |
| -------- | --------- |
| **Requirement** | Drop food. |
| **Description** | Drops food into the current cell, transitioning to the next state as specified in the ant-brain finite state machine. |
| **Inputs** | The world context, the ant-brain instruction context. |
| **Source** | The game controller. |
| **Outputs** | No output required. |
| **Destination** | The game control loop. |
| **Actions** | Gets the current cell from the world context and adds a food particle to it. Transitions to the next state. |
| **Notes** | If the ant does not have food, no action on the cell shall be taken, and the ant shall transition to the next state. |

##### Ant/6

| Specification | Details |
| -------- | --------- |
| **Requirement** | Turn. |
| **Description** | Causes the internal direction attribute of the ant (as specified in `Ant/Misc.7`) to change based on the current turn instruction's specification. |
| **Inputs** | The world context, the ant-brain instruction context. |
| **Source** | The game controller. |
| **Outputs** | No output required. |
| **Destination** | The game control loop. |
| **Actions** | The direction specified by the instruction can be one of `left` or `right`. This shall cause the direction of the ant to 'rotate' - i.e. `left` will cause the ant to rotate one edge anticlockwise in its hexagonal cell, and vice-versa for `right`. The ant will then transition into the next state. |
| **Notes** | - |

##### Ant/7

| Specification | Details |
| -------- | --------- |
| **Requirement** | Move. |
| **Description** | Causes the ant to move forward based on its direction into an adjacent cell (if possible). |
| **Inputs** | The world context, the ant-brain instruction context. |
| **Source** | The game controller. |
| **Outputs** | A boolean result that informs the game controller which state the ant-brain has transitioned to (either the success state or failure-state, as specified in the original ant-brain file). |
| **Destination** | The game control loop. |
| **Actions** | Based on the ant's current direction and the ant's current cell, the world can be queried for the cell in front of the ant. If the cell is free (not rocky and does not contain another ant), the ant shall remove itself from the current cell and add itself to the relevant adjacent cell. It shall then update its own `resting` attribute to indicate to the game controller that it must now rest for 14 moves. Then it will transition to the success state. If not free, the ant shall transition to the fail-state. |
| **Notes** | Resting is not *handled* here, rather it is handled by the game controller which will check the resting attribute and not call any instruction-step methods on the ant unless not resting. |

##### Ant/8

| Specification | Details |
| -------- | --------- |
| **Requirement** | Flip. |
| **Description** | Causes the ant to transition into one of two states depending on the result of a pseudo-random generated integer. |
| **Inputs** | The world context, the ant-brain instruction context. |
| **Source** | The game controller. |
| **Outputs** | A boolean result that informs the game controller which state the ant-brain has transitioned to (either state-1 or state-2, as specified in the original ant-brain file). |
| **Destination** | The game control loop. |
| **Actions** | Using the system's random number generator, the ant shall produce a random integer between `0..(n-1)`, where `n` is the integer specified by the flip instruction. If `0`, the ant shall transition to state-1. Otherwise, it shall transition to state-2. |
| **Notes** | - |

##### Miscellaneous

| Identifier | Requirement | Rationale |
| --- | --- | --- |
| **Ant/Misc.1** | Each ant shall have an associated unique integer identifier. |  This identifier shall determine the order in which the ant moves as compared to other ants (with ants moving in ascending order of identifier). |
| **Ant/Misc.2** | Each ant shall have an associated colour, either `red` or `black`. | The colour shall identify the ant's team and also make it clear to the user in world visualisation. |
| **Ant/Misc.3** | Each ant shall use the finite-state machine representation of its brain to store its current state. | This state is queried by the game controller to decide how the ant interacts with the world. |
| **Ant/Misc.4** | Each ant shall hold a `boolean` field that tracks whether the ant is holding food. | Accessible to the game controller since it performs different actions depending on whether an ant carries food or not. |
| **Ant/Misc.5** | Each ant shall hold an `integer` field that tracks how many turns it is resting for. | When an ant rests, it is not allowed to move. This field shall be decremented by the game loop, and if `0` then the ant is able to move. |
| **Ant/Misc.6** | Ants shall die if surrounded by `5` ants of the opposing team. | User-specified requirement. Note: this means that ants on the edge of the game-world or next to certain rocky structures are not able to be killed, since they do not have `5` free cells surrounding them. |
| **Ant/Misc.7** | The ant shall track its own direction. | The direction of the ant shall be used by the game controller for operations such as moving the ant forward or sensing. |

### World

##### World/1

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

##### World/2

| Specification | Details |
| -------- | --------- |
| **Requirement**     | Generate random contest worlds. |
| **Description** | Creates random contest worlds that conform to the requirements for a tournament, as described in the *Actions* section below. |
| **Inputs** | None. |
| **Source** | The tournament setup, or alternatively the GUI. That is, this has two different operations - either the user requests a custom ant world (to write to file), or the contest world is randomly generated as a tournament is starting. |
| **Outputs** | A programmatic representation of an ant world. |
| **Destination** | The GUI event loop (or tournament setup). |
| **Actions** | Pseudo-randomly generates an ant world of 150x150 cells. This ant world shall have rocky edge cells. It shall have two hexagonal ant hills of edge length 7, for red and black, randomly located. It shall have 14 randomly located rocks. It shall have 11 randomly located blobs of food, where a blob of food is a 5x5 rectangle. Each food blob shall be randomly oriented and each cell in the ant blob shall have 5 food particles. All separate components of the world (ant-hills, food-blobs, rocks) shall be separated by at least one clear cell. |
| **Notes** | A component for converting a programmatic representation of an ant world to a textual representation should also be developed. |

##### Miscellaneous

| Identifier | Requirement | Rationale |
| --- | --- | --- |
| **World/Misc.1** | The ant world programmatic structure shall present an interface that appears as if the world is comprised of hexagonal cell (that is, each non-edge cell should be surrounded by 6 adjacent cells). | As specified in the user requirements, each cell is a hexagon. |
| **World/Misc.2** | Each cell shall have an associated type, which shall be one of `rocky`, `clear`, `red-ant-hill` or `black-ant-hill`. | As per user requirements, `rocky` cells are inaccessible to ants, and `ant-hill` cells belong to their associated teams and are where ants are initially spawned. |
| **World/Misc.4** | Ant hills cannot be adjacent to one another. | Direct user requirement, reasoning unclear. |
| **World/Misc.5** | Each non-rocky cell can contain `0-1` ants. | Direct user requirement. Ants cannot 'walk over' each other. |
| **World/Misc.6** | Each clear cell can contain `0+` food particles. | Food cannot logically be negative. Note: as specified by the ant-world cell specification, initially a cell can contain only `0-9` food particles. However, as food is dropped this number can increase above the initial limit unboundedly. |
| **World/Misc.7** | Each clear cell can contain any non-negative number of chemical markers for each team. | Ants can mark the same cell with multiple markers, but cannot modify their opponents. |

### GUI

##### GUI/1

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

##### Miscellaneous

| Identifier | Requirement | Rationale |
| --- | --- | ---- |
| **GUI/Misc.1** | The GUI shall display the entire world, including all world content. | Allows viewing of the entire state of the game in a user-friendly way. |
| **GUI/Misc.2** | The game view interface shall scale proportionally to world dimensions `(x, y)`. | Scaling means the entire world state is always visible to users. |
| **GUI/Misc.3** | There will be a user-customisable option to display the state of the game every `n` frames. | Makes the game play-speed personalisable to the user, to avoid simulation being too short or too long. |

### Parsing Specifications

#### Ant-Brain

This is the specification for an ant-brain file. These are provided by the user, but development of the brain parser shall interpret the file contents as conforming to the following (taken directly from the client requirements):

-  Each line in the file represents one state. The first line is state 0, the second line state 1, and so on.
- The file may contain at most 10000 lines.
- Each line shall consists of a sequence of whitespace-separated tokens, followed (optionally) by a comment beginning with a semicolon and extending to the end of the line.
- Tokens are either keywords or integers.
- Keywords are case-insensitive.
- The possible instruction tokens are:
   - `Sense`
   - `Mark`
   - `Unmark`
   - `PickUp`
   - `Drop`
   - `Turn`
   - `Move`
   - `Flip`
- The tokens for sensing directions are:
   - `Here`
   - `Ahead`
   - `LeftAhead`
   - `RightAhead`
- The tokens for conditions are:
   - `Friend`
   - `Foe`
   - `FriendWithFood`
   - `FoeWithFood`
   - `Food`
   - `Rock`
   - `Marker i`, where `i` is an integer from `0-5`
   - `FoeMarker`
   - `Home`
   - `FoeHome`
- The tokens for turn are:
   - `Left`
   - `Right`

#### Ant World

This is the specification for an ant-world file. These are both provided by the user and generated randomly, so development of the world parser and contest world generator shall conform to the following (taken directly from the client requirements):

- The first line of the file contains a single integer representing the size of the world in the `x` dimension.
- The second line of the file contains a single integer representing the size of the world in the `y` dimension.
- The rest of the file consists of `y` lines, each containing `x` one-character cell specifiers, separated by spaces (even lines also contain a leading space before the first cell specifier). The top-left cell specifier corresponds to position `(0, 0)`. **Note**: although generated worlds shall conform to this specification, the parser shall ignore leading and extra whitespace. 
- The cell specifiers are:
   - `#`: rocky cell
   - `.`: clear cell (containing nothing interesting)
   - `+`: red anthill cell
   - `-`: black anthill cell
   - `1`-`9`: clear cell containing the given number of food particles