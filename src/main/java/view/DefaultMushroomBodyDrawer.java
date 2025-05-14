package main.java.view;

import java.awt.*;
import java.awt.image.BufferedImage;

import main.java.MainMenu;
import main.java.mushroom.*;

public class DefaultMushroomBodyDrawer extends UtilityTool implements MushroomBodyDrawer {

    private final BufferedImage shortImage;
    private final BufferedImage grownImage;
    private final BufferedImage mediumImage;

    DefaultMushroomBodyDrawer() {
        shortImage = load(MainMenu.prefix + "mb_small.png");
        mediumImage = load(MainMenu.prefix + "mb_medium.png");
        grownImage = load(MainMenu.prefix + "mb_big.png");
    }

    @Override
    public void draw(Graphics2D g2, MushroomBody mb) {
        BufferedImage image;
        switch(mb.getState()){
            case "SMALL" :
                image = shortImage;
                break;
            case "MEDIUM" :
                image =mediumImage;
                break;
            case "BIG" :
                image = grownImage;
                break;
            default :
                image = shortImage;
                break;
        }

        int width = 50;
        float drawX = mb.getGeometry().getX() - (float) width /2;
        int height = 50;
        float drawY = mb.getGeometry().getY() - (float) height /2;

        g2.drawImage(image, (int) drawX, (int) drawY, width, height, null);
    }

}