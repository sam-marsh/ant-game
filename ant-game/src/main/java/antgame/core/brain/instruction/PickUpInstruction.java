package antgame.core.brain.instruction;

/**
 * Represents an instruction for an ant to pick up food at the current cell.
 *
 * @author Sam Marsh
 */
public class PickUpInstruction extends Instruction {

    /**
     * Creates a new instruction to pick up food.
     *
     * @param insn the instruction identifier (line number)
     */
    public PickUpInstruction(int insn) {
        super(insn, Type.PICK_UP);
    }

}
