/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antgame.core;

import antgame.core.world.World;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A tournament between any number of players on any number of worlds
 * Each player plays every other player on each side (red or black) on every world
 *
 * @author Regan
 */
public class Tournament {

    //The set of players in the tournament
    private Set<Player> players;
    //The set of worlds the matches in the tournament will be played on
    private Set<World> worlds;

    /**
     * Creates a new tournament and assignes the supplied players and worlds
     * @param players The players that will participate in the tournament
     * @param worlds The worlds the matches will be played on
     */
    public Tournament(Set<Player> players, Set<World> worlds) {
        this.players = players;
        this.worlds = worlds;
    }

    public Set<Match> getMatches() {
        Set<Match> matches = new HashSet<>();
        for (World world : worlds) {
            for (Player player1 : players) {
                for (Player player2 : players) {
                    matches.add(new Match(player1, player2, world.clone()));
                    matches.add(new Match(player2, player1, world.clone()));
                }
            }
        }
        return matches;
    }

    /**
     * Runs the tournament determining the winner.
     * @return The winning Player in the tournament
     */
    public Player runTournament() {

        //run a match between every player on every world as both red and black
        for (World world : worlds) {
            for (Player player1 : players) {
                for (Player player2 : players) {
                    //player 1 = red, player 2 = black
                    MatchOutcome outcome1 = runMatch(new Match(player1, player2, world), Integer.MAX_VALUE);
                    player1.addPoints(outcome1.getRedPlayerOutcome().getResult().getPoints());
                    player2.addPoints(outcome1.getBlackPlayerOutcome().getResult().getPoints());

                    //player 1 = black, player 2 = red
                    MatchOutcome outcome2 = runMatch(new Match(player2, player1, world), Integer.MAX_VALUE);
                    player1.addPoints(outcome2.getBlackPlayerOutcome().getResult().getPoints());
                    player2.addPoints(outcome2.getRedPlayerOutcome().getResult().getPoints());
                }
            }
        }

        //get the maximum number of points achieved by any player
        int max = 0;
        for (Player player : players) {
            if (player.points() >= max) {
                max = player.points();
            }
        }

        //count how many player have the maximum number of points
        int count = 0;
        for (Player player : players) {
            if (player.points() == max) {
                ++count;
            }
        }

        if (count == 1) {
            //we have a winning player - return it
            int cpy = max;
            return players.stream().filter(p -> p.points() == cpy).findFirst().orElseGet(null);
        } else {
            //sort by points, get the top half
            Set<Player> best = players.stream().sorted((p1, p2) -> p2.points() - p1.points())
                    .limit(Math.max(players.size() / 2, 2)).collect(Collectors.toSet());

            //run another tournament with this half
            Tournament tournament = new Tournament(best, worlds);
            return tournament.runTournament();
        }
    }

    /**
     * Runs the supplied Match and returns the outcome
     * @param match The Match to run
     * @param speed The speed at which to run the match
     * @return The outcome of the run match
     */
    private MatchOutcome runMatch(Match match, int speed) {
        match.run(Match.NUM_ROUNDS, speed);
        return match.getOutcome();
    }
    
}
