package antgame.core.brain.instruction;

import antgame.core.world.Marker;

/**
 * Represents a mark instruction. A mark instruction is an order for an ant to mark the cell with a
 * sense marker.
 *
 * @author Sam Marsh
 */
public class MarkInstruction extends Instruction {

    //the integer marker to mark the cell with, in the range 0..5
    private final Marker marker;

    /**
     * Creates a new mark instruction.
     *
     * @param insn the instruction identifier (line number)
     * @param marker the marker to mark the cell with, in the range 0..5
     */
    public MarkInstruction(int insn, Marker marker) {
        super(insn, Type.MARK);
        this.marker = marker;
    }

    /**
     * @return the integer to mark the cell with, in the range 0..5
     */
    public Marker getMarker() {
        return marker;
    }

}
