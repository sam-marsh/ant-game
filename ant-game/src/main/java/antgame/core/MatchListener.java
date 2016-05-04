package antgame.core;

import antgame.core.world.World;

/**
 * <strong>Currently unused.</strong> An event-listener for subscribing to
 * match events. Could possibly be extended in the future to provide functionality
 * for firing events when ants die, when food is dropped, etc.
 *
 * @author Sam Marsh
 */
public interface MatchListener {

    /**
     * Called when the world is progressed by one 'step' in a match.
     *
     * @param world the world
     */
    void updated(World world);

}
