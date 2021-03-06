package antgame.gui.screen;

import antgame.core.Colony;
import antgame.core.Match;
import antgame.core.Player;
import antgame.core.brain.Brain;
import antgame.core.brain.parser.BrainParser;
import antgame.core.world.World;
import antgame.core.world.parser.WorldParser;
import antgame.gui.GUI;
import antgame.gui.util.CentrePanel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;

/**
 * The view for constructing a match.
 *
 * @author Sam Marsh
 */
public class MatchSetupView extends View {

    //used to indicate that something still needs to be done by the user
    private static final Color INCOMPLETE_COLOUR = new Color(255, 108, 0);

    /**
     * Creates a new match setup view and initialises the components.
     *
     * @param context the main frame
     */
    public MatchSetupView(GUI context) {
        super(context);

        //create the incomplete players and match
        PlayerBuilder p1 = new PlayerBuilder();
        p1.colour = Colony.Colour.RED;
        PlayerBuilder p2 = new PlayerBuilder();
        p2.colour = Colony.Colour.BLACK;
        MatchBuilder m1 = new MatchBuilder();

        //use flexible layout for this
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        //used for notifying the user of what they still need to do
        PlayerStatusPanel psp1 = new PlayerStatusPanel(p1);
        PlayerStatusPanel psp2 = new PlayerStatusPanel(p2);
        MatchStatusPanel msp1 = new MatchStatusPanel(m1);

        //setup initial constraints
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        constraints.fill = GridBagConstraints.BOTH;
        //add the panel
        add(new PlayerSetupPanel(p1, psp1), constraints);

        //move along one, add the other player panel
        constraints.gridx = 1;
        add(new PlayerSetupPanel(p2, psp2), constraints);

        //add the checklist stuff
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = constraints.weighty = 0.01;
        add(new CentrePanel(new JLabel() {
            {
                setText("Checklist");
                setFont(GUI.TITLE_FONT);
            }
        }), constraints);

        //add the player status 2
        constraints.weightx = constraints.weighty = 0.1;
        constraints.gridwidth = 1;
        constraints.gridy = 2;
        constraints.gridx = 1;
        add(psp2, constraints);

        //player status 1
        constraints.gridx = 0;
        add(psp1, constraints);

        //match status panel
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        add(msp1, constraints);

        //where you can change options
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(new MatchConfigurationPanel(m1, msp1), constraints);

        //the final button
        constraints.gridy = 5;
        constraints.weightx = constraints.weighty = 0.01;
        constraints.fill = GridBagConstraints.NONE;
        add(new StartGameButton(m1, p1, p2), constraints);
    }

    /**
     * The panel for configuring a player's options.
     */
    private class PlayerSetupPanel extends JPanel {

