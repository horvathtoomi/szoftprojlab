package main.java.view;

import java.awt.*;
import main.java.MushroomBody;

public interface MushroomBodyDrawer {
    public void draw(Graphics2D g2, MushroomBody mb, int x, int y);
}