package main.java.mushroom;

import main.java.Geometry;

import java.io.Serial;
import java.io.Serializable;

public class GeometryString extends Geometry implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int width;
    private int endX, endY;

    public GeometryString(int x, int y, int x2, int y2, int width) { // Az x, y lesz a kezdetének a koordinátái, az endX és endY pedig a végének.
        super(x,y);
        endX = x2;
        endY = y2;
        this.width = width;
    }

}