package antgame.core.brain.instruction;

/**
 * Represents a drop instruction. A drop instruction orders that the ant drop
 * its food onto the current cell.
 *
 * @author Sam Marsh
 */
public class DropInstruction extends Instruction {

    //the state to move to next
    private final Instruction st;

    /**
     * Creates a new drop instruction, with the given instruction identifier and the state
     * to move to next.
     *
     * @param insn the instruction number (line number)
     * @param st the state to transition to next
     */
    public DropInstruction(int insn, Instruction st) {
        super(insn, Type.DROP);
        this.st = st;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Instruction success() {
        return st;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Instruction failure() {
        return st;
    }

}
