package main.java.view;

import java.awt.*;
import java.awt.image.BufferedImage;

import main.java.MainMenu;
import main.java.spore.Spore;

/**
 * A spórákat kirajzoló osztály
 */
public class DefaultSporeDrawer extends UtilityTool implements SporeDrawer {

    public static final int SIZE = 30;
    private final BufferedImage image;

    /**
     * Létrehoz egy új példányt, és beolvassa a megfelelő képet hozzá
     */
    DefaultSporeDrawer() {
        image = load(MainMenu.prefix + "spore.png");
    }

    /**
     * A kirajzoló függvény
     * @param g2 A kirajzoló objektum
     * @param spore A kirajzolandó objektum
     */
    @Override
    public void draw(Graphics2D g2, Spore spore) {
        g2.drawImage(image, (spore.getGeometry().getX() - SIZE / 2), (spore.getGeometry().getY() - SIZE / 2), SIZE, SIZE, null);
    }
}   