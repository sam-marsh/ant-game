package antgame.core.world;

import antgame.core.Ant;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Sam Marsh
 */
public class Cell {

    private final Type type;

    //holds the chemical markers in the cell
    private final Set<Marker> markers;
    private Ant ant;
    private int food;

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
     * Checks if this cell contains a particular marker.
     *
     * @param marker the marker to check for
     * @return true if the cell contains the marker
     */
    public boolean marked(Marker marker) {
        return markers.contains(marker);
    }

    public boolean hasAnt() {
        return ant != null;
    }

    public Ant getAnt() {
        return ant;
    }

    public boolean hasFood() {
        return food > 0;
    }

    public int getFoodAmount() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        CLEAR,
        ROCK,
        ANTHILL_RED,
        ANTHILL_BLACK
    }

}
