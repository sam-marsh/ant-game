package antgame.core.brain.instruction;

import antgame.GlobalConfiguration;

/**
 * @author Sam Marsh
 */
public class FlipInstruction extends Instruction {

    private final int n;
    private final Instruction st1;
    private final Instruction st2;

    public FlipInstruction(int insn, int n, Instruction st1, Instruction st2) {
        super(insn);
        this.n = n;
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
