package antgame.core.world;

import antgame.core.Colony;

/**
 * Represents a cell marker left by an ant of a particular colony.
 *
 * @author Sam Marsh
 */
public class Marker {

    //the colony of the ant that this marker belongs to
    private final Colony.Colour colour;

    //the marker index, between 0 and 5
    private final int marker;

    /**
     * Creates a new marker.
     *
     * @param colour the colony of the ant creating the marker
     * @param marker the marker index, from 0..5
     */
    public Marker(Colony.Colour colour, int marker) {
        this.colour = colour;
        this.marker = marker;
        if (marker < 0 || marker > 5) {
            throw new IllegalArgumentException("marker not in range 0..5: " + marker);
        }
    }

    /**
     * @return the colony of the ant that the marker belongs to
     */
    public Colony.Colour getColour() {
        return colour;
    }

    /**
     * @return the marker identifier, in the range 0..5
     */
    public int getMarker() {
        return marker;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Marker marker1 = (Marker) o;
        return marker == marker1.marker && colour == marker1.colour;

    }

    @Override
    public int hashCode() {
        int result = colour != null ? colour.hashCode() : 0;
        result = 31 * result + marker;
        return result;
    }

    @Override
    public String toString() {
        return String.format("Marker{colony=%s,marker=%d}", colour, marker);
    }

}
