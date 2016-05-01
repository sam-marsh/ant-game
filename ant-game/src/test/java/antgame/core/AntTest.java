package antgame.core;

import antgame.core.brain.Brain;
import antgame.core.brain.instruction.Condition;
import antgame.core.brain.instruction.Instruction;
import antgame.core.brain.instruction.SenseInstruction;
import antgame.core.brain.parser.BrainParser;
import antgame.core.world.World;
import antgame.core.world.parser.WorldParser;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests the {@link Ant} class.
 *
 * @author Sam Marsh
 */
public class AntTest {

    //holds the brain of the red ant colony (resource /brain/ant-brain-1.txt)
    private Brain redBrain;

    //holds the red colony instance
    private Colony redColony;

    //holds the brain of the black ant colony (resource /brain/ant-brain-1.txt) i.e. same brain
    private Brain blackBrain;

    //holds the black colony instance
    private Colony blackColony;

    //the world in which the ants reside (resource /world/ant-world-1.txt)
    private World world;

    /**
     * Initialises the variables above (the brains and world).
     *
     * @throws IOException if an error occurs reading the brain or world files
     * @throws ParseException if one of the brains or the world is malformed
     */
    @Before
    public void setUp() throws IOException, ParseException {
        //parse the red brain
        redBrain = BrainParser.parse(
                Colony.Colour.RED,
                new File(AntTest.class.getResource("/brain/ant-brain-1.txt").getFile())
        );
        redColony = new Colony(Colony.Colour.RED, redBrain);

        //parse the black brain
        blackBrain = BrainParser.parse(
                Colony.Colour.BLACK,
                new File(AntTest.class.getResource("/brain/ant-brain-1.txt").getFile())
        );
        blackColony = new Colony(Colony.Colour.BLACK, blackBrain);

        //create the world
        world = WorldParser.parse(
                new File(AntTest.class.getResource("/world/ant-world-1.txt").getFile())
        );

        //create the ants in the world
        world.spawnAnts(redColony, blackColony);
    }

    @Test
    public void testInitialState() {

    }

}