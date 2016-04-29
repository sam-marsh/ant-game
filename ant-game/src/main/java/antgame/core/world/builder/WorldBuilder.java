package antgame.core.world.builder;

import antgame.core.util.RandomNumberGenerator;
import antgame.core.world.Cell;
import antgame.core.world.World;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author Sam Marsh
 */
public class WorldBuilder {

    //the size of the contest world (both width and height)
    private static final int CONTEST_WORLD_SIZE = 150;

    //the size of the hexagonal anthills (number of cells per hexagon edge)
    private static final int CONTEST_ANTHILL_SIZE = 7;

    //the number of rocks to place in the contest world
    private static final int CONTEST_NUMBER_ROCKS = 14;

    //the number of 'blobs' of food to place in the world
    private static final int CONTEST_NUMBER_FOOD_BLOBS = 11;

    //the size of each food blob rectangle (both width and height)
    private static final int CONTEST_FOOD_BLOB_SIZE = 5;

    //the number of food particles per food-cell
    private static final int CONTEST_FOOD_COUNT_PER_CELL = 5;

    //the width of this world
    private final int width;

    //the height of this world
    private final int height;

    //the cell array being built up
    private final Cell[][] cells;

    /**
     * Creates a new world builder.
     *
     * @param width the width of the world to build
     * @param height the height of the world to build
     */
    private WorldBuilder(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[width][height];
        init();
    }

    /**
     * Initialises the world by setting all edge cells to be rocky.
     */
    private void init() {
        for (int x = 0; x < width; ++x) {
            cells[x][0] = new Cell(Cell.Type.ROCK, x, 0);
            cells[x][height - 1] = new Cell(Cell.Type.ROCK, x, height - 1);
        }
        for (int y = 0; y < height; ++y) {
            cells[0][y] = new Cell(Cell.Type.ROCK, 0, y);
            cells[width - 1][y] = new Cell(Cell.Type.ROCK, width - 1, y);
        }
    }

    /**
     * Convenience method for checking if an integer is even.
     *
     * @param a the integer to check
     * @return {@code true} if a is even, otherwise {@code false}
     */
    private boolean even(int a) {
        return a % 2 == 0;
    }

