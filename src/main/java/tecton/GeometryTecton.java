package main.java.tecton;

import main.java.Geometry;

import java.io.Serial;
import java.io.Serializable;

/**
 * A Geometry osztály kiterjesztése tektonokra úgy, hogy eltárolja a tekton sugarát is
 */
public class GeometryTecton extends Geometry implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private final int radius;

	/**
	 * Létrehoz egy új példányt a kapott paraméterekkel
	 * @param x X koordináta
	 * @param y Y koordináta
	 * @param radius a tekton sugara
	 */
	public GeometryTecton(int x, int y, int radius) {
		super(x, y);
		this.radius = radius;
	}

	//Getter
    public int getRadius() {
		return radius;
	}
}