package antgame.core.brain.instruction;

/**
 * @author Sam Marsh
 */
public class MarkInstruction extends Instruction {

    private final int marker;
    private final int st;

    public MarkInstruction(int insn, int marker, int st) {
        super(insn);
        this.marker = marker;
        this.st = st;
    }

}
