package antgame.gui.util;

import javax.swing.*;
import java.awt.*;

/**
 * @author Sam Marsh
 */
public class CentrePanel extends JPanel {

    public CentrePanel(JComponent... components) {
        setLayout(new FlowLayout());
        for (Component c : components) {
            add(c);
        }
    }

}
