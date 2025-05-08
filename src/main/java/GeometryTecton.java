package main.java;

public class GeometryTecton extends Geometry {
	
	private int radius;
	
	public GeometryTecton(int x, int y, int radius) {
		super(x, y);
		this.radius = radius;
	}
	
    public int getRadius() { return radius; }
}