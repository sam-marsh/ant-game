/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antgame.core;

import antgame.core.brain.Brain;

/**
 *
 * @author Regan
 */
public class Player implements Comparable<Player> {

    private final String name;
    private final Brain  brain;
    private int points;

    public Player(String name, Brain brain)
    {
        this.name = name;
        points = 0;
        this.brain = brain;
    }
    
    public void addPoints(int points)
    {
        this.points += points;
    }
    
    public Brain getBrain()
    {
        return brain; 
    }

    @Override
    public int compareTo(Player o) {
        return points - o.points;
    }

    public int points() {
        return points;
    }

}
