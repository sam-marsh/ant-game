package antgame.gui.screen;

import antgame.gui.GUI;

import javax.swing.*;

/**
 * @author Sam Marsh
 */
public abstract class View extends JPanel {

    protected final GUI context;

    public View(GUI context) {
        this.context = context;
    }

}
