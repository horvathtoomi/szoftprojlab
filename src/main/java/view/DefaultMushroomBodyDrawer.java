package main.java.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import main.java.MushroomBody;

public class DefaultMushroomBodyDrawer extends UtilityTool implements MushroomBodyDrawer {

    private int x;
    private int y;
    private final int width = 20, height = 20;
    private BufferedImage shortImage;
    private BufferedImage grownImage;
    private BufferedImage mediumImage;
    private BufferedImage image;

    DefaultMushroomBodyDrawer() {
        image = load("mushbody");
    }

    @Override
    public void draw(Graphics2D g2, MushroomBody insect, int x, int y) {
        g2.drawImage(image, x, y, width, height, null);
    }

}