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
 * Tests the  {@link BrainParser} class.
 *  @author Sam Marsh
 */
public class BrainParserTest {


    /**
     * Tests Brain Parser on a Valid File
     * Tests That It Parsed The Expected Instruction
     *
     * @throws IOException
     * @throws ParseException
     * @see BrainParser#parse()
     */
    @Test
    public void testValid() throws IOException, ParseException {
        Brain brain = BrainParser.parse(
                new File(BrainParserTest.class.getResource("/brain/ant-brain-1.txt").getFile())
        );

        Instruction first = brain.getInstructionGraph();
        assertEquals(Instruction.Type.SENSE, first.getType());
    }

    /**
     * Tests Brain Parser on a Invalid File
     */
    @Test (expected = ParseException.class)
    public void testInvalid() throws IOException, ParseException {

        Brain brain = BrainParser.parse(
                new File(BrainParserTest.class.getResource("/brain/invalid.txt").getFile())
        );
    }


    /**
     * Test Brain Parser on Large File
     */
    @Test (expected = ParseException.class)
    public void testLarge() throws IOException, ParseException  {

        Brain brain = BrainParser.parse(
                new File(BrainParserTest.class.getResource("/brain/ant-brain-too-large.txt").getFile())
        );
    }



    /**
     * Test Brain Parser on an Empty File
     */
    @Test (expected = ParseException.class)
    public void testEmptyFile() throws IOException, ParseException {

        Brain brain = BrainParser.parse(
                new File(BrainParserTest.class.getResource("/brain/empty.txt").getFile())
        );

    }

    /**
     * Test Brain Parser on an Invalid Instruction Length
     */
    @Test (expected = ParseException.class)
    public void testLength() throws IOException, ParseException  {

        Brain brain = BrainParser.parse(
                new File(BrainParserTest.class.getResource("/brain/ant-brain-invalid-sense-instruction.txt").getFile())
        );

    }

    /**
     * Test Brain Parser on Out of Range Marker Instruction
     */
    @Test (expected = ParseException.class)
    public void testOutofRange() throws IOException, ParseException {

        Brain brain = BrainParser.parse(
                new File(BrainParserTest.class.getResource("/brain/ant-brain-mark-out-range.txt").getFile())
        );
    }

}