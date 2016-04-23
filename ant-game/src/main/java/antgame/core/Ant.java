package antgame.core;

import antgame.core.brain.instruction.Instruction;

/**
 * @author Sam Marsh
 */
public class Ant {

    private final Colony colony;
    private final Instruction state;
    private boolean hasFood;

    /**
     * Creates a new ant belonging to the specified colony.
     *
     * @param colony the colony to which the ant belongs
     */
    public Ant(Colony colony) {
        this.colony = colony;
        this.state = colony.getBrain().getInstruction();
        this.hasFood = false;
    }

    /**
     * @return the colony of the ant
     */
    public Colony getColony() {
        return colony;
    }

    public boolean hasFood() {
        return hasFood;
    }

}
