package antgame.gui.screen;

import antgame.core.Match;
import antgame.core.MatchOutcome;
import antgame.core.Player;
import antgame.core.Tournament;
import antgame.core.world.World;
import antgame.gui.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Sam Marsh
 */
public class TournamentView extends View {

    /**
     * Creates a tournament new view, passing the main GUI instance as an argument.
     *
     * @param context the gui frame
     */
    public TournamentView(GUI context, Tournament tournament) {
        super(context);

        setLayout(new GridLayout(4, 4));

        Set<Match> matches = tournament.getMatches();

        Iterator<Match> iterator = matches.iterator();
        for (int x = 0; x < 4; ++x) {
            for (int y = 0; y < 4; ++y) {
                if (iterator.hasNext()) {
                    Match match = iterator.next();
                    add(new JPanel() {
                        {
                            setBorder(new EmptyBorder(5, 5, 5, 5));
                            setLayout(new BorderLayout());
                            add(new MatchView(context, match));
                            new Thread(() -> match.run(Match.NUM_ROUNDS, Match.DEFAULT_MATCH_SPEED)).start();
                        }
                    });
                }
            }
        }
    }

}
