/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antgame.core;

import antgame.core.world.World;
import antgame.core.world.builder.WorldBuilder;
import antgame.core.world.parser.WorldParser;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Regan
 */
public class Game {
    
    /**
     * Runs a tournament using the input players and worlds from the UI
     *
     * @param players The players that will participate in the tournament
     * @param worldFiles The world files to be parsed (null if all worlds will be generated)
     * @param numOfWorlds The total number of worlds in the tournament
     * @return The player that won the tournament
     * @throws ParseException if any of the supplied world's specifications are invalid - Should be caught by UI?
     * @throws IOException if an I/O exception occurred in reading any of the supplied world file contents - Should be caught by UI?
     */
    public Player playTournament(Set<Player> players, Set<File> worldFiles, int numOfWorlds) throws
            ParseException, IOException
    {
        Set<World> worlds = new HashSet<>();
        Tournament tournament = null;
                
        for (File worldFile : worldFiles)
        {
            worlds.add(WorldParser.parse(worldFile));
        }
        
        if (worlds.size() == numOfWorlds)
        {
            worlds.add(WorldBuilder.generateContestWorld());
        }
        
        tournament = new Tournament(players, worlds);
        return tournament.runTournament();
    }
    
    
}
