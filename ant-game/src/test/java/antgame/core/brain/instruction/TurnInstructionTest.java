package antgame.core.brain.instruction;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the {@link TurnInstruction} class.
 * Created by Arsalan on 02/05/2016.
 */
public class TurnInstructionTest {


    //arbitrary number to set as the drop instruction's identifier (this is the line number in the ant brain file)
    private static final int TURN_LINE_NUMBER = 2;

    //just a random instruction which the drop instruction moves to after executing
    private Instruction next;

    //the drop instruction, used for testing
    private TurnInstruction turn;



    @Before
    public void setUp() {

        next = new FlipInstruction(10, 10);
        turn = new TurnInstruction(TURN_LINE_NUMBER, TurnInstruction.Direction.LEFT);
        turn.success(next);
        turn.failure(next);
    }



    /**
     * Tests whether the set direction is returned in {@link TurnInstruction}
     * @see TurnInstruction#getDirection()
     * @throws Exception
     */
    @Test
    public void getDirection() throws Exception {

        assertEquals(TurnInstruction.Direction.LEFT,turn.getDirection());
    }



    /**
     * Tests below methods exposed by the {@link Instruction} class
     * @see Instruction#getType()
     * @see Instruction#getID()
     * @see Instruction#success()
     * @see Instruction#failure()
     */
    @Test
    public void testTurnInstruction() throws Exception {

        //turn instruction should be of type turn
        assertEquals(Instruction.Type.SENSE, turn.getType());
        //check that the instruction ID is what we set it to be before
        assertEquals(TURN_LINE_NUMBER, turn.getID());
        //both success and failure states should move to the single next state
        assertEquals(next, turn.success());
        assertEquals(next, turn.failure());
    }


}