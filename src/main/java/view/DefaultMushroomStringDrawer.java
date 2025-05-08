package main.java.view;

import java.awt.*;
import main.java.mushroom.*;

public class DefaultMushroomStringDrawer implements MushroomStringDrawer {

    @Override
    public void draw(Graphics2D g2, MushroomString ms, int x1, int y1, int x2, int y2) {

        Stroke originalStroke = g2.getStroke();

        if(ms.getDead()){
            g2.setColor(Color.GRAY);
        } else if(ms.getLifeCycle() == MushroomString.LifeCycle.Child) {
            g2.setColor(Color.GREEN);
        } else {
            g2.setColor(Color.BLUE);
        }

        float thickness = ms.getLifeCycle() == MushroomString.LifeCycle.Grown ? 3.0f : 1.5f;
        g2.setStroke(new BasicStroke(thickness));
        g2.drawLine(x1, y1, x2, y2);
        g2.setStroke(originalStroke);
    }
}