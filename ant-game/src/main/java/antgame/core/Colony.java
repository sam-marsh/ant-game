package antgame.core;

/**
 * @author Sam Marsh
 */
public class Colony {

    //the colour of the ants in the colony
    private final Colour colour;

    /**
     * Creates a new colony with the given colour.
     *
     * @param colour the colour of the ants
     */
    public Colony(Colour colour) {
        this.colour = colour;
    }

    /**
     * @return the colour of the colony's ants
     */
    public Colour getColour() {
        return colour;
    }

    /**
     * A representation of the two possible ant colours.
     */
    public enum Colour {

        RED,
        BLACK

    }

}
