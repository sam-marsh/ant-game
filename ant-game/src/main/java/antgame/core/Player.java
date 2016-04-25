/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antgame.core;

/**
 *
 * @author Regan
 */
public class Player {
    private int wins;
    
    public Player()
    {
        wins = 0;
    }
    
    public void incrementWins()
    {
        wins++;
    }
}
