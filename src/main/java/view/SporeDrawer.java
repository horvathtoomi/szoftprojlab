package main.java.view;

import main.java.spore.Spore;
import java.awt.*;

/**
 * Interfész a spórák kirajzolásához a bővíthetőség érdekében
 */
public interface SporeDrawer {
    void draw(Graphics2D g2, Spore s);
}