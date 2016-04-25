package antgame.core.brain.instruction;

import antgame.core.world.Marker;

/**
 * Represents an instruction to remove a marker from a cell.
 *
 * @author Sam Marsh
 */
public class UnmarkInstruction extends Instruction {

    //the marker index, in the range 0..5
    private final Marker marker;

    //the instruction to transition to next
    private final Instruction st;

    /**
     * Creates a new unmark instruction.
     *
     * @param insn the instruction identifer (line number)
     * @param marker the marker to remove, in the range 0..5
     * @param st the instruction to transition to after removing the marker
     */
    public UnmarkInstruction(int insn, Marker marker, Instruction st) {
        super(insn);
        this.marker = marker;
        this.st = st;
    }

    /**
     * @return the marker index to remove
     */
    public Marker getMarker() {
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
