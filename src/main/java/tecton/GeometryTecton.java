package main.java.tecton;

import main.java.Geometry;

public class GeometryTecton extends Geometry {
	
	private final int radius;
	
	public GeometryTecton(int x, int y, int radius) {
		super(x, y);
		this.radius = radius;
	}
	
    public int getRadius() { return radius; }
}