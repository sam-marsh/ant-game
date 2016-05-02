package antgame.core.brain.instruction;

import antgame.core.Colony;
import antgame.core.world.Marker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the {@link MarkInstruction} class.
 * Created by Arsalan on 01/05/2016.
 */
public class MarkInstructionTest {


    //just a random instruction which the drop instruction moves to after executing
    private Instruction next;

    //the Mark instruction, used for testing
    private MarkInstruction mark;

    // Marker used for testing
    private Marker m;

    // Marker 2 used for testing
    private Marker m2;


    // Mark Instruction line number
    private static int MARK_LINE_NUMBER = 5;




    @Before
    public void setUp() throws Exception {

        m = new Marker(5);
        m2 = new Marker(2);

        mark = new MarkInstruction(MARK_LINE_NUMBER,m);

    }


    /**
     * Tests if a Marker set was the correct marker in {@link MarkInstruction}
     * @see MarkInstruction#getMarker()
     */
    @Test
    public void testGetMarker() throws Exception {

        // Mark instruction should return the marker set
        assertEquals(m,mark.getMarker());

        // Mark Instruction should not return the 2nd marker
        assertNotEquals(m2,mark.getMarker());
    }

    /**
     * Tests methods within Instruction Class {@link Instruction}
     * @see Instruction#getID()
     * @see Instruction#getType()
     * @see Instruction#success()
     * @see Instruction#failure()
     */
    @Test
    public void testMarkInstruction() throws Exception
    {
        //mark instruction should be of type MARK
        assertEquals(Instruction.Type.MARK, mark.getType());

        //check that the instruction ID is what we set it to be before
        assertEquals(MARK_LINE_NUMBER, mark.getID());

        //both success and failure states should move to the single next state
        assertEquals(next, mark.success());
        assertEquals(next, mark.failure());
    }

}