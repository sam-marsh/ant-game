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
    public Player playTournament(ArrayList<Player> players, ArrayList<File> worldFiles, int numOfWorlds) throws ParseException, IOException
    {
        ArrayList<World> worlds = new ArrayList<>();
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
    
    public Player playMatch(Player player1, Player player2, File worldFile) throws ParseException, IOException
    {
        World world = null;
        Match match = null;
        if (worldFile != null)
        {
            world = WorldParser.parse(worldFile);
        }
        else
        {
            world = WorldBuilder.generateContestWorld();
        }
        
        match = new Match(player1, player2, world);
        return match.run(300000);
    }
    
    
}
