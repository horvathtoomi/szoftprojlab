package main.java.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import main.java.MushroomBody;

public class DefaultMushroomBodyDrawer extends UtilityTool implements MushroomBodyDrawer {

    private final int width = 32, height = 32;
    private BufferedImage shortImage;
    private BufferedImage grownImage;
    private BufferedImage mediumImage;

    DefaultMushroomBodyDrawer() {
        shortImage = load("/mb_small.png");
        mediumImage = load("/mb_medium.png");
        grownImage = load("/mb_big.png");
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
        g2.drawImage(image, mb.getGeometry().getX(), mb.getGeometry().getY(), width, height, null);
    }

}