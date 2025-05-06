package main.java.view;

import java.awt.*;
import main.java.MushroomString;

public interface MushroomStringDrawer {
    public void draw(Graphics2D g2, MushroomString ms, int x1, int y1, int x2, int y2);
}