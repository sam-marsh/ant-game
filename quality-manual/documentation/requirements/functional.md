# Functional requirements 
Version 1.0
Authors: Regan Ware and Kea Tossavainen

## Project: 
The team shall create an ant game and an ant brain which will take part in a competition against other teams in their ant worlds. 

## Functional requirements of the game:
- The program shall check if ant brain is valid
- The program shall check if ant world is valid
- The program shall visualise ant world
- The program shall allow two players to play
- The program shall keep statistics and determine the winner of the game based on those
- The program shall allow tournaments when more than two players submit an ant brain and players will be paired to play against each other

### Detailed requirements of different components:
### Ant brain:
- Ant brain shall be a finite state machine
- Ant brain shall be able to sense their surroundings (Here, ahead, LeftAhead, RightAhead)
- Ant brain shall be able to move to ahead, LeftAhead and RightAhead cells with a 14 turn rest after

### Ant: 
- Each ant shall have unique id that determines the order in which ants take actions
- Each ant shall have an integer between 0 and 9999 which represents current state of brain
- Each ant shall carry at most one particle of food, this shall be represented as boolean field
- Each ant shall have a "colour" denoting team
- Each ant shall have a "resting" integer which represents the number of turns they must stay inactive after moving
- Each ant shall have a has_food boolean recording whether the ant is carrying food
- If an ant is surrounded by 5 or more enemy ants it shall detect it and remove itself
- If an ant is cornered but only surrounded by 4 ants of the other species, it shall not die
- When an ant dies it shall drop 3 food onto the tile it was on
- Each ant shall keep track of their current direction
- Ant shall have several different actions:
    - Sense surroundings
    - Mark or unmark a cell
    - Pick up food from a current cell
    - Drop food
    - Turn left or right to change direction
    - Move to an adjacent cell
    - Flip

### Ant world:
- Ant world shall be hexagonal grid
- Each cell in the grid shall either be rocky or clear, or contain an ant hill that is red or black
- Ant hills shall not be adjacent to each other
- Ants should not go to each others ant hills
- Each clear cell in the grid can contain at most one of each of these:
    - At most one ant
    - Non-negative number of food between 0 and 9
    - At most one set of chemical markers from 6 different markers

### UI Requirements:
- Shall display the world including tile contents
- Scales with world dimensions (x,y)
- Ideally a swing GUI interface
- Can be text based due to time constraints (Print to IDE output)
- The UI will show the state of the game every *n* frames, customisable by the user.

## Functions the program shall have:
### Ant functions:
```
function state(a:ant):int     = <get state component of a>
function color(a:ant):color   = <get color component of a>
function resting(a:ant):int   = <get resting component of a>
function direction(a:ant):dir = <get direction component of a>
function has_food(a:ant):bool = <get has_food component of a>
function set_state(a:ant, s:int)     = <set state component of a to be s>
function set_resting(a:ant, r:int)   = <set resting component of a to be r>
function set_direction(a:ant, d:dir) = <set direction component of a to be d>
function set_has_food(a:ant, b:bool) = <set has_food component of a to be b>

function turn(lr:left_or_right, d:dir):dir =
  switch lr of
    case Left:  (d+5) mod 6
    case Right: (d+1) mod 6
    
    type sense_dir =
    Here           /* sense the ant's current cell */
  | Ahead          /* sense the cell straight ahead in the direction ant is facing */
  | LeftAhead      /* sense the cell that would be ahead if ant turned left */
  | RightAhead     /* sense the cell that would be ahead if ant turned right */

function sensed_cell(p:pos, d:dir, sd:sense_dir):pos =
  switch sd of
    case Here: p
    case Ahead: adjacent_cell(p, d)
    case LeftAhead: adjacent_cell(p, turn(Left,d))
    case RightAhead: adjacent_cell(p, turn(Right,d))
```

