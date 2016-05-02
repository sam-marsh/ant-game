package antgame.gui;

import antgame.AntGame;
import antgame.core.Match;
import antgame.core.world.parser.WorldParser;
import antgame.gui.screen.MatchView;
import antgame.gui.screen.StartView;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

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

        try {
            MatchView view = new MatchView(this, new Match(null, null, WorldParser.parse(
                    new File("/Users/Sam/Projects/ant-game/ant-game/src/test/resources/world/ant-world-1.txt"))));
            setContentPane(view);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

}
