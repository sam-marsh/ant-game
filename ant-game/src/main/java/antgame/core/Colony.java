package antgame.core;

import antgame.core.brain.Brain;

/**
 * Represents a colony of ants, with their associated colour and ant-brain.
 *
 * @author Sam Marsh
 */
public class Colony {

    //the colour of the ants in the colony
    private final Colour colour;
    private final Brain brain;

    /**
     * Creates a new colony with the given colour.
     *
     * @param colour the colour of the ants
     * @param brain the ant brain associated with this team
     */
    public Colony(Colour colour, Brain brain) {
        this.colour = colour;
        this.brain = brain;
    }

    /**
     * @return the colour of the colony's ants
     */
    public Colour getColour() {
        return colour;
    }

    /**
     * @return the brain which 'controls' all this colony's ants
     */
    public Brain getBrain() {
        return brain;
    }

    /**
     * A representation of the two possible ant colours.
     */
    public enum Colour {

        RED,
        BLACK

    }

}
