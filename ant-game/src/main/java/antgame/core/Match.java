/*
 * A match between two supplied players
 */
package antgame.core;

import antgame.core.Colony.Colour;
import antgame.core.world.*;
import antgame.core.world.Cell.Type;

/**
 *
 * @author Regan
 */
public class Match {
    
    private final Player playerRed;
    private final Player playerBlack;
    private final World world;
    
    public Match(Player playerRed, Player playerBlack, World world)
    {
        this.playerRed = playerRed;
        this.playerBlack = playerBlack;
        this.world = world;
    }
    
    public Player run(int rounds)
    {
        int redFood = 0;
        int blackFood = 0;
        
        world.spawnAnts(new Colony(Colour.RED, playerRed.getBrain()), new Colony(Colour.RED, playerBlack.getBrain()));
        for (int i = 0; i<rounds  ;i++)
        {
            for (Ant ant : world.getAnts())
            {
                if (ant.surrounded())
                {
                    world.murder(ant);
                }
            }
            
            for (Ant ant : world.getAnts())
            {    
                ant.step();
            }
        }
        
        for (int x = 0; x<=world.width(); x++)
        {
            for (int y = 0; y<=world.height(); y++)
            {
                if (world.cell(x, y).getType() == Type.ANTHILL_RED)
                {
                    redFood++;
                }
                else if (world.cell(x, y).getType() == Type.ANTHILL_BLACK)
                {
                    blackFood++;
                }
            }
        }
        
        if (redFood > blackFood)
        {
            return playerRed;
        }
        else
        {
            return playerBlack;
        }
    }
    
    public World world() {
        return world;
    }
}
