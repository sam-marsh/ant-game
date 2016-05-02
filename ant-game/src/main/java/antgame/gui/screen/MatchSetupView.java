package antgame.gui.screen;

import antgame.core.Colony;
import antgame.core.Match;
import antgame.core.MatchOutcome;
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
                setText("checklist");
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

    private class PlayerSetupPanel extends JPanel {

        private PlayerSetupPanel(PlayerBuilder pb, PlayerStatusPanel psp) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(new BevelBorder(BevelBorder.LOWERED));
            add(Box.createVerticalGlue());
            add(new CentrePanel(new JLabel("black player") {
                {
                    if (pb.colour == Colony.Colour.RED) {
                        setForeground(Color.RED);
                        setText("red player");
                    }
                    setFont(GUI.TITLE_FONT);
                }
            }));
            add(new CentrePanel(new JButton("set name") {
                {
                    addActionListener((e) -> {
                        String result = JOptionPane.showInputDialog(
                                context, "enter your name", "name",
                                JOptionPane.QUESTION_MESSAGE
                        );
                        if (result != null) {
                            pb.name = result;
                            SwingUtilities.invokeLater(psp::refresh);
                        }
                    });
                }
            }));
            add(new CentrePanel(new JButton("upload your ant-brain") {
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
                                            "error parsing brain: " +
                                                    pe.getMessage() + " on line " + pe.getErrorOffset(),
                                            "parsing error",
                                            JOptionPane.ERROR_MESSAGE
                                    ));
                                } catch (IOException ioe) {
                                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                                            context,
                                            "error reading from file: " + ioe.getMessage(),
                                            "i/o error",
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

    private class MatchStatusPanel extends JPanel {

        private final MatchBuilder match;
        private final JLabel world;

        private MatchStatusPanel(MatchBuilder builder) {
            this.match = builder;
            this.world = new JLabel("world");
            world.setForeground(INCOMPLETE_COLOUR);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            add(new CentrePanel(world));
        }

        private void refresh() {
            if (match.world != null) {
                world.setText("world: uploaded");
                world.setForeground(Color.GREEN);
            } else {
                world.setText("world");
                world.setForeground(INCOMPLETE_COLOUR);
            }
        }

    }


    private class PlayerStatusPanel extends JPanel {

        private final PlayerBuilder player;
        private final JLabel name;
        private final JLabel brain;

        private PlayerStatusPanel(PlayerBuilder builder) {
            this.player = builder;

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            this.name = new JLabel("name");
            name.setForeground(INCOMPLETE_COLOUR);
            name.setBorder(new EmptyBorder(5, 5, 5, 5));

            this.brain = new JLabel("brain");
            brain.setForeground(INCOMPLETE_COLOUR);
            brain.setBorder(new EmptyBorder(5, 5, 5, 5));

            add(new CentrePanel(name));
            add(new CentrePanel(brain));
        }

        public void refresh() {
            if (player.name != null) {
                name.setForeground(Color.GREEN);
                name.setText("name: " + player.name);
            } else {
                name.setForeground(INCOMPLETE_COLOUR);
                name.setText("name");
            }
            if (player.brain != null) {
                brain.setForeground(Color.GREEN);
                brain.setText("brain: uploaded");
            } else {
                brain.setForeground(INCOMPLETE_COLOUR);
                brain.setText("brain");
            }
        }

    }

    private class MatchConfigurationPanel extends JPanel {

        private MatchConfigurationPanel(MatchBuilder mb, MatchStatusPanel msp) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            add(new CentrePanel(new JLabel("configuration") {
                {
                    setFont(GUI.TITLE_FONT);
                }
            }));
            add(new CentrePanel(new JButton("upload ant world") {
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
                                            "error parsing brain: " +
                                                    pe.getMessage() + " on line " + pe.getErrorOffset(),
                                            "parsing error",
                                            JOptionPane.ERROR_MESSAGE
                                    ));
                                } catch (IOException ioe) {
                                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                                            context,
                                            "error reading from file: " + ioe.getMessage(),
                                            "i/o error",
                                            JOptionPane.ERROR_MESSAGE
                                    ));
                                }
                            }).start();
                        }
                    });
                }
            }));
            add(new CentrePanel(new JLabel("simulation speed"), new CentrePanel(new JSlider(1, 100) {
                {
                    addChangeListener((c) -> mb.speed = getValue());
                }
            })));
        }

    }

    private class StartGameButton extends JPanel {

        private StartGameButton(MatchBuilder m, PlayerBuilder p1, PlayerBuilder p2) {
            setLayout(new FlowLayout());
            add(new JButton("start") {
                {
                    addActionListener(e -> {
                        if (m.complete() && p1.complete() && p2.complete()) {
                            Match match = new Match(
                                    new Player(p1.name, p1.brain),
                                    new Player(p2.name, p2.brain),
                                    m.world
                            );
                            context.setContentPane(new MatchView(context, match));
                            context.revalidate();
                            context.repaint();
                            new Thread(() -> match.run(Match.NUM_ROUNDS, m.speed)).start();
                        } else {
                            JOptionPane.showMessageDialog(
                                    context,
                                    "setup not complete",
                                    "incomplete",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    });
                }
            });
        }
    }

    private class PlayerBuilder {

        private String name;
        private Brain brain;
        private Colony.Colour colour;

        private boolean complete() {
            return name != null && brain != null;
        }

    }

    private class MatchBuilder {

        private World world;
        private int speed = 50;

        private boolean complete() {
            return world != null;
        }

    }
}
