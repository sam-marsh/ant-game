## State

Sense - The Ant locates a cell either in front (Ahead), to it's left (LeftAhead) or it's right (RightAhead) and updates it's state.

Move - The current direction of the Ant is obtained and it's position is updated to the sensed cell along with it's state.

Drop - The Ant's food content is put into the cell and it's current food and state are updated along with the cell's contents.

Flip - The Ant makes a decision to turn left or right by generating a random number and it's state is updated.

Turn - The Ant's current direction is updated to left or right and it's state is updated.

Mark - The Ant set's a marker of its own colour in the sensed cell and it's state is updated.

Unmark - An Ant's marker is cleared in the sensed cell and it's state is updated.

Pick Up - The Ant collects the amount of food in the sensed cell, it's food contents, the cells contents and it's state is updated.



## Stimulus

Move To Cell - The Ant has chosen to move to the sensed cell.

Decide - The Ant has chosen to decide whether to turn left or right.

Choice - The Ant has made it's choice and now turns either left or right.

Left - The Ant turns left.

Right - The Ant turns right.

Place Marker - The Ant has chosen to place a marker in the sensed cell.

Food - The Ant has located some food in the sensed cell.

Marker - The Ant has located a marker in the sensed cell.

Anthill - The Ant has located a cell which is part of it's Anthill after moving.