    /**
     * Fills all remaining unused (null) cells in the cell array to be empty (clear) cells.
     */
    private void fill() {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (cells[x][y] == null) {
                    cells[x][y] = new Cell(Cell.Type.CLEAR, x, y);
                }
            }
        }
    }

    /**
     * A class for holding an (x, y) pair.
     */
    private class Position {

        //the x position in the array
        private int x;

        //the y position in the array
        private int y;

        /**
         * Creates a new position.
         *
         * @param x the x position in the array
         * @param y the y position in the array
         */
        private Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Position position = (Position) o;

            return x == position.x && y == position.y;

        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }

    }

    /**
     * Gets the adjacent position in a particular direction (treating the positions
     * as hexagonal coordinates).
     *
     * <ul>
     *     <li>d = 0: to the right</li>
     *     <li>d = 1: right-down</li>
     *     <li>d = 2: left-down</li>
     *     <li>d = 3: left</li>
     *     <li>d = 4: left-up</li>
     *     <li>d = 5: right-up</li>
     * </ul>
     *
     * @param position the position to find the adjacent position for
     * @param dir the direction as explained above
     * @return the adjacent position in the given direction
     */
    private Position adjacent(Position position, int dir) {
        int x = position.x;
        int y = position.y;
        switch (dir) {
            case 0:
                return new Position(x + 1, y);
            case 1:
                return new Position(even(y) ? x : x + 1, y + 1);
            case 2:
                return new Position(even(y) ? x - 1 : x, y + 1);
            case 3:
                return new Position(x - 1, y);
            case 4:
                return new Position(even(y) ? x - 1 : x, y - 1);
            case 5:
                return new Position(even(y) ? x : x + 1, y - 1);
            default:
                throw new AssertionError("internal error: d = " + dir);
        }
    }

    /**
     * Places a rock in a particular location.
     *
     * @param x the x location to place the rock
     * @param y the y location to place the rock
     * @throws WorldBuilderException if something is already at or adjacent to that location
     */
    private void rock(int x, int y) throws WorldBuilderException {
        validate(x, y);
        checkAdjacent(new HashSet<Position>() {
            {
                add(new Position(x, y));
            }
        });
        cells[x][y] = new Cell(Cell.Type.ROCK, x, y);
    }

    /**
     * Places a rectangle into the world.
     *
     * @param initialX the x component of the top-left coordinate of the rectangle
     * @param initialY the y component of the top-left coordinate of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @param dir the orientation of the rectangle - either 2 for a rectangle which slopes in the left-downward
     *            direction, or 4 for a rectangle which slopes in the right-downward direction)
     * @param type the type of cell to create at each position of the rectangle
     * @param consumer will be called by this method for each cell touched in the rectangle. can be used to modify
     *                 the rectangle's cells, such as for adding food
     * @throws WorldBuilderException if the rectangle is adjacent to or overlapping another object
     */
    private void rectangle(int initialX, int initialY, int width, int height, int dir,
                           Cell.Type type, Consumer<Cell> consumer) throws WorldBuilderException {

        //keep track of all cells of the rectangle
        Map<Position, Cell> map = new HashMap<>();

        //iterate from top-left x coordinate to top-right
        for (int x = initialX; x < initialX + width; ++x) {

            //create the initial top-left position
            Position curr = new Position(x, initialY);

            //loop once for each y coordinate
            for (int i = 0; i < height; ++i) {
                //check that the position is not already filled
                validate(curr.x, curr.y);
                //stick the new cell in the map
                map.put(new Position(curr.x, curr.y), new Cell(type, curr.x, curr.y));
                //move to the adjacent cell
                curr = adjacent(curr, dir);
            }
        }

        //ensure the rectangle is adjacent to nothing
        checkAdjacent(map.keySet());

        //add all cells to the world
        for (Map.Entry<Position, Cell> entry : map.entrySet()) {
            cells[entry.getKey().x][entry.getKey().y] = entry.getValue();
            consumer.accept(entry.getValue());
        }
    }

    /**
     * Places a hexagon into the world.
     *
     * @param initialX the left-most cell's x coordinate
     * @param initialY the left-most cell's y coordinate
     * @param edgeSize the number of cells per hexagonal edge
     * @param type the type of cell to fill the hexagon with
     * @throws WorldBuilderException if the hexagon is adjacent to or overlapping another object
     */
    private void hexagon(int initialX, int initialY, int edgeSize,
                         Cell.Type type) throws WorldBuilderException {

        //keep track of all cells of the hexagon
        Map<Position, Cell> map = new HashMap<>();

        //first, fill in the top half of the hexagon
        Position curr = new Position(initialX, initialY);

        for (int i = 0; i < edgeSize; ++i) {

            //the number of cells in a row is given by 2 * size - offset - 1
            int currentSize = 2 * edgeSize - i - 1;

            //loop through all cells in the row and add them to the map
            for (int x = curr.x; x < curr.x + currentSize; ++x) {
                validate(x, curr.y);
                map.put(new Position(x, curr.y), new Cell(type, x, curr.y));
            }

            //move to the next cell in the up-right direction
            curr = adjacent(curr, 5);
        }

        //repeat for the lower half of the hexagon
        curr = adjacent(new Position(initialX, initialY), 1);

        for (int i = 1; i < edgeSize; ++i) {

            //as above, the number of cells in a row is given by 2 * size - offset - 1
            int currentSize = 2 * edgeSize - i - 1;

            //loop through all cells in the row and add them to the map
            for (int x = curr.x; x < curr.x + currentSize; ++x) {
                validate(x, curr.y);
                map.put(new Position(x, curr.y), new Cell(type, x, curr.y));
            }

            //move to the next cell in the down-right direction
            curr = adjacent(curr, 1);
        }

        //ensure the hexagon is adjacent to nothing
        checkAdjacent(map.keySet());

        //add all cells to the world
        for (Map.Entry<Position, Cell> entry : map.entrySet()) {
            cells[entry.getKey().x][entry.getKey().y] = entry.getValue();
        }

    }

    /**
     * Checks that a set of cells representing an object is adjacent to nothing.
     *
     * @param set a set of cells
     * @throws WorldBuilderException if the cells are adjacent to another object
     */
    private void checkAdjacent(Set<Position> set) throws WorldBuilderException {
        //loop over every cell in the object
        for (Position position : set) {
            //loop over every direction
            for (int i = 0; i < 6; ++i) {
                //get the adjacent cell in this direction
                Position adjacent = adjacent(position, i);
                //if it's non-null and not a part of this object, it is adjacent to something
                if (cells[adjacent.x][adjacent.y] != null) {
                    if (!set.contains(new Position(adjacent.x, adjacent.y))) {
                        throw new WorldBuilderException("adjacent to something else");
                    }
                }
            }
        }
    }

    /**
     * Convenient exception class for identifying when objects are overlapping or adjacent.
     */
    private class WorldBuilderException extends Exception {
        WorldBuilderException(String msg) { super(msg); }
    }

    /**
     * Checks that a cell does not overlap another.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @throws WorldBuilderException if the cell does indeed overlap another
     */
    private void validate(int x, int y) throws WorldBuilderException {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new WorldBuilderException("not in bounds: (" + x + "," + y + ")");
        }
        if (cells[x][y] != null) {
            throw new WorldBuilderException("cell overlapping another: (" + x + "," + y + ")");
        }
    }

    /**
     * Just wraps the cell array with a world object.
     *
     * @return a world consisting of the cells built
     */
    private World build() {
        return new World(cells);
    }

    /**
     * Creates a contest-valid world. A contest world is specified by:
     *
     * <ul>
     *     <li>150x150 size</li>
     *     <li>rocky edge cells</li>
     *     <li>2 anthills - hexagons of edge size 7</li>
     *     <li>14 rocks</li>
     *     <li>11 blobs of food, where a blob is a 5x5 random orientation rectangle and each cell
     *     in the food blob contains 5 food particles</li>
     *     <li>no two objects as specified above will overlap or be directly adjacent</li>
     * </ul>
     *
     * @return a world with
     */
    public static World generateContestWorld() {
        //create a world builder object
        WorldBuilder builder = new WorldBuilder(CONTEST_WORLD_SIZE, CONTEST_WORLD_SIZE);

        //set up our rng
        RandomNumberGenerator rng = new RandomNumberGenerator();

        //loop continuously trying to add a red anthill until we succeed
        while (true) {
            try {
                builder.hexagon(
                        rng.next(CONTEST_WORLD_SIZE),
                        rng.next(CONTEST_WORLD_SIZE),
                        CONTEST_ANTHILL_SIZE,
                        Cell.Type.ANTHILL_RED
                );
            } catch (WorldBuilderException e) {
                //hexagon was overlapping or adjacent to something else, try again with
                // new random numbers
                continue;
            }
            //success, break out
            break;
        }

        //loop continuously trying to add a black anthill until we succeed
        while (true) {
            try {
                builder.hexagon(
                        rng.next(CONTEST_WORLD_SIZE),
                        rng.next(CONTEST_WORLD_SIZE),
                        CONTEST_ANTHILL_SIZE,
                        Cell.Type.ANTHILL_BLACK
                );
            } catch (WorldBuilderException e) {
                //hexagon was overlapping or adjacent to something else, try again with
                // new random numbers
                continue;
            }
            //success, break out
            break;
        }

        //add the food blobs now
        for (int i = 0; i < CONTEST_NUMBER_FOOD_BLOBS; ++i) {
            while (true) {
                try {
                    builder.rectangle(
                            rng.next(CONTEST_WORLD_SIZE),
                            rng.next(CONTEST_WORLD_SIZE),
                            CONTEST_FOOD_BLOB_SIZE,
                            CONTEST_FOOD_BLOB_SIZE,
                            rng.next(2) == 1 ? 2 : 4,
                            Cell.Type.CLEAR,
                            c -> c.setFood(CONTEST_FOOD_COUNT_PER_CELL)
                    );
                } catch (WorldBuilderException e) {
                    continue;
                }
                break;
            }
        }

        //next add the rocks
        for (int i = 0; i < CONTEST_NUMBER_ROCKS; ++i) {
            while (true) {
                try {
                    builder.rock(rng.next(CONTEST_WORLD_SIZE), rng.next(CONTEST_WORLD_SIZE));
                } catch (WorldBuilderException e) {
                    continue;
                }
                break;
            }
        }

        //fill the rest of the world with empty cells
        builder.fill();

        //return the created world
        return builder.build();
    }

}
