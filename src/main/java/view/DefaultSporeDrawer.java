package main.java.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import main.java.MainMenu;
import main.java.spore.Spore;
import main.java.tecton.Tecton;


public class DefaultSporeDrawer extends UtilityTool implements SporeDrawer {

    public static final int SIZE = 30;
    private BufferedImage image;
    DefaultSporeDrawer() {
        image = load(MainMenu.prefix + "spore.png");
    }

    @Override
    public void draw(Graphics2D g2, Spore spore) {
        g2.drawImage(image, spore.getGeometry().getX() - SIZE / 2, spore.getGeometry().getY() - SIZE / 2, SIZE, SIZE, null);
        
    }
}   