package main.java.view;

import java.awt.*;
import main.java.mushroom.MushroomBody;

/**
 * Interfész a gombatestek kirajzolásához a bővíthetőség érdekében
 */
public interface MushroomBodyDrawer {
    void draw(Graphics2D g2, MushroomBody mb);
}