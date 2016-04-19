package antgame.core.world;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Sam Marsh
 */
public class Cell {

    //holds the chemical markers in the cell
    private Set<Marker> markers;

    public Cell() {
        this.markers = new HashSet<>();
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

}
