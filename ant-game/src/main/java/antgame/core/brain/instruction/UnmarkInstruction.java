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

    /**
     * Creates a new unmark instruction.
     *
     * @param insn the instruction identifer (line number)
     * @param marker the marker to remove, in the range 0..5
     */
    public UnmarkInstruction(int insn, Marker marker) {
        super(insn, Type.UNMARK);
        this.marker = marker;
    }

    /**
     * @return the marker index to remove
     */
    public Marker getMarker() {
        return marker;
    }

}
