package antgame.core.brain.instruction;

/**
 * Represents an instruction for an ant to move in the current direction.
 *
 * @author Sam Marsh
 */
public class MoveInstruction extends Instruction {

    /**
     * Creates a new move instruction.
     *
     * @param insn the instruction identifier (line number)
     */
    public MoveInstruction(int insn) {
        super(insn, Type.MOVE);
    }

}
