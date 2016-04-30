/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
}
