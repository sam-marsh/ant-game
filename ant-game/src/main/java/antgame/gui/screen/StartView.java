package antgame.gui.screen;

import antgame.AntGame;
import antgame.core.world.World;
import antgame.core.world.builder.WorldBuilder;
import antgame.gui.GUI;
import antgame.gui.util.CentrePanel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * The view which is presented to the user when they start the program.
 *
 * @author Sam Marsh
 */
public class StartView extends View {

    /**
     * Creates a new instance of this view and initialises the components.
     *
     * @param gui the main frame instance
     */
    public StartView(GUI gui) {
        super(gui);

        //set to a vertical layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalGlue());

        //add the title
        add(new CentrePanel(new JLabel(AntGame.APPLICATION_NAME) {
            {
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createBevelBorder(BevelBorder.LOWERED),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
                setFont(GUI.TITLE_FONT);
            }
        }));

        //add a label
        add(new CentrePanel(new JLabel("start a new game below...") {
            {
                setFont(GUI.TITLE_FONT);
            }
        }));

        //add the buttons for starting different types of game
        add(new CentrePanel(new MatchButton(), new TournamentButton()));

        //add another label
        add(new CentrePanel(new JLabel("...or, generate a random contest world") {
            {
                setFont(GUI.TITLE_FONT);
            }
        }));

        //add the world generator screen
        add(new CentrePanel(new WorldGeneratorButton()));

        add(Box.createVerticalGlue());
    }

    /**
     * The button which is clicked to go to the match setup screen.
     */
    private class MatchButton extends JButton {

        /**
         * Initialises the match button's components.
         */
        private MatchButton() {
            setText("two-player match");
            addActionListener(e -> {
                context.setContentPane(new MatchSetupView(context));
                context.revalidate();
                context.repaint();
            });
        }

    }

    /**
     * The button which is clicked to go to the tournament setup screen.
     */
    private class TournamentButton extends JButton {

        /**
         * Initialises the tournament button's properties.
         */
        private TournamentButton() {
            setText("tournament");
            addActionListener(e -> {
                context.setContentPane(new TournamentSetupView(context));
                context.revalidate();
                context.repaint();
            });
        }

    }

    /**
     * The button which is clicked to generate a new contest world.
     */
    private class WorldGeneratorButton extends JButton {

        /**
         * Initialises the properties of this button.
         */
        private WorldGeneratorButton() {
            setText("generate world");
            addActionListener(e -> {
                //disable button to indicate it is working
                setText("please wait...");
                setEnabled(false);

                //execute in parallel
                new Thread(() -> {
                    //create world and convert to text
                    World world = WorldBuilder.generateContestWorld();
                    List<String> strings = WorldBuilder.toText(world);

                    //switch back to gui thread
                    SwingUtilities.invokeLater(() -> {
                        //re-enable button
                        setText("generate world");
                        setEnabled(true);

                        //save the file
                        JFileChooser chooser = new JFileChooser();
                        int val = chooser.showSaveDialog(context);
                        if (val == JFileChooser.APPROVE_OPTION) {
                            try {
                                Files.write(chooser.getSelectedFile().toPath(), strings, StandardOpenOption.CREATE);
                            } catch (IOException e1) {
                                JOptionPane.showMessageDialog(
                                        context,
                                        e1.getMessage(),
                                        "failed to save",
                                        JOptionPane.ERROR_MESSAGE
                                );
                            }
                        }
                    });
                }).start();
            });
        }

    }
}
