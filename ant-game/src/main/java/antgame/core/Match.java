/*
 * A match between two supplied players
 */
package antgame.core;

import antgame.core.world.World;

/**
 *
 * @author Regan
 */
public class Match {
    
    private Player playerRed;
    private Player playerBlack;
    private World world;
    
    public Match(Player playerRed, Player playerBlack, World world)
    {
        this.playerRed = playerRed;
        this.playerBlack = playerBlack;
        this.world = world;
    }
    
    public Player run()
    {
        return playerRed;
    }

    public World world() {
        return world;
    }

}
