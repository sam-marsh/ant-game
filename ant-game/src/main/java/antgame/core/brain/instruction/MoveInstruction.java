package antgame.core.brain.instruction;

/**
 * @author Sam Marsh
 */
public class MoveInstruction extends Instruction {

    private final Instruction st1;
    private final Instruction st2;

    public MoveInstruction(int insn, Instruction st1, Instruction st2) {
        super(insn);
        this.st1 = st1;
        this.st2 = st2;
    }

    @Override
    public Instruction success() {
        return st1;
    }

    @Override
    public Instruction failure() {
        return st2;
    }

}
