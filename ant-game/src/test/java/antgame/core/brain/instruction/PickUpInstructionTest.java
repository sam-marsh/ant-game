package antgame.core.brain.instruction;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the {@link PickUpInstruction} class.
 * Created by Arsalan on 02/05/2016.
 */
public class PickUpInstructionTest {


    //arbitrary number to set as the drop instruction's identifier (this is the line number in the ant brain file)
    private static final int PICKUP_LINE_NUMBER = 2;

    //just a random instruction which the move instruction moves to after executing
    private Instruction next;

    //the move instruction, used for testing
    private PickUpInstruction pickup;



    @Before
    public void setUp() throws Exception {

        next = new FlipInstruction(10, 10);
        pickup = new PickUpInstruction(PICKUP_LINE_NUMBER);
        pickup.success(next);
        pickup.failure(next);
    }



    /**
     * Tests all methods exposed by the {@link Instruction} class.
     *
     * @see Instruction#getType()
     * @see Instruction#getID()
     * @see Instruction#success()
     * @see Instruction#failure()
     */
    @Test
    public void testPickUpInstruction() {
        //pickup instruction should be of type PICKUP
        assertEquals(Instruction.Type.PICK_UP, pickup.getType());
        //check that the instruction ID is what we set it to be before
        assertEquals(PICKUP_LINE_NUMBER, pickup.getID());
        //both success and failure states should move to the single next state
        assertEquals(next, pickup.success());
        assertEquals(next, pickup.failure());
    }


}