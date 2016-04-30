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

    /**
     * Creates a new flip instruction.
     *
     * @param insn the instruction identifier (line number)
     * @param n the random number used will be in the range 0..n
     */
    public FlipInstruction(int insn, int n) {
        super(insn, Type.FLIP);
        this.n = n;
    }

    /**
     * @return the upper bound on the random number to be generated, non-inclusive
     */
    public int getRange() {
        return n;
    }

}
