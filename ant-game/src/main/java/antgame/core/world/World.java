package antgame.core.world;

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

}
