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
    private final Brain  brain;
    
    public Player(String name, Brain brain)
    {
        this.name = name;
        wins = 0;
        this.brain = brain;
    }
    
    public void addPoints(int points)
    {
        wins += points;
    }
    
    public Brain getBrain()
    {
        return brain; 
    } 

}
