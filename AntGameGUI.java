/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antgame;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;

/**
 *
 * @author Kea
 */
public final class AntGameGUI {

    public AntGameGUI() {
        startScreen();
    }

    public void startScreen() {

        final JFileChooser BrainChooser = new JFileChooser();
//        final JFileChooser Team2Brain = new JFileChooser();
        final JFileChooser AntWorld = new JFileChooser();
        Component brainComponent = null;
        Component worldComponent = null;

        int returnValteam1 = BrainChooser.showOpenDialog(brainComponent);
//        int returnValteam2 = Team2Brain.showOpenDialog(brainComponent);
        int returnvalBrain = AntWorld.showOpenDialog(worldComponent);

        final JFrame frame = new JFrame("Ant Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gPanel = new GridBagConstraints();


        final JLabel OpenBrain1, OpenBrain2, OpenWorld;
        OpenBrain1 = new JLabel("Upload ant brain for team 1");
        OpenBrain2 = new JLabel("Upload ant brain for team 2");
        OpenWorld = new JLabel("Upload ant world");

        JButton startGameButton;
        startGameButton = new JButton("Start game");
        startGameButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public void gameScreen() {
        final JFrame frame = new JFrame("Ant Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new SpringLayout());




        JButton startGameButton;
        startGameButton = new JButton("Start game");
        startGameButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    
    public void statsScreen() {
        final JFrame frame = new JFrame("Statistics");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
