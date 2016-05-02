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
    private final Set<Marker> redMarkers;
    private final Set<Marker> blackMarkers;

    //holds the current ant - or null if none
    private Ant ant;

    //how much food is in the cell
    private int food;

    //the coordinate x, from the left of the world
    private final int x;

    //the coordinate y, from the top of the world
    private final int y;

    /**
     * Creates a new cell of the given type.
     *
     * @param type the type of cell - rocky or clear
     * @param x the cell's x coordinate
     * @param y the cell's y coordinate
     */
    public Cell(Type type, int x, int y) {
        this.type = type;
        this.redMarkers = new HashSet<>();
        this.blackMarkers = new HashSet<>();
        this.ant = null;
        this.food = 0;
        this.x = x;
        this.y = y;
    }

    /**
     * Adds a marker to this cell.
     *
     * @param marker the marker to add
     * @return true if the cell did not already contain the marker
     */
    public boolean mark(Colony colony, Marker marker) {
        if (colony.getColour() == Colony.Colour.RED) {
            return redMarkers.add(marker);
        } else {
            return blackMarkers.add(marker);
        }
    }

    /**
     * Removes a marker from this cell.
     *
     * @param marker the marker to remove
     * @return true if the cell contained the marker
     */
    public boolean unmark(Colony colony, Marker marker) {
        if (colony.getColour() == Colony.Colour.RED) {
            return redMarkers.remove(marker);
        } else {
            return blackMarkers.remove(marker);
        }
    }

    /**
     * Checks if the cell is marked the foe. The parameter is the colony that is asking.
     *
     * @param asking the colony asking
     * @return true if marked by the enemy
     */
    public boolean foeMarked(Colony asking) {
        if (asking.getColour() == Colony.Colour.RED) {
            return !blackMarkers.isEmpty();
        } else {
            return redMarkers.isEmpty();
        }
    }

    /**
     * Checks if this cell contains a particular marker.
     *
     * @param marker the marker to check for
     * @return true if the cell contains the marker
     */
    public boolean marked(Colony colony, Marker marker) {
        if (colony.getColour() == Colony.Colour.RED) {
            return redMarkers.contains(marker);
        } else {
            return blackMarkers.contains(marker);
        }
    }

    /**
     * @return whether this cell contains an ant
     */
    public boolean hasAnt() {
        return ant != null;
    }

    /**
     * Called by ants to check if this cell is available to move in to.
     *
     * @return true if the cell is free (clear of ants and other obstacles), otherwise false
     */
    public boolean free() {
        return type != Type.ROCK && !hasAnt();
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
     * Puts an ant in this cell.
     * @param ant the ant to place here
     */
    public void setAnt(Ant ant) {
        if (hasAnt()) {
            throw new AssertionError("ant already here");
        }
        this.ant = ant;
    }

    /**
     * Removes the current ant from this cell.
     */
    public void removeAnt() {
        if (!hasAnt()) {
            throw new AssertionError("no ant to remove");
        }
        ant = null;
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
     * @return the cell's x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * @return the cell's y coordinate
     */
    public int getY() {
        return y;
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
