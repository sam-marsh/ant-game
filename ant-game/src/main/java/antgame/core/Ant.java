package antgame.core;

/**
 * @author Sam Marsh
 */
public class Ant {

    private final Colony colony;

    /**
     * Creates a new ant belonging to the specified colony.
     *
     * @param colony the colony to which the ant belongs
     */
    public Ant(Colony colony) {
        this.colony = colony;
    }

    /**
     * @return the colony of the ant
     */
    public Colony getColony() {
        return colony;
    }

}
