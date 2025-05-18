package main.java;

import main.java.console.ConsoleHandler;
import main.java.view.UtilityTool;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A játékbeli menü megvalósításáért felelős osztály.
 */
public class GameMenu extends JMenuBar {

        GameController gameController;
        JButton b1, b2, b3, b4;
        JFrame frame;

        Color baseColor = new Color(47, 84, 39);
        Color hoverColor = new Color(75, 125, 64);

    /**
     * Létrehoz egy új példányt a aparaméterek alapján
     * @param gameController A gc, amihez tartozik
     * @param frame Az ablak, ami tartalmazza
     */
        public GameMenu(JFrame frame, GameController gameController) {
            this.frame = frame;
            this.gameController = gameController;
            setBackground(new Color(77, 92, 71));
            UtilityTool uTool = new UtilityTool();
            setBorderPainted(false);

            //Gombok
            BufferedImage b1Image = uTool.load(MainMenu.prefix + "bExit4.png");
            BufferedImage b1Imageh = uTool.load(MainMenu.prefix + "bExit4h.png");
            b1 = new JButton("");
            styleButton(b1, b1Image, b1Imageh);

            BufferedImage b2Image = uTool.load(MainMenu.prefix + "bLoad4.png");
            BufferedImage b2Imageh = uTool.load(MainMenu.prefix + "bLoad4h.png");
            b2 = new JButton("");
            styleButton(b2, b2Image, b2Imageh);

            BufferedImage b3Image = uTool.load(MainMenu.prefix + "bExit4.png");
            BufferedImage b3Imageh = uTool.load(MainMenu.prefix + "bExit4h.png");
            b3 = new JButton("");
            styleButton(b3,b3Image, b3Imageh);

            b4 = new JButton("Developer Console");
            b4.setForeground(Color.WHITE);
            b4.setBackground(baseColor);
            b4.setMargin(new Insets(0,0,0,0));
            Dimension size = new Dimension(120, 35);
            b4.setPreferredSize(size);
            b4.setMinimumSize(size);
            b4.setMaximumSize(size);
            b4.setContentAreaFilled(true);
            b4.setBorderPainted(false);
            b4.setOpaque(true);

            b1.addActionListener(e -> {
                if (GameFileChooser.saveGame(frame, gameController)) {
                    JOptionPane.showMessageDialog(frame, "Játékállapot sikeresen elmentve!", "Mentés sikeres", JOptionPane.INFORMATION_MESSAGE);
                }
            });

            b2.addActionListener(e -> {
                GameState loadedState = GameFileChooser.loadGame(frame, frame);
                if(loadedState != null) {
                    JOptionPane.showMessageDialog(frame, "Játékállapot sikeresen betöltve!", "Betöltés sikeres", JOptionPane.INFORMATION_MESSAGE);
                    MainMenu.startGameFromLoad(loadedState);
                }
            });

            b4.addActionListener(e -> {
                ConsoleHandler consoleHandler = new ConsoleHandler();
                consoleHandler.startConsoleInput();
            });

            b3.addActionListener(e -> {
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

            UIManager.put("PopupMenu.border", new LineBorder(new Color(77, 92, 71)));
            JMenu menu = new JMenu("Menu");
            menu.setForeground(Color.WHITE);

            menu.add(b1);
            menu.add(b2);
            menu.add(b3);
            menu.add(b4);

            this.setPreferredSize(new Dimension(100, 20));
            this.add(menu);
        }

        /**
         * A játékbeli gombok stílusát állítja be.
         * @param button A gomb, amit beállít
         */
        private void styleButton(JButton button, BufferedImage image, BufferedImage hovered) {

            button.setContentAreaFilled(true);
            button.setBorderPainted(false);
            button.setOpaque(true);
            button.setMargin(new Insets(0,10,0,0));
            button.setIcon(new ImageIcon(image));

            Dimension size = new Dimension(120, 35);
            button.setPreferredSize(size);
            button.setMinimumSize(size);
            button.setMaximumSize(size);

            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setIcon(new ImageIcon(hovered));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setIcon(new ImageIcon(image));
                }
            });
        }
}
