package antgame.core.world;

import antgame.core.Colony;

/**
 * Represents a cell marker left by an ant of a particular colony.
 *
 * @author Sam Marsh
 */
public class Marker {

    //the colony of the ant that this marker belongs to
    private final Colony colony;

    //the marker index, between 0 and 5
    private final int marker;

    /**
     * Creates a new marker.
     *
     * @param colony the colony of the ant creating the marker
     * @param marker the marker index, from 0..5
     */
    public Marker(Colony colony, int marker) {
        this.colony = colony;
        this.marker = marker;
        if (marker < 0 || marker > 5) {
            throw new IllegalArgumentException("marker not in range 0..5: " + marker);
        }
    }

    /**
     * @return the colony of the ant that the marker belongs to
     */
    public Colony getColony() {
        return colony;
    }

    /**
     * @return the marker identifier, in the range 0..5
     */
    public int getMarker() {
        return marker;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Marker && colony.equals(((Marker) o).colony) && marker == ((Marker) o).marker;
    }

    @Override
    public String toString() {
        return String.format("Marker{colony=%s,marker=%d}", colony, marker);
    }

}
