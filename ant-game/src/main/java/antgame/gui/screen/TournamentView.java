package antgame.gui.screen;

import antgame.core.Match;
import antgame.core.Player;
import antgame.core.Tournament;
import antgame.core.world.World;
import antgame.gui.GUI;
import javafx.util.Pair;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A view for a tournament in progress. Shows an overview of all worlds and the games currently
 * being played on them.
 *
 * @author Sam Marsh
 */
public class TournamentView extends View {

    /**
     * Creates a tournament new view, passing the main GUI instance as an argument.
     *
     * @param context the gui frame
     * @param tournament the tournament to view
     * @param speed the speed to run the tournament at - see {@link Match}
     */
    public TournamentView(GUI context, Tournament tournament, int speed) {
        super(context);

        //set width and height to be a square number to make it look even
        int size = (int) Math.ceil(Math.sqrt(tournament.numWorlds()));
        setLayout(new GridLayout(size, size));

        Set<Pair<Player, Player>> pairings = tournament.getPairings();

        //iterate over all worlds, add a new mini-panel for each world
        for (World world : tournament.worlds()) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(new EmptyBorder(5, 5, 5, 5));
            panel.setLayout(new GridLayout());

            //shuffle the pairings for this world to make it interesting
            List<Pair<Player, Player>> listPairings = new ArrayList<>(pairings);
            Collections.shuffle(listPairings);
            Iterator<Pair<Player, Player>> iterator = listPairings.iterator();

            new Thread(() -> {
                //iterate over each of these pairings
                while (iterator.hasNext()) {
                    Pair<Player, Player> pair = iterator.next();
                    //create a new match between the pair
                    Match match = new Match(pair.getKey(), pair.getValue(), new World(world));
                    MatchView view = new MatchView(context, match, 2);
                    SwingUtilities.invokeLater(() -> {
                        panel.removeAll();
                        panel.add(view, BorderLayout.CENTER);
                    });
                    //run it and then move to the next match
                    match.run(Match.NUM_ROUNDS, speed);
                }
            }).start();

            add(panel);
        }
    }

}
