package antgame.core.brain.instruction;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Sam Marsh
 */
public class DropInstructionTest {

    //arbitrary number to set as the drop instruction's identifier (this is the line number in the ant brain file)
    private static final int DROP_LINE_NUMBER = 2;

    //just a random instruction which the drop instruction moves to after executing
    private Instruction next;

    //the drop instruction, used for testing
    private DropInstruction drop;

    @Before
    public void setUp() {
        next = new FlipInstruction(10, 10, null, null);
        drop = new DropInstruction(DROP_LINE_NUMBER, next);
    }

    /**
     * Tests all methods exposed by the {@link DropInstruction} class.
     */
    @Test
    public void testDrop() {
        //drop instruction should be of type DROP
        assertEquals(Instruction.Type.DROP, drop.getType());
        assertEquals(DROP_LINE_NUMBER, drop.getInstruction());
        //both success and failure states should move to the single next state
        assertEquals(next, drop.success());
        assertEquals(next, drop.failure());
    }

}