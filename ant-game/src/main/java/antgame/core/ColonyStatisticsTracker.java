package antgame.core;

import antgame.core.world.Cell;
import antgame.core.world.World;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Regan Ware
 */
public class ColonyStatisticsTracker {

    //The colony to track statistics for
    private final Colony myColony;

    //The world to track statistics in
    private final World context;

    //the cells that belong to my anthill
    private final Set<Cell> colonyCells;

    //the number of ants left alive on my team
    private int antsAlive;

    //The food in our ant hill
    private int foodInAntHill;

    //the number of our markingsMade on the ground
    private int markings;

    //the number of movements made
    private int movements;

    /**
     * Creates a new statistics tracker for the given world and colony.
     *
     * @param myColony the colony to track statistics for
     * @param context the world to track the statistics in
     */
    public ColonyStatisticsTracker(Colony myColony, World context) {
        this.myColony = myColony;
        this.context = context;
        this.colonyCells = new HashSet<>();

        Cell.Type myType =
                (myColony.getColour() == Colony.Colour.RED ? Cell.Type.ANTHILL_RED : Cell.Type.ANTHILL_BLACK);
        for (int x = 0; x < context.width(); ++x) {
            for (int y = 0; y < context.height(); ++y) {
                Cell cell = context.cell(x, y);
                if (cell.getType() == myType) {
                    colonyCells.add(cell);
                }
            }
        }

        update();
    }

    /**
     * The colony for which we are tracking statistics.
     *
     * @return the colony
     */
    public Colony colony() {
        return myColony;
    }

    /**
     * Updates the statistics held in this tracker.
     */
    public void update() {
        int tmpFoodInAntHill = 0;
        int tmpMarkings = 0;
        int tmpAntsAlive = 0;
        int tmpMovements = 0;
        for (Cell cell : colonyCells) {
            tmpFoodInAntHill += cell.getFoodAmount();
        }
        for (Ant ant : context.getAnts()) {
            if (ant.getColony().equals(myColony)) {
                ++tmpAntsAlive;
                tmpMovements += ant.successfulMovements();
                tmpMarkings += ant.markingsMade();
            }
        }
        this.foodInAntHill = tmpFoodInAntHill;
        this.markings = tmpMarkings;
        this.antsAlive = tmpAntsAlive;
        this.movements = tmpMovements;
    }

    /**
     * @return the number of ants left alive
     */
    public int numAliveAnts() {
        return antsAlive;
    }

    /**
     * @return the amount of food in the anthill
     */
    public int foodInAntHill() {
        return foodInAntHill;
    }

    /**
     * @return the number of markings made left by this colony
     */
    public int markingsMade() {
        return markings;
    }

    /**
     * @return the number of movements made by this colony
     */
    public int movementsMade() {
        return movements;
    }

}
