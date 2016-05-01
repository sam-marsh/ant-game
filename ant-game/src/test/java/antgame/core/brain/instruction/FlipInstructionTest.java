package antgame.core.brain.instruction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the {@link FlipInstruction} class.
 * Created by Arsalan Sadeghpour on 01/05/2016.
 */
public class FlipInstructionTest {

    //arbitrary number to set as the drop instruction's identifier (this is the line number in the ant brain file)
    private static final int FLIP_LINE_NUMBER = 5;

    // arbitrary random number used by Flip Instruction
    private static final int RANDOM_NUMBER = 45;


    //the flip instruction, used for testing
    private FlipInstruction flip;

    //just a random instruction which the flip instruction moves to after executing
    private Instruction next;



    @Before
    public void setUp() throws Exception {
        next = new DropInstruction(10);
        flip = new FlipInstruction(5,RANDOM_NUMBER);
        flip.success(next);
        flip.failure(next);

    }

    @After
    public void tearDown() throws Exception {

    }

    /**
     * Tests getting the range of the Flip Instruction by the {@link FlipInstruction} class
     * @see FlipInstruction#getRange()
     */
    @Test
    public void getRange() throws Exception {

        // Test range of Flip Instruction is
        assertEquals(5,flip.getRange());
    }

    /**
     * Tests below methods exposed by the {@link FlipInstruction} class
     * @see FlipInstruction#getType()
     * @see FlipInstruction#getID()
     * @see FlipInstruction#success()
     * @see FlipInstruction#failure()
     */
    @Test
    public void testFlip() throws Exception {

        //flip instruction should be of type FLIP
        assertEquals(Instruction.Type.FLIP, flip.getType());
        //check that the instruction ID is what we set it to be before
        assertEquals(FLIP_LINE_NUMBER, flip.getID());
        //both success and failure states should move to the single next state
        assertEquals(next, flip.success());
        assertEquals(next, flip.failure());
    }

}