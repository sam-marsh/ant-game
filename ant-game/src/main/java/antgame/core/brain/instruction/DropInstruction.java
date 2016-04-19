package antgame.core.brain.instruction;

/**
 * @author Sam Marsh
 */
public class DropInstruction extends Instruction {

    private final Instruction st;

    public DropInstruction(int insn, Instruction st) {
        super(insn);
        this.st = st;
    }

}
