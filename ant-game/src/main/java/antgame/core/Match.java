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

    //number of iterations
    public static int NUM_ROUNDS = 300000;

    //match speed is from 1-100
    public static int DEFAULT_MATCH_SPEED = 50;

    //The player to be assigned to the red colony within the match
    private final Player playerRed;
    //The player to be assigned to the black colony within the match
    private final Player playerBlack;
    //The world this match takes place in
    private final World world;

    private final Colony redColony;
    private final Colony blackColony;

    private final ColonyStatisticsTracker redStatisticsTracker;
    private final ColonyStatisticsTracker blackStatisticsTracker;

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
        this.redColony = new Colony(Colour.RED, playerRed.getBrain());
        this.blackColony = new Colony(Colour.BLACK, playerBlack.getBrain());
        this.redStatisticsTracker = new ColonyStatisticsTracker(redColony, world);
        this.blackStatisticsTracker = new ColonyStatisticsTracker(blackColony, world);
    }

    public Player getRedPlayer() {
        return playerRed;
    }

    public Player getBlackPlayer() {
        return playerBlack;
    }

    /**
     * Runs the match determining and returning the winner based on which colony has more food in their ant hills
     *
     * @param rounds The number of rounds to run the match for (Usually NUM_ROUNDS at 300000)
     *
     */
    public void run(int rounds, int speed)
    {
        //Populate the world with ants, using the appropriate player to form and supply new colonies
        world.spawnAnts(redColony, blackColony);

        //Loop for the supplied number of rounds
        for (int i = 0; i < rounds; i++)
        {
            //Loop through the ants in the world (stream to handle dynamic removal) filtering ants that are surrounded
            Set<Ant> toRemove = world.getAnts().stream().filter(Ant::surrounded).collect(Collectors.toSet());
            //Murder each of the surrounded ants
            toRemove.stream().forEach(world::murder);

            //After ants have been murdered let the survivors step
            world.getAnts().forEach(Ant::step);

            redStatisticsTracker.update();
            blackStatisticsTracker.update();

            try { //Manually slow down simulation for display purposes
                if (speed < DEFAULT_MATCH_SPEED) Thread.sleep(100 / speed);
            } catch (InterruptedException ignore) {}
        }

        int redFood = redStatisticsTracker.getFoodInAntHill();
        int blackFood = blackStatisticsTracker.getFoodInAntHill();

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

    public ColonyStatisticsTracker redStatistics() {
        return redStatisticsTracker;
    }

    public ColonyStatisticsTracker blackStatistics() {
        return blackStatisticsTracker;
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
