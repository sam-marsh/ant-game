package antgame.gui.util;

import javax.swing.*;
import java.awt.*;

/**
 * A utility for conveniently centering a number of components.
 *
 * @author Sam Marsh
 */
public class CentrePanel extends JPanel {

    /**
     * Creates a new centred layout with the given components laid out horizontally.
     *
     * @param components the components to add
     */
    public CentrePanel(JComponent... components) {
        setLayout(new FlowLayout());
        for (Component c : components) {
            add(c);
        }
    }

}
