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

    /**
     * Létrehoz egy új példányt a aparaméterek alapján
     * @param gameController A gc, amihez tartozik
     * @param frame Az ablak, ami tartalmazza
     */
        public GameMenu(JFrame frame, GameController gameController, boolean customCursor) {
            this.frame = frame;
            this.gameController = gameController;
            setBackground(new Color(77, 92, 71));
            UtilityTool uTool = new UtilityTool();
            setBorderPainted(false);

            //Gombok
            BufferedImage b1Image = uTool.load(MainMenu.prefix + "bSave.png");
            BufferedImage b1Imageh = uTool.load(MainMenu.prefix + "bSaveh.png");
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

            b1.addActionListener(e -> {
                if (GameFileChooser.saveGame(frame, gameController)) {
                    JOptionPane.showMessageDialog(frame, "Játékállapot sikeresen elmentve!", "Mentés sikeres", JOptionPane.INFORMATION_MESSAGE);
                }
            });

            b2.addActionListener(e -> {
                GameState loadedState = GameFileChooser.loadGame(frame);
                if(loadedState != null) {
                    JOptionPane.showMessageDialog(frame, "Játékállapot sikeresen betöltve!", "Betöltés sikeres", JOptionPane.INFORMATION_MESSAGE);
                    MainMenu.startGameFromLoad(loadedState);
                }
            });

            b3.addActionListener(e -> {
                frame.setJMenuBar(null);
                frame.getContentPane().removeAll();
                MainMenu menu = new MainMenu(frame, customCursor);
                menu.setBackground(new Color(6, 26, 14));
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.add(menu);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                MenuSelectionManager.defaultManager().clearSelectedPath();
                frame.revalidate();
                frame.repaint();
            });

            UIManager.put("Menu.selectionBackground", new Color(50, 50, 50));
            UIManager.put("Menu.selectionForeground", Color.WHITE);
            JMenu menu = new JMenu("Menu");
            menu.setForeground(Color.WHITE);
            menu.setBorder(BorderFactory.createEmptyBorder());
            menu.setBorderPainted(false);
            menu.getPopupMenu().setBorder(BorderFactory.createEmptyBorder());

            menu.add(b1);
            menu.add(b2);
            menu.add(b3);

            this.setPreferredSize(new Dimension(100, 25));
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
