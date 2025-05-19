package main.java.view;

import main.java.MainMenu;
import main.java.view.UtilityTool;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameOverPanel extends JPanel {

    private final BufferedImage background;
    private final String shroomerName;
    private final String insecterName;
    private final JFrame frame;

    public GameOverPanel(JFrame frame, String shroomerName, String insecterName) {
        this.frame = frame;
        this.shroomerName = shroomerName;
        this.insecterName = insecterName;

        // háttér betöltése
        UtilityTool tool = new UtilityTool();
        background = tool.load(MainMenu.prefix + "gameover_picture.jpg");

        setLayout(new BorderLayout());

        // Exit gomb
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            frame.setJMenuBar(null);
            frame.getContentPane().removeAll();
            MainMenu menu = new MainMenu(frame);
            menu.setBackground(new Color(6, 26, 14));
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.add(menu);
            frame.revalidate();
            frame.repaint();
        });
        add(exitButton, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // háttér kirajzolása
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        }

        // nyertesek nevei
        g.setColor(new Color(255, 215, 0));           // sárga
        g.setFont(getFont().deriveFont(Font.BOLD, 48f));

        FontMetrics fm = g.getFontMetrics();
        String shLine = "Shroomer: " + shroomerName;
        String inLine = "Insecter: " + insecterName;

        int y = getHeight() / 2;
        g.drawString(shLine, (getWidth() - fm.stringWidth(shLine)) / 2, y);
        g.drawString(inLine, (getWidth() - fm.stringWidth(inLine)) / 2, y + 60);
    }
}
