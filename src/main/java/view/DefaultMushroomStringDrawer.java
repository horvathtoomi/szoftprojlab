package main.java.view;

import java.awt.*;
import main.java.MushroomString;

public class DefaultMushroomStringDrawer implements MushroomStringDrawer {
    
    private int x1, y1, x2, y2;

    @Override
    public void draw(Graphics2D g2, MushroomString mb, int x1, int y1, int x2, int y2) {
        g2.drawLine(x1, y1, x2, y2);
    }
}