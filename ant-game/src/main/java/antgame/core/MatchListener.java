package antgame.core;

import antgame.core.world.Cell;

/**
 * @author Sam Marsh
 */
public interface MatchListener {

    void turn(Cell[][] view, int n);

}
