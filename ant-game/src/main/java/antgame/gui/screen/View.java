package antgame.gui.screen;

import antgame.gui.GUI;

import javax.swing.*;

/**
 * Represents one of the different 'main screens' that may be showing in the program.
 *
 * @author Sam Marsh
 */
public abstract class View extends JPanel {

    //the gui instance
    protected final GUI context;

    /**
     * Creates a new view, passing the main GUI instance as an argument.
     *
     * @param context the gui frame
     */
    public View(GUI context) {
        this.context = context;
    }

}
