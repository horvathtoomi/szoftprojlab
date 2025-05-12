package main.java;


import java.io.Serial;
import java.io.Serializable;

public class Geometry implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int x;
    private int y;


    public Geometry(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // getterek, ha kellenek a DrawManagernek
    public int getX() { return x; }
    public int getY() { return y; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
}
