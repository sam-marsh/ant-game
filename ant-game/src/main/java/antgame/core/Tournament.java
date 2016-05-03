/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antgame.core;

import antgame.core.world.World;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Regan
 */
public class Tournament {
    
    private Set<Player> players;
    private Set<World> worlds;
    
    public Tournament(Set<Player> players, Set<World> worlds) {
        this.players = players;
        this.worlds = worlds;
    }
    
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
    
    private MatchOutcome runMatch(Match match, int speed) {
        match.run(Match.NUM_ROUNDS, speed);
        return match.getOutcome();
    }
    
}
