package antgame.core.brain.instruction;

import antgame.core.Colony;
import antgame.core.world.Marker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the {@link SenseInstruction} class.
 * Created by Arsalan on 02/05/2016.
 */
public class SenseInstructionTest {


    //arbitrary number to set as the sense instruction's identifier (this is the line number in the ant brain file)
    private static final int SENSE_LINE_NUMBER = 5;


    //the sense instruction, used for testing
    private SenseInstruction sense;

    // marker condition used for testing
    private Condition MarkerCondition;


    //just a random instruction which the flip instruction moves to after executing
    private Instruction next;


    @Before
    public void setUp() throws Exception {

        // setup arbitrary  next instruction
        next = new DropInstruction(10);

        MarkerCondition = new Condition(new Marker(Colony.Colour.BLACK,3));

        sense = new SenseInstruction(SENSE_LINE_NUMBER, SenseInstruction.Direction.AHEAD,MarkerCondition);
        sense.success(next);
        sense.failure(next);
    }

    /**
     * Tests whether the set direction is returned in  {@link SenseInstruction}
     * @see SenseInstruction#getDirection()
     */
    @Test
    public void getDirection() throws Exception {

        assertEquals(SenseInstruction.Direction.AHEAD,sense.getDirection());
    }

    /**
     * Tests whether the set condition is returned in {@link SenseInstruction}
     * @see SenseInstruction#getCondition()
     * @throws Exception
     */
    @Test
    public void getCondition() throws Exception {

        assertEquals(MarkerCondition,sense.getCondition());
    }



    /**
     * Tests below methods exposed by the {@link Instruction} class
     * @see Instruction#getType()
     * @see Instruction#getID()
     * @see Instruction#success()
     * @see Instruction#failure()
     */
    @Test
    public void testSenseInstruction() throws Exception {

        //flip instruction should be of type FLIP
        assertEquals(Instruction.Type.SENSE, sense.getType());
        //check that the instruction ID is what we set it to be before
        assertEquals(SENSE_LINE_NUMBER, sense.getID());
        //both success and failure states should move to the single next state
        assertEquals(next, sense.success());
        assertEquals(next, sense.failure());
    }

}