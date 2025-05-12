package main.java;

import main.java.view.UtilityTool;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A játékbeli menü megvalósításáért felelős osztály.
 */
public class GameMenu extends JMenuBar {

        GameController gameController;
        JButton b1, b2, b3;
        JFrame frame;

        public GameMenu(JFrame frame, GameController gameController) {
            this.frame = frame;
            this.gameController = gameController;
            setBackground(Color.LIGHT_GRAY);
            UtilityTool uTool = new UtilityTool();

            //Gombok
            BufferedImage b1Image = uTool.load(MainMenu.prefix + "b2.png");

            b1 = new JButton("");
            b2 = new JButton("Load");
            b3 = new JButton("Exit");
            b1.setMargin(new Insets(0,0,0,0));
            b1.setIcon(new ImageIcon(b1Image));

            Color baseColor = new Color(47, 84, 39);
            Color hoverColor = new Color(75, 125, 64);

            b1.setContentAreaFilled(true);
            b1.setBorderPainted(true);
            b1.setOpaque(true);
            b1.setForeground(Color.WHITE);
            b1.setBackground(baseColor);

            b2.setContentAreaFilled(true);
            b2.setBorderPainted(true);
            b2.setOpaque(true);
            b2.setForeground(Color.WHITE);
            b2.setBackground(baseColor);

            b3.setContentAreaFilled(true);
            b3.setBorderPainted(true);
            b3.setOpaque(true);
            b3.setForeground(Color.WHITE);
            b3.setBackground(baseColor);

            b1.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    b1.setBackground(hoverColor);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    b1.setBackground(baseColor);
                }
            });

            b2.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    b2.setBackground(hoverColor);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    b2.setBackground(baseColor);
                }
            });

            b3.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    b3.setBackground(hoverColor);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    b3.setBackground(baseColor);
                }
            });

            b1.addActionListener(e -> {
                if (GameFileChooser.saveGame(frame, gameController)) {
                    JOptionPane.showMessageDialog(frame, "Játékállapot sikeresen elmentve!", "Mentés sikeres", JOptionPane.INFORMATION_MESSAGE);
                }
            });

            b2.addActionListener(e -> {
                if (GameFileChooser.loadGame(frame, frame)) {
                    JOptionPane.showMessageDialog(frame, "Játékállás sikeresen betöltve!", "Betöltés sikeres", JOptionPane.INFORMATION_MESSAGE);
                }
            });

            b3.addActionListener(e -> {
                System.out.println("exit");
                frame.setJMenuBar(null);
                frame.getContentPane().removeAll();
                MainMenu menu = new MainMenu(frame);
                menu.setBackground(new Color(6, 26, 14));
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.add(menu);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                MenuSelectionManager.defaultManager().clearSelectedPath();
                frame.revalidate();
                frame.repaint();
            });

            JMenu menu = new JMenu("Menu");

            Dimension size = new Dimension(150, 25);

            b1.setPreferredSize(size);
            b1.setMinimumSize(size);
            b1.setMaximumSize(size);

            b2.setPreferredSize(size);
            b2.setMinimumSize(size);
            b2.setMaximumSize(size);

            b3.setPreferredSize(size);
            b3.setMinimumSize(size);
            b3.setMaximumSize(size);

            this.setPreferredSize(new Dimension(100, 20));

            menu.add(b1);
            menu.add(b2);
            menu.add(b3);

            this.add(menu);
        }
}
