package main.java.mushroom;

import main.java.Geometry;

import java.io.Serial;
import java.io.Serializable;

/**
 * A Geometry osztály kiterjesztése fonalakra úgy, hogy eltárolja a fonal végpontjait is
 */
public class GeometryString extends Geometry implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int x2;
    private int y2;

    /**
     * Létrehozza az új példányt, és beállítja a kezdő-végpontjait.
     * @param x kezdőpont x koordinátája
     * @param y kezdőpont y koordinátája
     * @param x2 végpont x koordinátája
     * @param y2 végpont y koordinátája
     */
    public GeometryString(int x, int y, int x2, int y2) {
        super(x,y);
        this.x2 = x2;
        this.y2 = y2;
    }

    //Getterek, setterek
    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }
    
    public void setX2(int x2) {
    	this.x2 = x2;
    }

    public void setY2(int y2) {
    	this.y2 = y2;
    }
}