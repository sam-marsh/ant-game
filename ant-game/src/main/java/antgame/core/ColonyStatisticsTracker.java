package antgame.core;

import antgame.core.world.Cell;
import antgame.core.world.World;

/**
 * @author Regan Ware
 */
public class ColonyStatisticsTracker {

    private final Colony myColony;
    private final World context;
    private final Cell.Type myHomeCellType;

    private int antsAlive;
    private int foodInAntHill;
    private int markings;

    public ColonyStatisticsTracker(Colony myColony, World context) {
        this.myColony = myColony;
        this.context = context;
        this.myHomeCellType =
                (myColony.getColour() == Colony.Colour.RED ? Cell.Type.ANTHILL_RED : Cell.Type.ANTHILL_BLACK);

        update();
    }

    public Colony colony() {
        return myColony;
    }

    public void update() {
        int tmpFoodInAntHill = 0;
        int tmpMarkings = 0;
        int tmpAntsAlive = (int) context.getAnts().stream().filter(a -> a.getColony() == myColony).count();
        for (int x = 0; x < context.width(); ++x) {
            for (int y = 0; y < context.height(); ++y) {
                Cell cell = context.cell(x, y);
                if (cell.getType() == myHomeCellType) {
                    tmpFoodInAntHill += cell.getFoodAmount();
                }
                tmpMarkings += cell.numMarkings(myColony);
            }
        }
        this.foodInAntHill = tmpFoodInAntHill;
        this.markings = tmpMarkings;
        this.antsAlive = tmpAntsAlive;
    }

    public int getNumAliveAnts() {
        return antsAlive;
    }

    public int getFoodInAntHill() {
        return foodInAntHill;
    }

    public int getMarkingsCount() {
        return markings;
    }

}
