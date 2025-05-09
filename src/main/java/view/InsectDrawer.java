package main.java.view;

import java.awt.*;
import main.java.insect.Insect;

public interface InsectDrawer {
    public void draw(Graphics2D g2, Insect i);
}