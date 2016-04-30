## User Requirements

- The program should check if an ant-brain supplied by a player is syntactically well-formed.
- The program should check if a given description of an ant world is syntactically well-formed and meets the requirements for ant worlds used in tournaments.
- The program should be able visualise a given ant world.
- The program should be able to generate random but well-formed ant worlds.
- The program should allow two players to play: i.e. enable two players to upload their ant-brains and choose an ant-world, and then run the game in the ant world, taking statistics and determining the winner of the game.
- The program should allow users to play tournaments, where an arbitrary number of players can upload ant-brains, who are all paired up to play against each other. The overall tournament winner is the ant brain that wins the most individual games.
- The development team should also produce a novel, proof-of-concept ant-brain.
- The user can upload brains into an ant world with two ant colonies, one for each player.
- The program should also support uploading custom ant world from files.
- An ant world should simulate the behaviour of both kinds of ants, using the ant-brains supplied by the players.
- The two species of ants are placed in a random world containing two anthills, some food sources, and several obstacles. 
- Ants must explore the world, find food and bring it back to their anthill. 
- Ants can communicate or leave trails by means of chemical markers.
- Each species of ants can sense (with limited capabilities), but not modify, the markers of the other species.
- Ants can also attack the ants of the other species by surrounding them
- Ants that die as a result of an attack become food.
- If an ant is adjacent to 5 (or 6) ants of the other species then it dies. However, if an ant is in a cell in the very corner of the grid or along one of the edges it can only have at most 4 ants adjacent to it - therefore it cannot be killed.
- The match is won by the species with the most food in its anthill at the end of 300,000 rounds.
- A cell can hold an unlimited amount of food during the course of the simulation.
- In a tournament, each team will play every other team twice on each of the contest worlds. Once as red and once as black. So for 23 teams, each team will be pitted up against 22 other teams.
- There must be at least one empty cell between adjacent non-food items.

