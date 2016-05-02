package antgame.core.world;

import antgame.core.Colony;

/**
 * Represents a cell marker left by an ant of a particular colony.
 *
 * @author Sam Marsh
 */
public class Marker {

    //the marker index, between 0 and 5
    private final int marker;

    /**
     * Creates a new marker.
     *
     * @param marker the marker index, from 0..5
     */
    public Marker(int marker) {
        this.marker = marker;
        if (marker < 0 || marker > 5) {
            throw new IllegalArgumentException("marker not in range 0..5: " + marker);
        }
    }

    /**
     * @return the marker identifier, in the range 0..5
     */
    public int getID() {
        return marker;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Marker marker1 = (Marker) o;

        return marker == marker1.marker;

    }

    @Override
    public int hashCode() {
        return marker;
    }

    @Override
    public String toString() {
        return String.format("Marker{id=%d}", marker);
    }

}