### Sense:
```
type condition =
    Friend             /* cell contains an ant of the same color */
  | Foe                /* cell contains an ant of the other color */
  | FriendWithFood     /* cell contains an ant of the same color carrying food */
  | FoeWithFood        /* cell contains an ant of the other color carrying food */
  | Food               /* cell contains food (not being carried by an ant) */
  | Rock               /* cell is rocky */
  | Marker(marker)     /* cell is marked with a marker of this ant's color */
  | FoeMarker          /* cell is marked with *some* marker of the other color */
  | Home               /* cell belongs to this ant's anthill */
  | FoeHome            /* cell belongs to the other anthill */
    
function other_color(c:color):color =
  switch c of
    case Red: Black
    case Black: Red
```

### Ant brain:
```
function get_instruction(c:color, s:state):instruction =  <get the instruction for state s of ant color c>
```

### Martial Arts functions:
```
function adjacent_ants(p:pos, c:color):int =
  let n = 0 in
  for d = 0..5 do
    let cel = adjacent_cell(p, d) in
    if some_ant_is_at(cel) && color(ant_at(cel)) = c then <increment n by 1>
  end;
  n
function check_for_surrounded_ant_at(p:pos) =
  if some_ant_is_at(p) then
    let a = ant_at(p) in
    if adjacent_ants(p, other_color(color(a))) >= 5 then begin
      kill_ant_at(p);
      set_food_at(p, food_at(p) + 3 + (if has_food(a) then 1 else 0))
    end
function check_for_surrounded_ants(p:pos) =
  check_for_surrounded_ant_at(p);
  for d = 0..5 do
    check_for_surrounded_ant_at(adjacent_cell(p,d))
  end
```

### Ant world functions:
```
function rocky(p:pos):bool = <true if the cell at position p is rocky>
function some_ant_is_at(p:pos):bool =  <true if there is an ant in the cell at position p>
function ant_at(p:pos):ant =  <return the ant in the cell at position p>
function set_ant_at(p:pos, a:ant) =  <record the fact that the given ant is at position p>
function clear_ant_at(p:pos) =  <record the fact that no ant is at position p>
function ant_is_alive(id:int):bool =  <true if an ant with the given id exists somewhere in the world>
function find_ant(id:int):pos =  <return current position of the ant with the given id>
function kill_ant_at(p:pos) = clear_ant_at(p)
function food_at(p:pos):int =  <return the amount of food in the cell at position p>
function set_food_at(p:pos, f:int) =  <record the fact that a given amount of food is at position p>
function anthill_at(p:pos, c:color):bool =   <true if the cell at position p is in the anthill of color c>

function adjacent_cell(p:pos, d:dir):pos =
  let (x,y) = p in
  switch d of
    case 0: (x+1, y)
    case 1: if even then (x, y+1) else (x+1, y+1)
    case 2: if even then (x-1, y+1) else (x, y+1)
    case 3: (x-1, y)
    case 4: if even then (x-1, y-1) else (x, y-1)
    case 5: if even then (x, y-1) else (x+1, y-1)
```

### Marker functions:
```
function set_marker_at(p:pos, c:color, i:marker) =  <set marker i of color c in cell p>
function clear_marker_at(p:pos, c:color, i:marker) =  <clear marker i of color c in cell p>
function check_marker_at(p:pos, c:color, i:marker):bool =  <true if marker i of color c is set in cell p>
function check_any_marker_at(p:pos, c:color):bool =  <true if ANY marker of color c is set in cell p>
```

### Cell_matches function:
```
function cell_matches(p:pos, cond:condition, c:color):bool =
  if rocky(p) then
    if cond = Rock then true else false
  else
    switch cond of
      case Friend:
        some_ant_is_at(p) &&
        color(ant_at(p)) = c
      case Foe:
        some_ant_is_at(p) &&
        color(ant_at(p)) <> c
      case FriendWithFood:
        some_ant_is_at(p) &&
        color(ant_at(p)) = c &&
        has_food(ant_at(p))
      case FoeWithFood:
        some_ant_is_at(p) &&
        color(ant_at(p)) <> c &&
        has_food(ant_at(p))
      case Food:
        food_at(p) > 0
      case Rock:
        false
      case Marker(i):
        check_marker_at(p, c, i)
      case FoeMarker:
        check_any_marker_at(p, other_color(c))
      case Home:
        anthill_at(p, c)
      case FoeHome:
        anthill_at(p, other_color(c))
```
