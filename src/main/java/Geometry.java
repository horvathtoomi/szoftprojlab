package main.java;

import java.awt.*;

public class Geometry {
    private int x;
    private int y;

    public Geometry(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // getterek, ha kellenek a DrawManagernek
    public int getX() { return x; }
    public int getY() { return y; }
}
