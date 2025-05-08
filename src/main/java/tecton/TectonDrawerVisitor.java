package main.java.tecton;

import main.java.Geometry;

import java.awt.Color;
import java.awt.Graphics2D;

public class TectonDrawerVisitor implements TectonVisitor<Void> {
	
	private Graphics2D g2;
	private Geometry geometry;
	
	public TectonDrawerVisitor(Graphics2D g2) {
		this.g2 = g2;
	}
	
	public Void visit(BigTecton big) {
		g2.setColor(new Color(102, 93, 30));
		g2.fillOval(big.getGeometry().getX(), big.getGeometry().getY(), 2*big.getGeometry().getRadius(), 2*big.getGeometry().getRadius());
		
		return null;
    }
	
	public Void visit(SmallTecton small) {
		g2.setColor(new Color(80, 50, 20));
		g2.fillOval(small.getGeometry().getX(), small.getGeometry().getY(), 2*small.getGeometry().getRadius(), 2*small.getGeometry().getRadius());
		
		return null;
    }

	public Void visit(ToxicTecton toxic) {
		g2.setColor(new Color(0, 255, 0));
		g2.fillOval(toxic.getGeometry().getX(), toxic.getGeometry().getY(), 2*toxic.getGeometry().getRadius(), 2*toxic.getGeometry().getRadius());
		
		return null;
    }
	
	public Void visit(HealingTecton healing) {
		g2.setColor(new Color(173, 216, 230));
		g2.fillOval(healing.getGeometry().getX(), healing.getGeometry().getY(), 2*healing.getGeometry().getRadius(), 2*healing.getGeometry().getRadius());
		
		return null;
    }
	
	public Void visit(CoarseTecton coarse) {
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillOval(coarse.getGeometry().getX(), coarse.getGeometry().getY(), 2*coarse.getGeometry().getRadius(), 2*coarse.getGeometry().getRadius());
		
		return null;
    }
}
