/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antgame.core;

import antgame.core.brain.Brain;
import antgame.core.brain.parser.BrainParser;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

/**
 *
 * @author Regan
 */
public class Player {
    private final String name;
    private int wins;
    private final File brainFile;
    private final Brain  brain;
    
    public Player(String name, File brainFile) throws ParseException, IOException
    {
        this.name = name;
        this.brainFile = brainFile;
        wins = 0;
        brain = parseBrain();
    }
    
    public void incrementWins()
    {
        wins++;
    }
    
    public File getBrainFile()
    {
        return brainFile;
    }
    
    public Brain getBrain()
    {
        return brain; 
    } 
    
    private Brain parseBrain() throws ParseException, IOException
    {
        return BrainParser.parse(brainFile);
    }
}
