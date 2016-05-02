package antgame.core.brain.instruction;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the {@link MoveInstruction} class.
 * Created by Arsalan on 02/05/2016.
 */
public class MoveInstructionTest {

    //arbitrary number to set as the drop instruction's identifier (this is the line number in the ant brain file)
    private static final int MOVE_LINE_NUMBER = 2;

    //just a random instruction which the move instruction moves to after executing
    private Instruction next;

    //the move instruction, used for testing
    private MoveInstruction move;


    @Before
    public void setUp() throws Exception {

        next = new FlipInstruction(10, 10);
        move = new MoveInstruction(MOVE_LINE_NUMBER);
        move.success(next);
        move.failure(next);
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
    public void testMoveInstruction() {
        //move instruction should be of type MOVE
        assertEquals(Instruction.Type.MOVE, move.getType());
        //check that the instruction ID is what we set it to be before
        assertEquals(MOVE_LINE_NUMBER, move.getID());
        //both success and failure states should move to the single next state
        assertEquals(next, move.success());
        assertEquals(next, move.failure());
    }





}