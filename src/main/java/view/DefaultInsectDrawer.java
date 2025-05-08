package main.java.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import main.java.Insect;

public class DefaultInsectDrawer extends UtilityTool implements InsectDrawer {

    private int x;
    private int y;
    private final int width = 20, height = 20;
    private BufferedImage image;

    DefaultInsectDrawer(){
        image = load("/insect_icon.png");
    }

    @Override
    public void draw(Graphics2D g2, Insect insect, int x, int y) {
        g2.drawImage(image, x, y, width, height, null);
    }

}