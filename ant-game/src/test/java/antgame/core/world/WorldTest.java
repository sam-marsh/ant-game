package antgame.core.world;

import antgame.core.AntTest;
import antgame.core.Colony;
import antgame.core.Direction;
import antgame.core.brain.Brain;
import antgame.core.brain.instruction.Condition;
import antgame.core.brain.parser.BrainParser;
import antgame.core.world.parser.WorldParser;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Tests The {@link World} class.
 * Created by Arsalan on 02/05/2016.
 */
public class WorldTest {

    // World to test
    private World world;

    // Cells to Test World With
    private Cell rocky;
    private Cell clear;
    private Cell anthill_red;
    private Cell anthill_black;

    // Ant Colonies To Test World With
    private Colony redColony;
    private Colony blackColony;

    // Brains of Ants
    private Brain redBrain;
    private Brain blackBrain;


    @Before
    public void setUp() throws Exception {

        world = WorldParser.parse(new File(AntTest.class.getResource("/world/ant-world-1.txt").getFile()));

        clear = new Cell(Cell.Type.CLEAR,1,4);
        rocky = new Cell(Cell.Type.ROCK,0,3);

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


        // Create The Ants for Testing Spawning, Murdering and Checking Ant Friend/Foe Conditions
        world.spawnAnts(redColony, blackColony);


    }

    /**
     * Tests Whether an Adjacent Cell Functions Correctly
     * Tests that a cell adjacent to a particular clear cell is rocky in the particular world and not clear
     * @see World#adjacent(Cell, Direction)
     * @throws Exception
     */
    @Test
    public void adjacent() throws Exception {

        // (1,4) is a clear cell and adjacent to this is rocky cell at (0,3) in the north west section

        assertEquals(rocky.getType(),world.adjacent(clear, Direction.NORTH_WEST).getType());

        assertNotEquals(clear.getType(),world.adjacent(clear, Direction.NORTH_WEST).getType());

    }

    /**
     * Checks if World Can Correctly Identify Sense Conditions of Ant
     * @see World#check(Condition, Colony, Cell)
     * @throws Exception
     */
    @Test
    public void check() throws Exception {

    }

    /**
     * Check if Ants of different colonies Are spawned correctly
     * @see World#spawnAnts(Colony, Colony)
     * @throws Exception
     */
    @Test
    public void spawnAnts() throws Exception {

    }


    /**
     * Tests When Ants Are Killed they are removed from the world and turn into food correctly
     * @throws Exception
     */
    @Test
    public void murder() throws Exception {

    }

}