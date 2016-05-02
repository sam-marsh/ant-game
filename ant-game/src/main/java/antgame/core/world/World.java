package antgame.core.world;

import antgame.core.Ant;
import antgame.core.Colony;
import antgame.core.Direction;
import antgame.core.brain.instruction.Condition;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a game world.
 *
 * @author Sam Marsh
 */
public class World {

    //the cells that the world contains
    private final Cell[][] cells;

    //keep track of the ants
    private final List<Ant> ants;

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
        this.ants = new LinkedList<>();
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public Cell cell(int x, int y) {
        return cells[x][y]; //todo check bounds
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
                return cell.marked(asking, condition.getMarker());
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
     * Spawns the ants of both colonies, with one ant being placed on each anthill cell of the associated team.
     *
     * @param red the red colony
     * @param black the black colony
     */
    public void spawnAnts(Colony red, Colony black) {
        if (red.getColour() != Colony.Colour.RED) throw new IllegalArgumentException("red colony not red");
        if (black.getColour() != Colony.Colour.BLACK) throw new IllegalArgumentException("black colony not black");
        if (!ants.isEmpty()) throw new IllegalStateException("ants already spawned");

        int id = 0;
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                if (cells[x][y].getType() == Cell.Type.ANTHILL_RED) {
                    ants.add(new Ant(id, red, this, cells[x][y]));
                    ++id;
                } else if (cells[x][y].getType() == Cell.Type.ANTHILL_BLACK) {
                    ants.add(new Ant(id, black, this, cells[x][y]));
                    ++id;
                }
            }
        }

    }

    /**
     * @return an (unmodifiable) view of the ants in the world in ascending order of id
     */
    public List<Ant> getAnts() {
        return Collections.unmodifiableList(ants);
    }

    /**
     * Kills an ant and removes it from the game, also dropping 3 food where it existed.
     *
     * @param ant the ant to kill
     */
    public void murder(Ant ant) {
        //remove from the list
        ants.remove(ant);
        ant.getCell().removeAnt();
        ant.getCell().setFood(ant.getCell().getFoodAmount() + 3);
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
        return a % 2 == 1;
    }

}
