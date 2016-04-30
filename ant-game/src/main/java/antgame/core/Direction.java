package antgame.core;

/**
 * A hexagonal direction in terms of which edge of the hexagon you are looking at if sitting inside the hexagon.
 *
 * @author Sam Marsh
 */
public enum Direction {

    EAST(0),
    SOUTH_EAST(1),
    SOUTH_WEST(2),
    WEST(3),
    NORTH_WEST(4),
    NORTH_EAST(5);

    //the identifier for the direction
    private final int id;

    /**
     * Creates a new direction.
     * @param id the identifier for the direction
     */
    Direction(int id) {
        this.id = id;
    }

    /**
     * @return an integer uniquely representing the direction
     */
    public int id() {
        return id;
    }

    /**
     * @return the direction that is obtained by turning right to the next edge (clockwise)
     */
    public Direction right() {
        return rotate(1);
    }

    /**
     * @return the direction that is obtained by turning left to the next edge (anticlockwise)
     */
    public Direction left() {
        return rotate(-1);
    }

    /**
     * Rotates the direction by a given number of edges in a clockwise direction.
     *
     * @param offset the number of edges to rotate clockwise
     * @return the direction obtained by the rotation
     */
    private Direction rotate(int offset) {
        for (Direction d : values()) {
            if (d.id == Math.floorMod(id + offset, values().length)) {
                return d;
            }
        }
        throw new AssertionError("internal error: calculating direction rotate");
    }

}
