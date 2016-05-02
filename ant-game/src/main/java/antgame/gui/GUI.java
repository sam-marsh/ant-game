package antgame.gui;

import antgame.AntGame;
import antgame.gui.screen.StartView;

import javax.swing.*;
import java.awt.*;

/**
 * @author Sam Marsh
 */
public class GUI extends JFrame {

    public static final Font TITLE_FONT = new Font("Courier New", Font.BOLD, 20);

    private static final int GUI_WIDTH = 800;
    private static final int GUI_HEIGHT = 600;

    public GUI() {
        //set initial size
        setSize(GUI_WIDTH, GUI_HEIGHT);
        //quit application on close
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //place in centre of screen
        setLocationRelativeTo(null);
        //set title
        setTitle(AntGame.APPLICATION_NAME);

        StartView start = new StartView(this);
        setContentPane(start);
    }

}
