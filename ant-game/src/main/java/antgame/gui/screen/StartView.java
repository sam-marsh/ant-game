package antgame.gui.screen;

import antgame.AntGame;
import antgame.gui.GUI;
import antgame.gui.util.CentrePanel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * @author Sam Marsh
 */
public class StartView extends View {

    private static final int a = 0;

    public StartView(GUI gui) {
        super(gui);
        add(Box.createVerticalGlue());
        add(new CentrePanel(new JLabel(AntGame.APPLICATION_NAME) {
            {
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createBevelBorder(BevelBorder.LOWERED),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
                setFont(GUI.TITLE_FONT);
            }
        }));
        add(new CentrePanel(new JLabel("start a new game below") {
            {
                setFont(GUI.TITLE_FONT);
            }
        }));
        add(new CentrePanel(new GameButton(), new TournamentButton()));
        add(new CentrePanel(new JLabel("or, generate a random contest world") {
            {
                setFont(GUI.TITLE_FONT);
            }
        }));
        add(new CentrePanel(new WorldGeneratorButton()));
        add(Box.createVerticalGlue());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    private class GameButton extends JButton {

        private GameButton() {
            setText("two-player match");
            addActionListener(e -> {
                context.setContentPane(new MatchSetupView(context));
                context.revalidate();
                context.repaint();
            });
        }

    }

    private class TournamentButton extends JButton {

        private TournamentButton() {
            setText("tournament");
            addActionListener(e -> {
                context.setContentPane(new TournamentSetupView(context));
                context.revalidate();
                context.repaint();
            });
        }

    }

    private class WorldGeneratorButton extends JButton {

        private WorldGeneratorButton() {
            setText("generate world");
            addActionListener(e -> {});
        }

    }
}
