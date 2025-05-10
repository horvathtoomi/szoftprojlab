package main.java;

import javax.swing.*;
import java.awt.*;

/**
 * A játékbeli menü megvalósításáért felelős osztály.
 */
public class GameMenu extends JMenuBar {

        JButton b1, b2, b3;
        JFrame frame;

        public GameMenu(JFrame frame) {
            this.frame = frame;
            setBackground(Color.LIGHT_GRAY);

            //Gombok
            b1 = new JButton("Save");
            b2 = new JButton("Load");
            b3 = new JButton("Exit");

            b1.addActionListener(e -> System.out.println("save"));
            b2.addActionListener(e -> System.out.println("load"));
            b3.addActionListener(e -> {
                System.out.println("exit");
                frame.setJMenuBar(null);
                frame.getContentPane().removeAll();
                MainMenu menu = new MainMenu(frame);
                frame.add(menu); frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                MenuSelectionManager.defaultManager().clearSelectedPath();
                frame.revalidate();
                frame.repaint();
            });

            JMenu menu = new JMenu("Menu");

            menu.add(b1);
            menu.add(b2);
            menu.add(b3);
            this.add(menu);
        }
}
