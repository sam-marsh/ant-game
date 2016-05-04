package antgame.core;

import antgame.core.Colony.Colour;
import antgame.core.world.*;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * A match between two supplied players. Once constructed call {@link #run(int, int)}, which iterates
 * continually until the match is complete. Then the outcome of the match can be obtained using
 * {@link #outcome()}.
 *
 * @author Regan Ware
 */
public class Match {

    //number of iterations
    public static int NUM_ROUNDS = 300000;

    //max playback speed of a match
    public static int MAXIMUM_MATCH_SPEED = 100;

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

    /**
     * @return the player that has the red colony in this match
     */
    public Player redPlayer() {
        return playerRed;
    }

    /**
     * @return the player that has the black colony in this match
     */
    public Player blackPlayer() {
        return playerBlack;
    }

    /**
     * Runs the match determining and returning the winner based on which colony has more food in their ant hills
     *
     * @param rounds The number of rounds to run the match for (Usually NUM_ROUNDS at 300000)
     * @param speed the speed to run the match - from 1 to 100 (or more to not pause between steps at all)
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
                if (speed < MAXIMUM_MATCH_SPEED) Thread.sleep(100 / speed);
            } catch (InterruptedException ignore) {}
        }

        int redFood = redStatisticsTracker.foodInAntHill();
        int blackFood = blackStatisticsTracker.foodInAntHill();

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
     * @return current match statistics for the red player
     */
    public ColonyStatisticsTracker redStatistics() {
        return redStatisticsTracker;
    }

    /**
     * @return current match statistics for the black player
     */
    public ColonyStatisticsTracker blackStatistics() {
        return blackStatisticsTracker;
    }

    /**
     * Checks if the match has finished or not.
     *
     * @return True if the match is finished, false otherwise.
     */
    public boolean finished() {
        return outcome != null;
    }

    /**
     * Gets the outcome of the match.
     *
     * @return The {@link MatchOutcome} produced at the end of the match
     */
    public MatchOutcome outcome() {
        return outcome;
    }

    /**
     * Gets the match's {@link World}.
     *
     * @return The world this match takes place in
     */
    public World world() {
        return world;
    }
}
