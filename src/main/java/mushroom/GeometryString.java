package main.java.mushroom;

import main.java.Geometry;

import java.io.Serial;
import java.io.Serializable;

public class GeometryString extends Geometry implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int width;

    public GeometryString(int x, int y, int width) {
        super(x,y);
        this.width = width;
    }
}