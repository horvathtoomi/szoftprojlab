package main.java.view;

import main.java.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameOverPanel extends JPanel {

    private final BufferedImage background;
    private final String shroomerName;
    private final String insecterName;

    public GameOverPanel(String shroomerName, String insecterName) {
        this.shroomerName = shroomerName;
        this.insecterName = insecterName;

        // háttér betöltése
        UtilityTool tool = new UtilityTool();
        background = tool.load(MainMenu.prefix + "gameover_picture.jpg");

        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // háttér kirajzolása
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        }

        // nyertesek nevei
        g.setColor(Color.WHITE);
        Font regularFont = new Font("SansSerif", Font.BOLD, 48);
        g.setFont(regularFont);

        FontMetrics fm = g.getFontMetrics();
        String shLine = "Shroomer: " + shroomerName;
        String inLine = "Insecter: " + insecterName;

        int y = getHeight() / 2;
        g.drawString(shLine, (getWidth() - fm.stringWidth(shLine)) / 2, y);
        g.drawString(inLine, (getWidth() - fm.stringWidth(inLine)) / 2, y + 60);
    }
}
