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
     * @return The outcome (LOSS, WIN, DRAW) of the match
     */
    public void run(int rounds, int speed)
    {
        int redFood = 0;
        int blackFood = 0;
        
        world.spawnAnts(new Colony(Colour.RED, playerRed.getBrain()), new Colony(Colour.BLACK, playerBlack.getBrain()));
        for (int i = 0; i < rounds; i++)
        {
            Set<Ant> toRemove = world.getAnts().stream().filter(Ant::surrounded).collect(Collectors.toSet());
            toRemove.stream().forEach(world::murder);

            world.getAnts().forEach(Ant::step);

            try {
                Thread.sleep(100 / speed);
            } catch (InterruptedException ignore) {}
        }

        for (int x = 0; x < world.width(); x++)
        {
            for (int y = 0; y < world.height(); y++)
            {
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

        if (redFood > blackFood) {
            outcome = new MatchOutcome(
                    new PlayerOutcome(playerRed, PlayerOutcome.Result.WIN, this),
                    new PlayerOutcome(playerBlack, PlayerOutcome.Result.LOSS, this)
            );
        } else if (blackFood > redFood) {
            outcome = new MatchOutcome(
                    new PlayerOutcome(playerRed, PlayerOutcome.Result.LOSS, this),
                    new PlayerOutcome(playerBlack, PlayerOutcome.Result.WIN, this)
            );
        } else {
            outcome = new MatchOutcome(
                    new PlayerOutcome(playerRed, PlayerOutcome.Result.DRAW, this),
                    new PlayerOutcome(playerBlack, PlayerOutcome.Result.DRAW, this)
            );
        }

    }

    public boolean finished() {
        return outcome != null;
    }

    public MatchOutcome getOutcome() {
        return outcome;
    }

    /**
     * @return The world this match takes place in
     */
    public World world() {
        return world;
    }
}
