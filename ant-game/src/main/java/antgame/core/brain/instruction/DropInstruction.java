package antgame.core.brain.instruction;

/**
 * Represents a drop instruction. A drop instruction orders that the ant drop
 * its food onto the current cell.
 *
 * @author Sam Marsh
 */
public class DropInstruction extends Instruction {

    /**
     * Creates a new drop instruction, with the given instruction identifier.
     *
     * @param insn the instruction number (line number)
     */
    public DropInstruction(int insn) {
        super(insn, Type.DROP);
    }

}
