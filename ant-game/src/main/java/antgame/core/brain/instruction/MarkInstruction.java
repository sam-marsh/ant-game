package antgame.core.brain.instruction;

/**
 * Represents a mark instruction. A mark instruction is an order for an ant to mark the cell with a
 * sense marker.
 *
 * @author Sam Marsh
 */
public class MarkInstruction extends Instruction {

    //the integer marker to mark the cell with, in the range 0..5
    private final int marker;

    //the state to transition to after the cell is marked
    private final Instruction st;

    /**
     * Creates a new mark instruction.
     *
     * @param insn the instruction identifier (line number)
     * @param marker the marker to mark the cell with, in the range 0..5
     * @param st the state to transition to after marking the cell
     */
    public MarkInstruction(int insn, int marker, Instruction st) {
        super(insn);
        this.marker = marker;
        this.st = st;
    }

    /**
     * @return the integer to mark the cell with, in the range 0..5
     */
    public int getMarker() {
        return marker;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Instruction success() {
        return st;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Instruction failure() {
        return st;
    }

}
