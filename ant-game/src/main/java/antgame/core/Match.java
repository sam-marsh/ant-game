package antgame.core;

import antgame.core.Colony.Colour;
import antgame.core.world.*;
import antgame.core.world.Cell.Type;

import java.util.LinkedList;
import java.util.List;
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
    //The container for the outcome of the match when it finishes
    private MatchOutcome outcome;


    /**Statistics variables**/
    //The ant hills belonging to the red colony in the match
    List<Cell> redAntHills = new LinkedList<>();
    //The ant hills belonging to the black colony in the match
    List<Cell> blackAntHills = new LinkedList<>();
    //The number of red ant deaths
    int murderedRedAnts;
    //The number of black ant deaths
    int murderedBlackAnts;


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
     */
    public void run(int rounds, int speed)
    {
        //Used to determine the winner of the match at the end
        int redFood;
        int blackFood;

        //Populate the world with ants, using the appropriate player to form and supply new colonies
        world.spawnAnts(new Colony(Colour.RED, playerRed.getBrain()), new Colony(Colour.BLACK, playerBlack.getBrain()));


        //Loop through the entire world, x and y going from 0 to the world's width and height respectively
        for (int x = 0; x < world.width(); x++)
        {
            for (int y = 0; y < world.height(); y++)
            {
                //If the cell is an anthill, add it to the appropriate list of ant hills for stats and determining the winner
                Cell curr = world.cell(x, y);
                if (world.cell(x, y).getType() == Type.ANTHILL_RED)
                {
                    redAntHills.add(curr);
                }
                else if (world.cell(x, y).getType() == Type.ANTHILL_BLACK)
                {
                    blackAntHills.add(curr);
                }
            }
        }

        //Loop for the supplied number of rounds
        for (int i = 0; i < rounds; i++)
        {
            //Loop through the ants in the world (stream to handle dynamic removal) filtering ants that are surrounded
            for (Ant ant : world.getAnts())
            {
                //Murder each of the surrounded ants
                if (ant.surrounded())
                {
                    statsIterateAntDeath(ant.getColony().getColour());
                    world.murder(ant);
                }
            }


            //After ants have been murdered let the survivors step
            world.getAnts().forEach(Ant::step);

            try { //Manually slow down simulation for display purposes
                Thread.sleep(100 / speed);
            } catch (InterruptedException ignore) {}
        }


        //Using statsGetFood
        redFood = statsGetFood(Colour.RED);
        blackFood = statsGetFood(Colour.BLACK);

        //If there's more red food, the red player wins
        if (redFood > statsGetFood(Colour.BLACK)) {
            outcome = new MatchOutcome(
                    new PlayerOutcome(playerRed, PlayerOutcome.Result.WIN, this),
                    new PlayerOutcome(playerBlack, PlayerOutcome.Result.LOSS, this)
            );
        //If there's more black food, the black player wins
        } else if (blackFood > statsGetFood(Colour.RED)) {
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
    public boolean finished() {return outcome != null;}

    /**
     * Gets the outcome of the match
     * @return The MatchOutcome produced at the end of the match
     */
    public MatchOutcome getOutcome() {return outcome;}

    /**
     * Gets the match's World
     * @return The world this match takes place in
     */
    public World world() {return world;}

    /**
     * Returns the amount of food in the supplied colours Ant Hills
     * @param colour The colour of which team's hills to check
     * @return The amount of food in the team's ant hills
     */
    public int statsGetFood(Colour colour)
    {
        List<Cell> hillsToCheck = new LinkedList<>();
        int food = 0;
        if (colour == Colour.RED)
        {
            hillsToCheck = redAntHills;
        }
        else if (colour == Colour.BLACK)
        {
            hillsToCheck = blackAntHills;
        }

        for (Cell cell : hillsToCheck)
        {
            food += cell.getFoodAmount();
        }

        return food;
    }

    /**
     * Iterates the appropriate murdered ants stat based on the input colour
     * @param colour The colour of the ant that was murdered
     */
    private void statsIterateAntDeath(Colour colour)
    {
        if (colour == Colour.RED)
        {
            murderedRedAnts++;
        }
        else if (colour == Colour.BLACK)
        {
            murderedBlackAnts++;
        }
    }

    /**
     * Gets the number of ant deaths on the supplied colour's side
     * @param colour The team colour to check for
     * @return The number of ant deaths of that colour
     */
    public int statsGetAntDeaths(Colour colour)
    {
        if (colour == Colour.RED)
        {
            return murderedRedAnts;
        }
        else
        {
            return murderedBlackAnts;
        }
    }

    /**
     * Gets the number of ants remaining of the supplied colour
     * @param colour The team colour to check for
     * @return The number of remaining ants of that colour
     */
    public int statsGetRemainingAnts(Colour colour)
    {
        if (colour == Colour.RED)
        {
            return redAntHills.size() - murderedRedAnts;
        }
        else
        {
            return blackAntHills.size() - murderedBlackAnts;
        }
    }

    /**
     * Gets the number of movements made by the ants of the supplied colour
     * @param colour The colour of ants to check for
     * @return The number of movements made by ants of that colour
     */
    public int statsGetMovements(Colour colour)
    {
        int movements = 0;
        for (Ant ant : world.getAnts())
        {
            if (ant.getColony().getColour() == colour)
            {
                movements += ant.successfulMovements();
            }
        }

        return movements;
    }

    /**
     * Gets the number of markings left by ants of the supplied colour
     * @param colour The colour of ants to check for
     * @return The number of markings left by ants of that colour
     */
    public int statsGetMarkings(Colour colour)
    {
        int markings = 0;
        for (Ant ant : world.getAnts())
        {
            if (ant.getColony().getColour() == colour)
            {
                markings += ant.markings();
            }
        }

        return markings;
    }
}
