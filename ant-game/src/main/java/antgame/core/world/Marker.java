package antgame.core.world;

import antgame.core.Colony;

/**
 * @author Sam Marsh
 */
public class Marker {

    private final Colony colony;
    private final int marker;

    public Marker(Colony colony, int marker) {
        this.colony = colony;
        this.marker = marker;
    }

    public Colony getColony() {
        return colony;
    }

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
