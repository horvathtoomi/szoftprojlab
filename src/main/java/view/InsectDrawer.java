package main.java.view;

import java.awt.*;
import main.java.insect.Insect;

public interface InsectDrawer {
    void draw(Graphics2D g2, Insect i);
}