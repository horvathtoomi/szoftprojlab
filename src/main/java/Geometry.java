package main.java;


import java.io.Serial;
import java.io.Serializable;

/**
 * Egy adott objektum elhelyezkedését adja meg a pályán
 */
public class Geometry implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int x;
    private final int y;

    /**
     * Létrehoz egy új példányt a kapott paraméterekkel
     * @param x X koordináta
     * @param y Y koordináta
     */
    public Geometry(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // getterek
    public int getX() { return x; }
    public int getY() { return y; }
}
