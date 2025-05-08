package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A játékbeli menü megvalósításáért felelős osztály.
 */
public class GameMenu extends JMenuBar {

        JButton b1, b2, b3;

        public GameMenu() {

            setBackground(Color.RED);

            //Gombok
            b1 = new JButton("Save");
            b2 = new JButton("Load");
            b3 = new JButton("Exit");

            b1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("save");
                }
            });
            b2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("load");
                }
            });
            b3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("exit");
                }
            });

            JMenu menu = new JMenu("MenuGeci");

            menu.add(b1);
            menu.add(b2);
            menu.add(b3);
            this.add(menu);

        }

}
