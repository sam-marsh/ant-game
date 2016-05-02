package antgame.core.brain;


import antgame.core.brain.instruction.DropInstruction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the {@link Brain} class.
 * Created by Arsalan on 02/05/2016.
 */
public class BrainTest {


    private static final int SENSE_LINE_NUMBER = 5;

    // Instruction to add to Brain
    private DropInstruction drop;


    private Brain brain;



    // Create an Instruction
    // Add to Brain
    @Before
    public void setUp() throws Exception {

        drop = new DropInstruction(SENSE_LINE_NUMBER);

        brain = new Brain(drop);

    }

    /**
     * Tests Getting an Instruction from the {@link Brain}
     * @see Brain#getInstructionGraph()
     * @throws Exception
     */
    @Test
    public void getInstructionGraph() throws Exception {

        assertEquals(drop,brain.getInstructionGraph());

    }

}