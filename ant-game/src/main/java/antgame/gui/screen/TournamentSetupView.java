package antgame.gui.screen;

import antgame.core.Match;
import antgame.core.Player;
import antgame.core.Tournament;
import antgame.core.brain.Brain;
import antgame.core.brain.parser.BrainParser;
import antgame.core.world.World;
import antgame.core.world.parser.WorldParser;
import antgame.gui.GUI;
import antgame.gui.util.CentrePanel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

/**
 * A view for setting up a tournament.
 *
 * @author Sam Marsh
 */
public class TournamentSetupView extends View {

    private DefaultListModel<WorldSetup> worldModel;
    private DefaultListModel<PlayerSetup> playerModel;
    private int speed = Match.DEFAULT_MATCH_SPEED;

    /**
     * Creates a new tournament setup screen and initialises the components.
     *
     * @param context the frame
     */
    public TournamentSetupView(GUI context) {
        super(context);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;

        add(new PlayerListView(), constraints);

        constraints.gridx = 1;
        add(new WorldListView(), constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.NONE;
        add(new ConfigurationPanel(), constraints);

        constraints.gridy = 2;
        add(new CentrePanel(new TournamentStartButton()), constraints);
    }

    /**
     * The view for adding/removing players from the tournament.
     */
    private class PlayerListView extends JPanel {

        /**
         * Creates a new player list for adding/removing players.
         */
        private PlayerListView() {
            //setup layer and border as always
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(5, 5, 5, 5),
                    BorderFactory.createBevelBorder(BevelBorder.LOWERED)
            ));

            //title
            add(new CentrePanel(new JLabel("Players") {
                {
                    setFont(GUI.TITLE_FONT);
                }
            }), BorderLayout.NORTH);

            //create list model and list, add to centre of panel
            playerModel = new DefaultListModel<>();
            JList<PlayerSetup> list = new JList<>(playerModel);
            add(new JPanel() {
                {
                    setLayout(new BorderLayout());
                    setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createEmptyBorder(5, 5, 5, 5),
                            BorderFactory.createBevelBorder(BevelBorder.LOWERED)
                    ));
                    add(list, BorderLayout.CENTER);
                }
            }, BorderLayout.CENTER);

            //create add button and add associated functionality
            JButton addPlayerButton = new JButton("Add") {
                {
                    addActionListener(e -> {
                        PlayerSetup pb = new PlayerSetup();

                        //ask for name
                        pb.name = JOptionPane.showInputDialog(
                                context,
                                "What is your name?",
                                "Player Name",
                                JOptionPane.QUESTION_MESSAGE
                        );
                        if (pb.name == null)
                            return;

                        //load ant brain file
                        JFileChooser chooser = new JFileChooser();
                        int val = chooser.showOpenDialog(context);
                        if (val == JFileChooser.APPROVE_OPTION) {
                            new Thread(() -> {
                                try {
                                    pb.brain = BrainParser.parse(chooser.getSelectedFile());
                                    pb.brainFile = chooser.getSelectedFile().getName();
                                } catch (ParseException pe) {
                                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                                            context,
                                            "error parsing brain: " +
                                                    pe.getMessage() + " on line " + pe.getErrorOffset(),
                                            "parsing error",
                                            JOptionPane.ERROR_MESSAGE
                                    ));
                                    return;
                                } catch (IOException ioe) {
                                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                                            context,
                                            "error reading from file: " + ioe.getMessage(),
                                            "i/o error",
                                            JOptionPane.ERROR_MESSAGE
                                    ));
                                    return;
                                }
                                SwingUtilities.invokeLater(() -> playerModel.addElement(pb));
                            }).start();
                        }
                    });
                }
            };

            //remove button
            JButton removePlayerButton = new JButton("Remove") {
                {
                    addActionListener(e -> list.getSelectedValuesList().forEach(playerModel::removeElement));
                }
            };

            //add it all together
            add(new CentrePanel(addPlayerButton, removePlayerButton), BorderLayout.SOUTH);
        }

    }

    /**
     * Similar to the player list view - add and remove worlds from this tournament.
     */
    private class WorldListView extends JPanel {

        /**
         * Creates a new list view for adding/removing worlds.
         */
        private WorldListView() {
            //set layout and border
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(5, 5, 5, 5),
                    BorderFactory.createBevelBorder(BevelBorder.LOWERED)
            ));

            //title
            add(new CentrePanel(new JLabel("Worlds") {
                {
                    setFont(GUI.TITLE_FONT);
                }
            }), BorderLayout.NORTH);

            //list setup
            worldModel = new DefaultListModel<>();
            JList<WorldSetup> list = new JList<>(worldModel);
            add(new JPanel() {
                {
                    setLayout(new BorderLayout());
                    setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createEmptyBorder(5, 5, 5, 5),
                            BorderFactory.createBevelBorder(BevelBorder.LOWERED)
                    ));
                    add(list, BorderLayout.CENTER);
                }
            }, BorderLayout.CENTER);

            //button for adding a world
            JButton addPlayerButton = new JButton("Add") {
                {
                    addActionListener(e -> {
                        WorldSetup ws = new WorldSetup();

                        //parse the world file
                        JFileChooser chooser = new JFileChooser();
                        int val = chooser.showOpenDialog(context);
                        if (val == JFileChooser.APPROVE_OPTION) {
                            new Thread(() -> {
                                try {
                                    ws.world = WorldParser.parse(chooser.getSelectedFile());
                                    ws.worldFile = chooser.getSelectedFile().getName();
                                } catch (ParseException pe) {
                                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                                            context,
                                            "error parsing world: " +
                                                    pe.getMessage() + " on line " + pe.getErrorOffset(),
                                            "parsing error",
                                            JOptionPane.ERROR_MESSAGE
                                    ));
                                    return;
                                } catch (IOException ioe) {
                                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                                            context,
                                            "error reading from file: " + ioe.getMessage(),
                                            "i/o error",
                                            JOptionPane.ERROR_MESSAGE
                                    ));
                                    return;
                                }
                                SwingUtilities.invokeLater(() -> worldModel.addElement(ws));
                            }).start();
                        }
                    });
                }
            };

            //the remove button to get rid of a world
            JButton removePlayerButton = new JButton("Remove") {
                {
                    addActionListener(e -> list.getSelectedValuesList().forEach(worldModel::removeElement));
                }
            };

            //add buttons at bottom
            add(new CentrePanel(addPlayerButton, removePlayerButton), BorderLayout.SOUTH);
        }

    }

    /**
     * The button for initiating the tournament.
     */
    private class TournamentStartButton extends JButton {

        /**
         * Creates a tournament start button and sets up event functionality.
         */
        private TournamentStartButton() {
            setText("Start");

            //convert user data to tournament object, switch to tournament view
            addActionListener(e -> {
                Set<Player> players = new HashSet<>();
                for (Object setup : playerModel.toArray()) {
                    PlayerSetup cast = (PlayerSetup) setup;
                    players.add(new Player(cast.name, cast.brain));
                }
                Set<World> worlds = new HashSet<>();
                for (Object setup : worldModel.toArray()) {
                    WorldSetup cast = (WorldSetup) setup;
                    worlds.add(cast.world);
                }
                Tournament tournament = new Tournament(players, worlds);
                context.setContentPane(new TournamentView(context, tournament, speed));
                context.revalidate();
                context.repaint();
            });
        }

    }

    /**
     * A half-built player - not quite there! Used for storing player data as a user adds each attribute
     * one by one.
     */
    private static class PlayerSetup {

        //the name of the player
        private String name;

        //the parsed brain object
        private Brain brain;

        //where the brain was loaded from
        private String brainFile;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PlayerSetup that = (PlayerSetup) o;

            if (name != null ? !name.equals(that.name) : that.name != null) return false;
            if (brain != null ? !brain.equals(that.brain) : that.brain != null) return false;
            return brainFile != null ? brainFile.equals(that.brainFile) : that.brainFile == null;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (brain != null ? brain.hashCode() : 0);
            result = 31 * result + (brainFile != null ? brainFile.hashCode() : 0);
            return result;
        }

        /**
         * Used for looking nice in the list.
         *
         * @return the string representation of the player
         */
        @Override
        public String toString() {
            return name + ": " + brainFile;
        }

    }

    /**
     * Similar to above - an incomplete world.
     */
    private static class WorldSetup {

        //the world object (parsed)
        private World world;

        //the world file
        private String worldFile;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            WorldSetup that = (WorldSetup) o;

            if (world != null ? !world.equals(that.world) : that.world != null) return false;
            return worldFile != null ? worldFile.equals(that.worldFile) : that.worldFile == null;

        }

        @Override
        public int hashCode() {
            int result = world != null ? world.hashCode() : 0;
            result = 31 * result + (worldFile != null ? worldFile.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return worldFile;
        }

    }

    /**
     * The bottom panel for changing global tournament settings.
     */
    private class ConfigurationPanel extends JPanel {

        /**
         * Creates a configuration panel for the tournament and initialises components and events.
         */
        private ConfigurationPanel() {
            setLayout(new BorderLayout());

            //when slider value changes, change the match speed to the new value
            add(new CentrePanel(new JLabel("Simulation Speed"), new CentrePanel(new JSlider(1, 101) {
                {
                    addChangeListener((c) -> speed = getValue());
                }
            })));
        }

    }

}
