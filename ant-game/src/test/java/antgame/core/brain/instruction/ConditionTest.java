package antgame.core.brain.instruction;

import antgame.core.Direction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the {@link Condition} class.
 * Created by Arsalan on 01/05/2016.
 */
public class ConditionTest {


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

        // Tests Conditions
        MarkerCondition = new Condition(Condition.Type.MARKER);
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

    @After
    public void tearDown() throws Exception {

    }

    /**
     * Tests whether condition type is correct for all conditions
     * @throws Exception
     */
    @Test
    public void getType() throws Exception {

        // tests that the conditions are what we expected
        assertEquals(Condition.Type.MARKER,MarkerCondition.getType());
        assertEquals(Condition.Type.FOOD,FoodCondition.getType());
        assertEquals(Condition.Type.FOE,FoeCondition.getType());
        assertEquals(Condition.Type.FOE_HOME,FoeHomeCondition.getType());
        assertEquals(Condition.Type.FOE_MARKER,FoeMarkerCondition.getType());
        assertEquals(Condition.Type.FOE_WITH_FOOD,FoeFoodCondition.getType());
        assertEquals(Condition.Type.FRIEND,FriendCondition.getType());
        assertEquals(Condition.Type.FRIEND_WITH_FOOD,FriendFoodCondition.getType());
        assertEquals(Condition.Type.HOME,HomeCondition.getType());
        assertEquals(Condition.Type.ROCK,RockCondition.getType());
    }

    /**
     * Tests If the Marker is set properly
     * Tests If Error is raised when obtaining a marker from a different condition
     */
    @Test (expected = AssertionError.class)
    public void getMarker() {

        assertNotNull(MarkerCondition.getMarker());
        assertNull(FoodCondition.getMarker());


    }
}