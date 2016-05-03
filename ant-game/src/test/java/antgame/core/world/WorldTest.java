package antgame.core.world;

import antgame.core.Ant;
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


    // Ant Colonies To Test World With
    private Colony redColony;
    private Colony blackColony;
    private Colony redPickupColony;
    private Colony blackPickupColony;

    // Brains of Ants
    private Brain redBrain;
    private Brain blackBrain;
    private Brain redPickUpBrain;
    private Brain blackPickUpBrain;

    // Conditions
    private Condition MarkerCondition;
    private Condition FoodCondition;
    private Condition FoeCondition;
    private Condition FoeHomeCondition;
    private Condition FoeMarkerCondition;
    private Condition FoeFoodCondition;
    private Condition FriendCondition;
    private Condition FriendFoodCondition;
    private Condition HomeCondition;
    private Condition RockCondition;






    @Before
    public void setUp() throws Exception {

        clear = new Cell(Cell.Type.CLEAR,1,5);
        rocky = new Cell(Cell.Type.ROCK,0,3);


        //parse the red brain
        redBrain = BrainParser.parse(
                new File(AntTest.class.getResource("/brain/ant-brain-1.txt").getFile())
        );

        //parse the red brain with a single pickup instruction to test FriendFood Condition
        redPickUpBrain = BrainParser.parse(
                new File(AntTest.class.getResource("/brain/ant-pick-up-test.txt").getFile())
        );

        redColony = new Colony(Colony.Colour.RED, redBrain);
        redPickupColony = new Colony(Colony.Colour.RED,redPickUpBrain);


        //parse the black brain
        blackBrain = BrainParser.parse(
                new File(AntTest.class.getResource("/brain/ant-brain-1.txt").getFile())
        );

        //parse the black brain with a single pickup instruction to test FoeFood Condition
        blackPickUpBrain = BrainParser.parse(
                new File(AntTest.class.getResource("/brain/ant-pick-up-test.txt").getFile())
        );

        blackColony = new Colony(Colony.Colour.BLACK, blackBrain);
        blackPickupColony = new Colony(Colony.Colour.BLACK, blackPickUpBrain);


        //create the world
        world = WorldParser.parse(
                new File(AntTest.class.getResource("/world/ant-world-1.txt").getFile())
        );

        // Setup Test Conditions
        MarkerCondition = new Condition(new Marker(3));
        FoodCondition = new Condition(Condition.Type.FOOD);
        FoeCondition = new Condition(Condition.Type.FOE);
        FoeHomeCondition = new Condition(Condition.Type.FOE_HOME);
        FoeMarkerCondition = new Condition(Condition.Type.FOE_MARKER);
        FoeFoodCondition = new Condition(Condition.Type.FOE_WITH_FOOD);
        FriendCondition = new Condition(Condition.Type.FRIEND);
        FriendFoodCondition = new Condition(Condition.Type.FRIEND_WITH_FOOD);
        HomeCondition = new Condition(Condition.Type.HOME);
        RockCondition = new Condition(Condition.Type.ROCK);



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

        // Tests assume enemy ants are from the black colony
        // Friendly ants are from the red colony

        Cell friend = new Cell(Cell.Type.CLEAR,5,10);
        Cell foe = new Cell(Cell.Type.CLEAR,7,10);

        // Add Friendly Ant to World - Using Red Pickup Colony To Test FriendFood Condition
        world.getAnts().add(new Ant(4,redPickupColony,world,friend));

        // Add Enemy Ant to World - Using Black Colony
        world.getAnts().add(new Ant(8,blackPickupColony,world,foe));

        // Test Checking for Friend
        assertTrue(world.check(FriendCondition,redPickupColony,friend));

        // Test Checking for Enemy
        assertTrue(world.check(FoeCondition,redColony,foe));

        // Add Food to A Friendly Ant's Cell
        world.getAnts().get(0).getCell().setFood(3);
        // Tell the Ant to Pick The Food Up
        world.getAnts().get(0).pickUp();
        // Check if the Friendly Ant Has Food
        assertTrue(world.check(FriendFoodCondition,redPickupColony,friend));



        // Test Checking for Enemy with Food
        world.getAnts().get(1).getCell().setFood(4);
        world.getAnts().get(1).pickUp();
        assertTrue(world.check(FoeFoodCondition,blackPickupColony,friend));


        // Test Checking for Food
        // Cell [0][0] contains food in the particular ant world testing file
        assertTrue(world.check(FoodCondition,redColony,world.getCells()[0][0]));


        // Test Checking for Rock
        assertTrue(world.check(RockCondition,redColony,rocky));


        // Test Checking for Marker
        // Create The Marker
        Marker m = new Marker(3);
        Cell marked = new Cell(Cell.Type.CLEAR,52,13);
        marked.mark(redColony,m);

        // add the marked cell to the world
       world.getCells()[52][13] = marked;

        // check if cell was marked
        assertTrue(world.check(MarkerCondition,redColony,marked));


        // Test Checking for Home
        Cell home = new Cell(Cell.Type.ANTHILL_RED,0,5);
        assertTrue(world.check(HomeCondition,redColony,home));

        // Test Checking for Enemy Home
        Cell enemy = new Cell(Cell.Type.ANTHILL_BLACK,0,6);
        assertTrue(world.check(FoeHomeCondition,redColony,enemy));

    }

    /**
     * Todo: Check if Ants of different colonies Are spawned correctly
     * @see World#spawnAnts(Colony, Colony)
     * @throws Exception
     */
    @Test
    public void spawnAnts() throws Exception {

        world.spawnAnts(redColony,blackColony);


        // Tests That an Ant from the Black Colony was spawned correctly
        /// The Cell representing the first Ant is a black anthill cell
        assertEquals(blackColony,world.getAnts().get(0).getColony());

        // Tests That an Ant from the Red Colony was spawned correctly
        // The Cell representing the 200th Ant is a red anthill cell
        assertEquals(redColony,world.getAnts().get(200).getColony());
    }


    /**
     * Tests When Ants Are Killed they are removed from the world and turn into food correctly
     * @throws Exception
     */
    @Test
    public void murder() throws Exception {

        // Spawn The Ants
        world.spawnAnts(redColony,blackColony);


        Cell antCell = world.getAnts().get(0).getCell();

        // Ant is still alive
        assertTrue(antCell.hasAnt());

        // Kill The Ant
        world.murder(world.getAnts().get(0));

        // Ant's Cell has turned into food
        assertTrue(antCell.hasFood());
    }

}