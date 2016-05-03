package antgame.core;

import antgame.core.Colony.Colour;
import antgame.core.world.*;
import antgame.core.world.Cell.Type;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * A match between two supplied players.
 * Once match has been constructed call run() which returns the winning player.
 * @author Regan Ware
 */
public class Match {

    public static int NUM_ROUNDS = 300000;

    //The player to be assigned to the red colony within the match
    private final Player playerRed;
    //The player to be assigned to the black colony within the match
    private final Player playerBlack;
    //The world this match takes place in
    private final World world;

    private MatchOutcome outcome;

    /**
     * Creates a new Match using the supplied players and world
     *
     * @param playerRed The player who's brain will control the red colony
     * @param playerBlack The player who's brain will control the black colony
     * @param world The parsed or generated world the match will be played in
     */
    public Match(Player playerRed, Player playerBlack, World world)
    {
        this.playerRed = playerRed;
        this.playerBlack = playerBlack;
        this.world = world;
    }
    
    /**
     * Runs the match determining and returning the winner based on which colony has more food in their ant hills
     *
     * @param rounds The number of rounds to run the match for (Usually NUM_ROUNDS at 300000)
     *
     */
    public void run(int rounds, int speed)
    {
        //Used to determine the winner of the match at the end
        int redFood = 0;
        int blackFood = 0;

        //Populate the world with ants, using the appropriate player to form and supply new colonies
        world.spawnAnts(new Colony(Colour.RED, playerRed.getBrain()), new Colony(Colour.BLACK, playerBlack.getBrain()));

        //Loop for the supplied number of rounds
        for (int i = 0; i < rounds; i++)
        {
            //Loop through the ants in the world (stream to handle dynamic removal) filtering ants that are surrounded
            Set<Ant> toRemove = world.getAnts().stream().filter(Ant::surrounded).collect(Collectors.toSet());
            //Murder each of the surrounded ants
            toRemove.stream().forEach(world::murder);

            //After ants have been murdered let the survivors step
            world.getAnts().forEach(Ant::step);

            try { //Manually slow down simulation for display purposes
                Thread.sleep(100 / speed);
            } catch (InterruptedException ignore) {}
        }

        //Loop through the entire world, x and y going from 0 to the world's width and height respectively
        for (int x = 0; x < world.width(); x++)
        {
            for (int y = 0; y < world.height(); y++)
            {
                //If the cell is an anthill, add the food present in it to the appropriate colour's food total
                Cell curr = world.cell(x, y);
                if (world.cell(x, y).getType() == Type.ANTHILL_RED)
                {
                    redFood += curr.getFoodAmount();
                }
                else if (world.cell(x, y).getType() == Type.ANTHILL_BLACK)
                {
                    blackFood += curr.getFoodAmount();
                }
            }
        }

        //If there's more red food, the red player wins
        if (redFood > blackFood) {
            outcome = new MatchOutcome(
                    new PlayerOutcome(playerRed, PlayerOutcome.Result.WIN, this),
                    new PlayerOutcome(playerBlack, PlayerOutcome.Result.LOSS, this)
            );
        //If there's more black food, the black player wins
        } else if (blackFood > redFood) {
            outcome = new MatchOutcome(
                    new PlayerOutcome(playerRed, PlayerOutcome.Result.LOSS, this),
                    new PlayerOutcome(playerBlack, PlayerOutcome.Result.WIN, this)
            );
        //If they're the same it's a draw. How droll.
        } else {
            outcome = new MatchOutcome(
                    new PlayerOutcome(playerRed, PlayerOutcome.Result.DRAW, this),
                    new PlayerOutcome(playerBlack, PlayerOutcome.Result.DRAW, this)
            );
        }

    }

    /**
     * Checks if the match has finished or not
     * @return True if the match is finished, false otherwise.
     */
    public boolean finished() {
        return outcome != null;
    }

    /**
     * Gets the outcome of the match
     * @return The MatchOutcome produced at the end of the match
     */
    public MatchOutcome getOutcome() {
        return outcome;
    }

    /**
     * Gets the match's World
     * @return The world this match takes place in
     */
    public World world() {
        return world;
    }
}
