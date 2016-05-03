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
 * @author Sam Marsh
 */
public class TournamentView extends View {

    /**
     * Creates a tournament new view, passing the main GUI instance as an argument.
     *
     * @param context the gui frame
     */
    public TournamentView(GUI context, Tournament tournament, int speed) {
        super(context);

        int size = (int) Math.ceil(Math.sqrt(tournament.numWorlds()));
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        setLayout(new GridLayout(size, size));

        Set<Pair<Player, Player>> pairings = tournament.getPairings();

        for (World world : tournament.worlds()) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(new EmptyBorder(5, 5, 5, 5));
            panel.setLayout(new GridLayout());

            List<Pair<Player, Player>> listPairings = new ArrayList<>(pairings);
            Collections.shuffle(listPairings);
            Iterator<Pair<Player, Player>> iterator = listPairings.iterator();

            new Thread(() -> {
                while (iterator.hasNext()) {
                    Pair<Player, Player> pair = iterator.next();
                    Match match = new Match(pair.getKey(), pair.getValue(), new World(world));
                    MatchView view = new MatchView(context, match);
                    SwingUtilities.invokeLater(() -> {
                        panel.removeAll();
                        panel.add(view, BorderLayout.CENTER);
                    });
                    match.run(Match.NUM_ROUNDS, speed);
                }
            }).start();

            add(panel);
        }
    }

}
