/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antgame.core;

import antgame.core.brain.Brain;
import java.io.File;

/**
 *
 * @author Regan
 */
public class Player {
    private final String name;
    private int wins;
    private final File brainFile;
    
    public Player(String name, File brainFile)
    {
        this.name = name;
        this.brainFile = brainFile;
        wins = 0;
    }
    
    public void incrementWins()
    {
        wins++;
    }
}
