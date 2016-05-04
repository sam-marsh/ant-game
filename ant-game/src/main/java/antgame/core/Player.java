/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antgame.core;

import antgame.core.brain.Brain;

/**
 * A player to participate in a match or tournament. Has a name for GUI, points for tournaments and a brain to
 * manage a colony within a Match.
 *
 * @author Regan
 */
public class Player implements Comparable<Player> {

    //Player's display name
    private final String name;

    //The ant brain that will run an assigned colony
    private final Brain  brain;

    //The number of points won by this player
    private int points;

    /**
     * Creates a new Player with the display name and brain supplied.
     *
     * @param name The desired display name of the player
     * @param brain The parsed brain to control this player's colonies
     */
    public Player(String name, Brain brain) {
        this.name = name;
        points = 0;
        this.brain = brain;
    }

    /**
     * Creates a new Match using the supplied players and world.
     *
     * @param points The number to points to add to the player's current points
     */
    public void addPoints(int points) {
        this.points += points;
    }

    /**
     * Creates a new Match using the supplied players and world.
     *
     * @return The parsed brain of this player
     */
    public Brain getBrain() {
        return brain; 
    }

    /**
     * Creates a new Match using the supplied players and world.
     *
     * @return The difference between the two player's points
     */
    @Override
    public int compareTo(Player o) {
        return points - o.points;
    }

    /**
     * Creates a new Match using the supplied players and world.
     *
     * @return The number of points this player has
     */
    public int points() {
        return points;
    }

    /**
     * Compares to see if two players are logically equal.
     *
     * @param o the other object
     * @return if the two players are the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return points == player.points && name.equals(player.name) && brain.equals(player.brain);
    }

    /**
     * @return a (hopefully) unique integer hash for this class
     */
    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + brain.hashCode();
        result = 31 * result + points;
        return result;
    }

    /**
     * @return a string representation of the player (their name)
     */
    @Override
    public String toString() {
        return name;
    }

}
