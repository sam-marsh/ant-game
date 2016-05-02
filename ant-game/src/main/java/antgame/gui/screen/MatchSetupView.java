package antgame.gui.screen;

import antgame.core.Colony;
import antgame.core.brain.parser.BrainParser;
import antgame.gui.GUI;
import antgame.gui.util.CentrePanel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author Sam Marsh
 */
public class MatchSetupView extends View {

    public MatchSetupView(GUI context) {
        super(context);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        //setup initial constraints
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 2;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        constraints.fill = GridBagConstraints.BOTH;
        add(new PlayerSetupPanel(), constraints);

        constraints.gridx = 1;
        add(new PlayerSetupPanel(), constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        add(new MatchConfigurationPanel(), constraints);

        constraints.gridy = 3;
        constraints.weightx = constraints.weighty = 0.01;
        constraints.fill = GridBagConstraints.NONE;
        add(new StartGameButton(), constraints);
    }

    private class PlayerSetupPanel extends JPanel {

        private PlayerSetupPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            add(Box.createVerticalGlue());
            add(new CentrePanel(new JLabel("player") {
                {
                    setFont(GUI.TITLE_FONT);
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
                                    BrainParser.parse(Colony.Colour.RED, chooser.getSelectedFile());
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

    private class MatchConfigurationPanel extends JPanel {

        private MatchConfigurationPanel() {
            setLayout(new FlowLayout());
            setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            add(new JLabel("test"));
        }

    }

    private class StartGameButton extends JPanel {

        private StartGameButton() {
            setLayout(new FlowLayout());
            add(new JButton("start") {
                {
                    addActionListener((e) -> {});
                }
            });
        }
    }

}
