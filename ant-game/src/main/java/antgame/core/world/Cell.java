package antgame.core.world;

import antgame.core.Ant;
import antgame.core.Colony;

import java.util.HashSet;
import java.util.Set;

/**
 * A representation of a single cell in the ant-world.
 *
 * @author Sam Marsh
 */
public class Cell {

    //rocky or clear or anthill?
    private final Type type;

    //holds the chemical markers in the cell
    private final Set<Marker> markers;

    //holds the current ant - or null if none
    private Ant ant;

    //how much food is in the cell
    private int food;

    /**
     * Creates a new cell of the given type.
     *
     * @param type the type of cell - rocky or clear
     */
    public Cell(Type type) {
        this.type = type;
        this.markers = new HashSet<>();
        this.ant = null;
        this.food = 0;
    }

    /**
     * Adds a marker to this cell.
     *
     * @param marker the marker to add
     * @return true if the cell did not already contain the marker
     */
    public boolean mark(Marker marker) {
        return markers.add(marker);
    }

    /**
     * Removes a marker from this cell.
     *
     * @param marker the marker to remove
     * @return true if the cell contained the marker
     */
    public boolean unmark(Marker marker) {
        return markers.remove(marker);
    }

    /**
     * Checks if the cell is marked the foe. The parameter is the colony that is asking.
     *
     * @param asking the colony asking
     * @return true if marked by the enemy
     */
    public boolean foeMarked(Colony asking) {
        return markers.parallelStream().anyMatch(m -> !m.getColony().equals(asking));
    }

    /**
     * Checks if this cell contains a particular marker.
     *
     * @param marker the marker to check for
     * @return true if the cell contains the marker
     */
    public boolean marked(Marker marker) {
        return markers.contains(marker);
    }

    /**
     * @return whether this cell contains an ant
     */
    public boolean hasAnt() {
        return ant != null;
    }

    /**
     * @return the ant contained in the cell
     */
    public Ant getAnt() {
        if (ant == null) {
            throw new NullPointerException("no ant in cell");
        }
        return ant;
    }

    /**
     * @return whether this cell contains any food
     */
    public boolean hasFood() {
        return food > 0;
    }

    /**
     * @return how much food the cell contains
     */
    public int getFoodAmount() {
        return food;
    }

    /**
     * Sets the amount of food in the cell.
     *
     * @param food the number of food particles
     */
    public void setFood(int food) {
        this.food = food;
    }

    /**
     * @return the type of cell - rocky, clear, anthill
     */
    public Type getType() {
        return type;
    }

    /**
     * Represents a type of cell.
     */
    public enum Type {

        CLEAR,
        ROCK,
        ANTHILL_RED,
        ANTHILL_BLACK

    }

}
