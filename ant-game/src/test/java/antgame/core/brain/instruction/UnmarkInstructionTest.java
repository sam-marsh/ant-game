package antgame.core.brain.instruction;

import antgame.core.Colony;
import antgame.core.world.Marker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the {@link UnmarkInstruction} class.
 * Created by Arsalan on 02/05/2016.
 */
public class UnmarkInstructionTest {


    //just a random instruction which the unmark instruction moves to after executing
    private Instruction next;

    //the Unmark instruction, used for testing
    private UnmarkInstruction unmark;

    // Marker used for testing
    private Marker m;

    // Marker 2 used for testing
    private Marker m2;


    // Unmark Instruction line number
    private static int UNMARK_LINE_NUMBER = 5;


    @Before
    public void setUp() throws Exception {

        m = new Marker(5);
        m2 = new Marker(2);

        unmark = new UnmarkInstruction(UNMARK_LINE_NUMBER, m);

    }


    /**
     * Tests if Marker to be removed was set correctly in {@link UnmarkInstruction}
     *
     * @see UnmarkInstruction#getMarker()
     */
    @Test
    public void testGetMarker() throws Exception {

        // Mark instruction should return the marker set
        assertEquals(m, unmark.getMarker());

        // Mark Instruction should not return the 2nd marker
        assertNotEquals(m2, unmark.getMarker());
    }

    /**
     * Tests methods within Instruction Class {@link Instruction}
     *
     * @see Instruction#getID()
     * @see Instruction#getType()
     * @see Instruction#success()
     * @see Instruction#failure()
     */
    @Test
    public void testUnmarkInstruction() throws Exception {
        //unmark instruction should be of type UNMARK
        assertEquals(Instruction.Type.UNMARK, unmark.getType());

        //check that the instruction ID is what we set it to be before
        assertEquals(UNMARK_LINE_NUMBER, unmark.getID());

        //both success and failure states should move to the single next state
        assertEquals(next, unmark.success());
        assertEquals(next, unmark.failure());
    }
}


