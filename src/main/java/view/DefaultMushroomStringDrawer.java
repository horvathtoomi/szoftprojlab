package main.java.view;

import java.awt.*;
import main.java.mushroom.*;

/**
 * A fonalakat kirajzoló osztály
 */
public class DefaultMushroomStringDrawer implements MushroomStringDrawer {

    /**
     * A kirajzoló függvény
     * @param g2 A kirajzoló objektum
     * @param ms A kirajzolandó objektum
     */
    @Override
    public void draw(Graphics2D g2, MushroomString ms) {

        Stroke originalStroke = g2.getStroke();

        if(ms.getDead()){
            g2.setColor(Color.GRAY);
        } else if(ms.getLifeCycle() == MushroomString.LifeCycle.Child) {
            g2.setColor(Color.GREEN);
        } else {
            g2.setColor(Color.WHITE);
        }

        float thickness = ms.getLifeCycle() == MushroomString.LifeCycle.Grown ? 3.0f : 1.5f;
        g2.setStroke(new BasicStroke(thickness));
        g2.drawLine(ms.getGeometry().getX(), ms.getGeometry().getY(),
                ms.getGeometry().getX2(), ms.getGeometry().getY2());
        g2.setStroke(originalStroke);
    }
}