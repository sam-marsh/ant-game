package antgame.core.world;

import antgame.core.Colony;
import antgame.core.Direction;
import antgame.core.brain.instruction.Condition;

/**
 * Represents a game world.
 *
 * @author Sam Marsh
 */
public class World {

    //the cells that the world contains
    private final Cell[][] cells;

    private final int width;
    private final int height;

    /**
     * Creates a new world from the given cell array.
     *
     * @param cells an array describing the cells that make up the world
     */
    public World(Cell[][] cells) {
        this.cells = cells;
        this.width = cells.length;
        this.height = cells[0].length;
    }

    /**
     * Finds the cell adjacent to a given cell, in a given direction.
     *
     * @param cell the given cell
     * @param direction the direction to move
     * @return the adjacent cell when moving one step in the given direction
     */
    public Cell adjacent(Cell cell, Direction direction) {
        int x = cell.getX();
        int y = cell.getY();
        switch (direction) {
            case EAST:
                return cells[x + 1][y];
            case SOUTH_EAST:
                return even(y) ? cells[x][y + 1] : cells[x + 1][y + 1];
            case SOUTH_WEST:
                return even(y) ? cells[x - 1][y + 1] : cells[x][y + 1];
            case WEST:
                return cells[x - 1][y];
            case NORTH_WEST:
                return even(y) ? cells[x - 1][y - 1] : cells[x][y - 1];
            case NORTH_EAST:
                return even(y) ? cells[x][y - 1] : cells[x + 1][y - 1];
            default:
                throw new AssertionError("internal error: direction not implemented");
        }
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

    /**
     * Prints a world. Currently for debugging.
     * TODO: maybe move this to a better location.
     */
    public void print() {
        for (int y = 0; y < height; ++y) {
            if (y % 2 == 1) System.out.print(' ');
            for (int x = 0; x < width; ++x) {
                Cell cell = cells[x][y];
                switch (cell.getType()) {
                    case CLEAR:
                        if (cell.hasFood()) {
                            System.out.print(cell.getFoodAmount());
                        } else {
                            System.out.print('.');
                        }
                        break;
                    case ROCK:
                        System.out.print('#');
                        break;
                    case ANTHILL_RED:
                        System.out.print('+');
                        break;
                    case ANTHILL_BLACK:
                        System.out.print('-');
                        break;
                }
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    private boolean even(int a) {
        return a % 2 == 0;
    }

}