        /**
         * Creates a new player setup panel - one for red, one for black.
         *
         * @param pb the incomplete player to update with user data
         * @param psp the status panel to update
         */
        private PlayerSetupPanel(PlayerBuilder pb, PlayerStatusPanel psp) {
            //fill centre
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(new BevelBorder(BevelBorder.LOWERED));
            add(Box.createVerticalGlue());
            //add title containing player type
            add(new CentrePanel(new JLabel("Black") {
                {
                    if (pb.colour == Colony.Colour.RED) {
                        setForeground(Color.RED);
                        setText("Red");
                    }
                    setFont(GUI.TITLE_FONT);
                }
            }));
            //add option to set name
            add(new CentrePanel(new JButton("Set Name") {
                {
                    addActionListener((e) -> {
                        String result = JOptionPane.showInputDialog(
                                context, "Enter your name:", "Name",
                                JOptionPane.QUESTION_MESSAGE
                        );
                        if (result != null) {
                            pb.name = result;
                            SwingUtilities.invokeLater(psp::refresh);
                        }
                    });
                }
            }));
            //add option to upload ant brain
            add(new CentrePanel(new JButton("Upload Brain") {
                {
                    addActionListener((e) -> {
                        JFileChooser chooser = new JFileChooser();
                        int val = chooser.showOpenDialog(context);
                        if (val == JFileChooser.APPROVE_OPTION) {
                            new Thread(() -> {
                                try {
                                    pb.brain = BrainParser.parse(chooser.getSelectedFile());
                                    SwingUtilities.invokeLater(psp::refresh);
                                } catch (ParseException pe) {
                                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                                            context,
                                            "Error parsing brain: " +
                                                    pe.getMessage() + " on line " + pe.getErrorOffset(),
                                            "Parsing error",
                                            JOptionPane.ERROR_MESSAGE
                                    ));
                                } catch (IOException ioe) {
                                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                                            context,
                                            "Error reading from file: " + ioe.getMessage(),
                                            "I/O error",
                                            JOptionPane.ERROR_MESSAGE
                                    ));
                                }
                            }).start();
                        }
                    });
                }
            }));
        }


    }

    /**
     * A panel to display whether the match setup is complete or not.
     */
    private class MatchStatusPanel extends JPanel {

        //the incomplete match to add data to
        private final MatchBuilder match;
        //the label indicating the status
        private final JLabel world;

        /**
         * Creates a new status panel, monitoring the progress of a match setup.
         *
         * @param builder the incomplete match setup
         */
        private MatchStatusPanel(MatchBuilder builder) {
            this.match = builder;
            this.world = new JLabel("World");
            world.setForeground(INCOMPLETE_COLOUR);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            add(new CentrePanel(world));
        }

        private void refresh() {
            if (match.world != null) {
                world.setText("World: uploaded!");
                world.setForeground(Color.GREEN);
            } else {
                world.setText("World");
                world.setForeground(INCOMPLETE_COLOUR);
            }
        }

    }

    /**
     * A panel indicating the status of a player's setup. Whether the player has added
     * a name and brain yet.
     */
    private class PlayerStatusPanel extends JPanel {

        //the incomplete player
        private final PlayerBuilder player;
        //the label showing whether the name has been added
        private final JLabel name;
        //the label showing whether the brain has been added
        private final JLabel brain;

        /**
         * Creates a new status panel for displaying whether a user has completed their team setup.
         *
         * @param builder the incomplete player
         */
        private PlayerStatusPanel(PlayerBuilder builder) {
            this.player = builder;

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            this.name = new JLabel("Name");
            name.setForeground(INCOMPLETE_COLOUR);
            name.setBorder(new EmptyBorder(5, 5, 5, 5));

            this.brain = new JLabel("Brain");
            brain.setForeground(INCOMPLETE_COLOUR);
            brain.setBorder(new EmptyBorder(5, 5, 5, 5));

            add(new CentrePanel(name));
            add(new CentrePanel(brain));
        }

        /**
         * Called when the status of the player builder changes.
         */
        public void refresh() {
            if (player.name != null) {
                name.setForeground(Color.GREEN);
                name.setText("Name: " + player.name);
            } else {
                name.setForeground(INCOMPLETE_COLOUR);
                name.setText("Name");
            }
            if (player.brain != null) {
                brain.setForeground(Color.GREEN);
                brain.setText("Brain: uploaded!");
            } else {
                brain.setForeground(INCOMPLETE_COLOUR);
                brain.setText("Brain");
            }
        }

    }

    /**
     * A panel for configuration of general match settings, like the world and the playback speed.
     */
    private class MatchConfigurationPanel extends JPanel {

        /**
         * Creates a new match configuration panel.
         *
         * @param mb the incomplete match settings
         * @param msp the status panel for displaying progress
         */
        private MatchConfigurationPanel(MatchBuilder mb, MatchStatusPanel msp) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            //add title
            add(new CentrePanel(new JLabel("Configuration") {
                {
                    setFont(GUI.TITLE_FONT);
                }
            }));
            //add a button to upload the world
            add(new CentrePanel(new JButton("Upload Ant-World") {
                {
                    addActionListener(e -> {
                        JFileChooser chooser = new JFileChooser();
                        int val = chooser.showOpenDialog(context);
                        if (val == JFileChooser.APPROVE_OPTION) {
                            new Thread(() -> {
                                try {
                                    mb.world = WorldParser.parse(chooser.getSelectedFile());
                                    msp.refresh();
                                } catch (ParseException pe) {
                                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                                            context,
                                            "Error parsing world: " +
                                                    pe.getMessage() + " on line " + pe.getErrorOffset(),
                                            "Parsing Error",
                                            JOptionPane.ERROR_MESSAGE
                                    ));
                                } catch (IOException ioe) {
                                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                                            context,
                                            "Error reading from file: " + ioe.getMessage(),
                                            "I/O Error",
                                            JOptionPane.ERROR_MESSAGE
                                    ));
                                }
                            }).start();
                        }
                    });
                }
            }));
            //add a slider to set the game speed
            add(new CentrePanel(new JLabel("Simulation Speed"), new CentrePanel(new JSlider(1, 101) {
                {
                    addChangeListener((c) -> mb.speed = getValue());
                }
            })));
        }

    }

    /**
     * The button for starting a game.
     */
    private class StartGameButton extends JPanel {

        /**
         * Creates a start-game button.
         *
         * @param m the match to start
         * @param p1 the player 1 (red)
         * @param p2 the player 2 (black)
         */
        private StartGameButton(MatchBuilder m, PlayerBuilder p1, PlayerBuilder p2) {
            setLayout(new FlowLayout());
            add(new JButton("Start") {
                {
                    addActionListener(e -> {
                        //check if complete
                        if (m.complete() && p1.complete() && p2.complete()) {
                            //create a match
                            Match match = new Match(
                                    new Player(p1.name, p1.brain),
                                    new Player(p2.name, p2.brain),
                                    m.world
                            );
                            //switch to match view and repaint
                            context.setContentPane(new MatchView(context, match));
                            context.revalidate();
                            context.repaint();
                            //run in new thread
                            new Thread(() -> match.run(Match.NUM_ROUNDS, m.speed)).start();
                        } else {
                            //give an error - not complete
                            JOptionPane.showMessageDialog(
                                    context,
                                    "Setup not complete!",
                                    "Incomplete!",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    });
                }
            });
        }
    }

    /**
     * Simple class for storing incomplete player properties.
     */
    private class PlayerBuilder {

        private String name;
        private Brain brain;
        private Colony.Colour colour;

        private boolean complete() {
            return name != null && brain != null;
        }

    }

    /**
     * Simple class for storing match properties.
     */
    private class MatchBuilder {

        private World world;
        private int speed = Match.DEFAULT_MATCH_SPEED;

        private boolean complete() {
            return world != null;
        }

    }

}
