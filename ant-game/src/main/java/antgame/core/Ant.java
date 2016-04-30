package antgame.core;

import antgame.core.brain.instruction.*;
import antgame.core.util.RandomNumberGenerator;
import antgame.core.world.Cell;
import antgame.core.world.World;

/**
 * Represents a game ant.
 *
 * @author Sam Marsh
 */
public class Ant {

    //used for flip instructions, shared amongst ants
    private static final RandomNumberGenerator RNG = new RandomNumberGenerator();

    //all ants will have this direction initially
    private static final Direction DEFAULT_DIRECTION = Direction.EAST;

    //how long to sleep once moved
    private static final int REST_NUMBER_TURNS = 14;

    //the ant's team
    private final Colony colony;

    //the current brain state
    private Instruction insn;

    //whether this ant is carrying a food particle
    private boolean food;

    //the direction this ant is facing
    private Direction direction;

    //holds the number of turns to rest
    private int rest;

    //access to the ant's world context
    private World world;

    //the ant where this ant currently resides
    private Cell cell;

    /**
     * Creates a new ant belonging to the specified colony.
     *
     * @param colony the colony to which the ant belongs
     * @param world the world in which this ant lives
     * @param cell the initial cell to place the ant in
     */
    public Ant(Colony colony, World world, Cell cell) {
        this.colony = colony;
        this.insn = colony.getBrain().getInstructionGraph();
        this.food = false;
        this.direction = DEFAULT_DIRECTION;
        this.rest = 0;
        this.world = world;
        this.cell = cell;
        cell.setAnt(this);
    }

    /**
     * Performs a game step, with this ant performing a particular
     * action based on the ant's brain state.
     */
    public void step() {
        //do nothing if have moved recently
        if (resting()) {
            sleep();
            return;
        }
        //otherwise carry out an instruction based on brain state
        switch (insn.getType()) {
            case DROP:
                drop();
                break;
            case FLIP:
                flip();
                break;
            case MARK:
                mark();
                break;
            case MOVE:
                move();
                break;
            case PICK_UP:
                pickUp();
                break;
            case SENSE:
                sense();
                break;
            case TURN:
                turn();
                break;
            case UNMARK:
                unmark();
                break;
        }
    }

    /**
     * Carries out a drop instruction.
     */
    private void drop() {
        if (!hasFood()) {
            insn = insn.failure();
            return;
        }
        food = false;
        cell.setFood(cell.getFoodAmount() + 1);
        insn = insn.success();
    }

    /**
     * Carries out a flip instruction.
     */
    private void flip() {
        FlipInstruction flip = (FlipInstruction) insn;
        int n = flip.getRange();
        if (RNG.next(n) == 0) {
            insn = insn.success();
        } else {
            insn = insn.failure();
        }
    }

    /**
     * Carries out a mark instruction.
     */
    private void mark() {
        MarkInstruction mark = (MarkInstruction) insn;
        if (cell.mark(mark.getMarker())) {
            insn = insn.success();
        } else {
            insn = insn.failure();
        }
    }

    /**
     * Carries out a move instruction.
     */
    private void move() {
        Cell adjacent = world.adjacent(cell, direction);
        if (adjacent.free()) {
            //relocate the ant
            cell.removeAnt();
            cell = adjacent;
            cell.setAnt(this);
            //start resting
            rest = REST_NUMBER_TURNS;
            insn = insn.success();
        } else {
            insn = insn.failure();
        }
    }

    /**
     * Carries out a pick up instruction.
     */
    private void pickUp() {
        PickUpInstruction pickUp = (PickUpInstruction) insn;
        if (cell.hasFood()) {
            cell.setFood(cell.getFoodAmount() - 1);
            food = true;
            insn = pickUp.success();
        } else {
            insn = pickUp.failure();
        }
    }

    /**
     * Carries out a sense instruction.
     */
    private void sense() {
        SenseInstruction sense = (SenseInstruction) insn;
        Cell toSense;
        switch (sense.getDirection()) {
            case HERE:
                toSense = cell;
                break;
            case AHEAD:
                toSense = world.adjacent(cell, direction);
                break;
            case LEFT_AHEAD:
                toSense = world.adjacent(cell, direction.left());
                break;
            case RIGHT_AHEAD:
                toSense = world.adjacent(cell, direction.right());
                break;
            default:
                throw new AssertionError("internal error: sense direction not implemented");
        }
        if (world.check(sense.getCondition(), colony, toSense)) {
            insn = insn.success();
        } else {
            insn = insn.failure();
        }
    }

    /**
     * Carries out a turn instruction.
     */
    private void turn() {
        TurnInstruction turn = (TurnInstruction) insn;
        switch (turn.getDirection()) {
            case LEFT:
                direction = direction.left();
                break;
            case RIGHT:
                direction = direction.right();
                break;
        }
        insn = turn.success();
    }

    /**
     * Carries out an unmark instruction.
     */
    private void unmark() {
        UnmarkInstruction unmark = (UnmarkInstruction) insn;
        if (cell.unmark(unmark.getMarker())) {
            insn = insn.success();
        } else {
            insn = insn.failure();
        }
    }

    /**
     * Continues to do nothing while resting from having moved less than 14 turns ago.
     */
    private void sleep() {
        if (rest <= 0) {
            throw new AssertionError("ant has already woken up");
        }
        --rest;
    }

    /**
     * @return if this ant is currently resting from having moved less than 14 turns ago
     */
    private boolean resting() {
        return rest > 0;
    }

    /**
     * @return the colony of the ant
     */
    public Colony getColony() {
        return colony;
    }

    /**
     * @return true if the ant is carrying anything, otherwise false
     */
    public boolean hasFood() {
        return food;
    }

}
