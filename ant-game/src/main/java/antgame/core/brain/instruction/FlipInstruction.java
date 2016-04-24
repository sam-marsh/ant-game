package antgame.core.brain.instruction;

/**
 * Represents a flip instruction. A flip instruction is an instruction to create a random number in a
 * certain range (using {@link antgame.core.util.RandomNumberGenerator}, and move to one state if the
 * random number is 0, and move to another state otherwise.
 *
 * @author Sam Marsh
 */
public class FlipInstruction extends Instruction {

    //the random number used will be in the range 0..n inclusive
    private final int n;

    //the state to move to if rand(n) == 0
    private final Instruction st1;

    //the state to move to if rand(n) != 0
    private final Instruction st2;

    /**
     * Creates a new flip instruction.
     *
     * @param insn the instruction identifier (line number)
     * @param n the random number used will be in the range 0..n
     * @param st1 the state to transition to if the random number is 0
     * @param st2 the state to transition to otherwise
     */
    public FlipInstruction(int insn, int n, Instruction st1, Instruction st2) {
        super(insn);
        this.n = n;
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
