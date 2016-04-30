package antgame.core.brain.parser;

import antgame.core.Colony;
import antgame.core.brain.Brain;
import antgame.core.brain.instruction.Instruction;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.*;

/**
 * @author Sam Marsh
 */
public class BrainParserTest {

    @Test
    public void test() throws IOException, ParseException {
        Brain brain = BrainParser.parse(
                Colony.Colour.RED,
                new File(BrainParserTest.class.getResource("/brain/ant-brain-1.txt").getFile())
        );

        Instruction first = brain.getInstructionGraph();
        assertEquals(Instruction.Type.SENSE, first.getType());
    }


}