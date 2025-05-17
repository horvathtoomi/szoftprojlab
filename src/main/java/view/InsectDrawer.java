package main.java.view;

import java.awt.*;
import main.java.insect.Insect;

/**
 * Interfész a rovarok kirajzolásához a bővíthetőség érdekében
 */
public interface InsectDrawer {
    void draw(Graphics2D g2, Insect i);
}