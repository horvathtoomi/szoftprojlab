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

        Color baseColor = new Color(47, 84, 39);
        Color hoverColor = new Color(75, 125, 64);

        public GameMenu(JFrame frame, GameController gameController) {
            this.frame = frame;
            this.gameController = gameController;
            setBackground(Color.LIGHT_GRAY);
            UtilityTool uTool = new UtilityTool();

            //Gombok
            BufferedImage b1Image = uTool.load(MainMenu.prefix + "b2.png");
            b1 = new JButton("");
            b1.setMargin(new Insets(0,0,0,0));
            b1.setIcon(new ImageIcon(b1Image));
            styleButton(b1);

            b2 = new JButton("Load");
            styleButton(b2);
            b3 = new JButton("Exit");
            styleButton(b3);


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
            menu.add(b1);
            menu.add(b2);
            menu.add(b3);

            this.setPreferredSize(new Dimension(100, 20));
            this.add(menu);
        }

        /**
         * A játékbeli gombok stílusát állítja be.
         */
        private void styleButton(JButton button){

            button.setContentAreaFilled(true);
            button.setBorderPainted(true);
            button.setOpaque(true);
            button.setForeground(Color.WHITE);
            button.setBackground(baseColor);

            Dimension size = new Dimension(150, 25);
            button.setPreferredSize(size);
            button.setMinimumSize(size);
            button.setMaximumSize(size);


            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(hoverColor);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(baseColor);
                }
            });
        }
}
