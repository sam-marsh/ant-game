package antgame.core.brain.instruction;

/**
 * Represents an instruction for an ant to pick up food at the current cell.
 *
 * @author Sam Marsh
 */
public class PickUpInstruction extends Instruction {

    //the state to move to if food was picked up
    private final Instruction st1;

    //the state to move to if failed to pick up food, or already carrying food
    private final Instruction st2;

    /**
     * Creates a new instruction to pick up food.
     *
     * @param insn the instruction identifier (line number)
     * @param st1 the state to move to if the food is picked up successfully
     * @param st2 the state to move to if the ant failed to pick up the food (e.g. if already
     *            carrying food, or no food in current cell)
     */
    public PickUpInstruction(int insn, Instruction st1, Instruction st2) {
        super(insn);
        this.st1 = st1;
        this.st2 = st2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Instruction success() {
        return st1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Instruction failure() {
        return st2;
    }

}
