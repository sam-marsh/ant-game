package antgame.core.brain.instruction;

/**
 * @author Sam Marsh
 */
public class UnmarkInstruction extends Instruction {

    private final int marker;
    private final Instruction st;

    public UnmarkInstruction(int insn, int marker, Instruction st) {
        super(insn);
        this.marker = marker;
        this.st = st;
    }

    public int getMarker() {
        return marker;
    }

    @Override
    public Instruction success() {
        return st;
    }

    @Override
    public Instruction failure() {
        return st;
    }

}
