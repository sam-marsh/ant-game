package antgame;

import antgame.gui.GUI;

import javax.swing.*;

/**
 * The start of the program.
 *
 * @author Sam Marsh
 */
public class AntGame {

    //the program name, to show on the interface
    public static final String APPLICATION_NAME = "ant-game";

    /**
     * The program starts here! No program arguments required, all user
     * configuration is done through the GUI.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });
    }

}
