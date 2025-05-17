package main.java.view;

import java.awt.*;
import java.awt.image.BufferedImage;

import main.java.MainMenu;
import main.java.insect.Insect;

/**
 * A rovarokat kirajzoló osztály
 */
public class DefaultInsectDrawer extends UtilityTool implements InsectDrawer {

    private final BufferedImage image;

    /**
     * Létrehoz egy új példányt, és beolvassa a megfelelő képet hozzá
     */
    DefaultInsectDrawer(){
        image = load(MainMenu.prefix + "insect_icon3.png");
    }

    /**
     * A kirajzoló függvény
     * @param g2 A kirajzoló objektum
     * @param insect A kirajzolandó objektum
     */
    @Override
    public void draw(Graphics2D g2, Insect insect) {
        int width = 40;
        int height = 40;
        g2.drawImage(image, (int)insect.getGeometry().getX(), (int)insect.getGeometry().getY(), width, height, null);
    }

}