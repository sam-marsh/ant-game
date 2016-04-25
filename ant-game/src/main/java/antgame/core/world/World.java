package antgame.core.world;

import antgame.core.Colony;
import antgame.core.brain.instruction.Condition;

/**
 * Represents a game world.
 *
 * @author Sam Marsh
 */
public class World {

    //the cells that the world contains
    private final Cell[][] cells;

    /**
     * Creates a new world from the given cell array.
     *
     * @param cells an array describing the cells that make up the world
     */
    public World(Cell[][] cells) {
        this.cells = cells;
    }

    /**
     * Checks if a given cell satisfies the given condition, with the question asked
     * by an ant of a particular {@link Colony}
     *
     * @param condition the condition to check for
     * @param asking the colony that is 'asking the question'
     * @param cell the cell to check the condition on
     * @return true if the condition holds for the cell, otherwise false
     */
    public boolean check(Condition condition, Colony asking, Cell cell) {
        switch (condition.getType()) {
            case FRIEND:
                return cell.hasAnt() && cell.getAnt().getColony().equals(asking);
            case FOE:
                return cell.hasAnt() && !cell.getAnt().getColony().equals(asking);
            case FRIEND_WITH_FOOD:
                return cell.hasAnt() && cell.getAnt().getColony().equals(asking) && cell.getAnt().hasFood();
            case FOE_WITH_FOOD:
                return cell.hasAnt() && !cell.getAnt().getColony().equals(asking) && cell.getAnt().hasFood();
            case FOOD:
                return cell.hasFood();
            case ROCK:
                return cell.getType() == Cell.Type.ROCK;
            case MARKER:
                return cell.marked(condition.getMarker());
            case FOE_MARKER:
                return cell.foeMarked(asking);
            case HOME:
                if (asking.getColour() == Colony.Colour.RED) {
                    return cell.getType() == Cell.Type.ANTHILL_RED;
                }
                return cell.getType() == Cell.Type.ANTHILL_BLACK;
            case FOE_HOME:
                if (asking.getColour() == Colony.Colour.RED) {
                    return cell.getType() == Cell.Type.ANTHILL_BLACK;
                }
                return cell.getType() == Cell.Type.ANTHILL_RED;
            default:
                throw new AssertionError("internal error: unimplemented condition check");
        }
    }

}
