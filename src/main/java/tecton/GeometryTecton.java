package main.java.tecton;

import main.java.Geometry;

import java.io.Serial;
import java.io.Serializable;

public class GeometryTecton extends Geometry implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private final int radius;
	
	public GeometryTecton(int x, int y, int radius) {
		super(x, y);
		this.radius = radius;
	}
	
    public int getRadius() { return radius; }
}