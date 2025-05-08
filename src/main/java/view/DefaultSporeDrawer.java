package main.java.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import main.java.spore.Spore;


public class DefaultSporeDrawer extends UtilityTool implements SporeDrawer {

    private final int width = 16, height = 16;
    private BufferedImage image;
    DefaultSporeDrawer() {
        image = load("resources/spore.png");
    }

    @Override
    public void draw(Graphics2D g2, Spore s, int x, int y) {
        g2.drawImage(image, x, y, width, height, null);
    }

}   