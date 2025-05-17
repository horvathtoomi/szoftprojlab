package main.java.view;

import main.java.tecton.Tecton;
import java.awt.*;

/**
 * Interfész a tektonok kirajzolásához a bővíthetőség érdekében
 */
public interface TectonDrawer {
    void draw(Graphics2D g2, Tecton t);
}