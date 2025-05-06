package main.java.view;

import main.java.Spore;
import java.awt.*;

public interface SporeDrawer {
    public void draw(Graphics2D g2, Spore s, int x, int y);
}