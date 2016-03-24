## States

Sense Cell - An Ant senses the cell either in front of it (Ahead), to it's left (LeftAhead) or it's right (RightAhead).

Move To Anthill - If the sensed cell is it's own Anthill then it's position is updated along with it's brain state and resting time.

Drop - When the Ant reaches it's Anthill it empties it's food inside the sensed cell at it's anthill and updates its brain state.

Pick Up Food - If the sensed cell contains food then it's food contents is updated with the amount of food at that cell and its brain state is updated.

Mark - The cells contents is updated with a marker of the ant's colour and the ant's brain state is updated.

Unmark - The sensed cell's marker is cleared and the ant's brain state is updated.

Flip - The ant generates a random number to aid its decision making and its brain state is updated.

Move Forward - The Ant moves to the sensed cell, it's position is updated along with it's brain state. It's resting time is also reset.

Turn Left - The Ant's direction and brain state is updated

Turn Right - The Ant's direction and brain state is updated




## Stimulus

Marker - The Ant has chosen to unmark the sensed cell.

Home - The Ant has identified the sensed cell as it's Anthill.

Move - The sensed cell is clear and the Ant has chosen to move to it.

Place Marker - The Ant has chosen to place a marker at the sensed cell

Food - The Ant has identified food at the sensed cell.

Left - The Ant has chosen to turn it's direction and move left.

Right - The Ant has chosen to turn it's direction and move right.

Forward - The Ant has chosen to move forward.

Drop Food - The Ant drops it's food contents at it's Anthill.

