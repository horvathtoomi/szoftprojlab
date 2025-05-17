package main.java.view;

import java.awt.*;
import main.java.mushroom.MushroomString;

/**
 * Interfész a fonalak kirajzolásához a bővíthetőség érdekében
 */
public interface MushroomStringDrawer {
    void draw(Graphics2D g2, MushroomString ms);
}