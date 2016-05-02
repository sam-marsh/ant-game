/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antgame.core;

import antgame.core.world.World;
import java.util.ArrayList;

/**
 *
 * @author Regan
 */
public class Tournament {
    
    private ArrayList<Player> players;
    private ArrayList<World> worlds;
    
    public Tournament(ArrayList<Player> players, ArrayList<World> worlds)
    {
        this.players = players;
        this.worlds = worlds;
    }
    
    public Player runTournament()
    {
        for (World world : worlds)
        {
            for (Player playeri : players)
            {
                for (Player playerj : players)
                {
                    MatchOutcome outcome = runMatch(new Match(playeri, playerj, world), 1000);
                    playeri.addPoints(outcome.getRedPlayerOutcome().getResult().getPoints());
                    playerj.addPoints(outcome.getBlackPlayerOutcome().getResult().getPoints());
                }
            }
        }
        
        return null;
    }
    
    private MatchOutcome runMatch(Match match, int speed)
    {
        return match.run(300000, speed);
    }
    
}
