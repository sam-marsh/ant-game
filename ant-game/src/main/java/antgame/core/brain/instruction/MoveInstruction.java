package antgame.core.brain.instruction;

/**
 * Represents an instruction for an ant to move in the current direction.
 *
 * @author Sam Marsh
 */
public class MoveInstruction extends Instruction {

    //the state to move to on succcessful move
    private final Instruction st1;

    //the state to move to on failure
    private final Instruction st2;

    /**
     * Creates a new move instruction.
     *
     * @param insn the instruction identifier (line number)
     * @param st1 the state to transition to on success
     * @param st2 the state to transition to on failure
     */
    public MoveInstruction(int insn, Instruction st1, Instruction st2) {
        super(insn, Type.MOVE);
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
