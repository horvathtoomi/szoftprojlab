package main.java.view;

import java.awt.*;
import main.java.mushroom.MushroomString;

public interface MushroomStringDrawer {
    void draw(Graphics2D g2, MushroomString ms, int x1, int y1, int x2, int y2);
}