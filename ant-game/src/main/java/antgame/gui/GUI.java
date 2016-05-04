package antgame.gui;

import antgame.AntGame;
import antgame.gui.screen.StartView;

import javax.swing.*;
import java.awt.*;

/**
 * The program's GUI.
 *
 * @author Sam Marsh
 */
public class GUI extends JFrame {

    //the font to use for all titles
    public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 20);

    //the default width and height
    private static final int GUI_WIDTH = 1000;
    private static final int GUI_HEIGHT = 600;

    /**
     * Initialises the GUI, adds all components.
     */
    public GUI() {
        //set initial size
        setSize(GUI_WIDTH, GUI_HEIGHT);
        //quit application on close
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //place in centre of screen
        setLocationRelativeTo(null);
        //set title
        setTitle(AntGame.APPLICATION_NAME);

        //add the content
        setContentPane(new StartView(this));
    }

}
