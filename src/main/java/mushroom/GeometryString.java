package main.java.mushroom;

import main.java.Geometry;

import java.io.Serial;
import java.io.Serializable;

public class GeometryString extends Geometry implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int x2;
    private final int y2;

    public GeometryString(int x, int y, int x2, int y2) {
        super(x,y);
        this.x2 = x2;
        this.y2 = y2;
    }

    public int getX2() {
        return x2;
    }
    public int getY2() {
        return y2;
    }

}