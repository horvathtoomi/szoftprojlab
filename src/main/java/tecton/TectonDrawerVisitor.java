package main.java.tecton;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.java.view.UtilityTool;

public class TectonDrawerVisitor implements TectonVisitor<Void> {

	private Graphics2D g2;
	UtilityTool uTool = new UtilityTool();

	public TectonDrawerVisitor(Graphics2D g2) {
		this.g2 = g2;
	}

	public Void visit(BigTecton big) {
		BufferedImage image = uTool.load("resources/bt.png");
		int r = big.getGeometry().getRadius();
		g2.drawImage(image, big.getGeometry().getX() - r,
				big.getGeometry().getY() - r,
				r * 2, r * 2, null);

		return null;
	}

	public Void visit(SmallTecton small) {
		BufferedImage image = uTool.load("resources/st.png");
		int r = small.getGeometry().getRadius();
		g2.drawImage(image, small.getGeometry().getX() - r,
				small.getGeometry().getY() - r,
				r * 2, r * 2, null);

		return null;
	}


	public Void visit(ToxicTecton toxic) {
		BufferedImage image = uTool.load("resources/tt.png");
		int r = toxic.getGeometry().getRadius();
		g2.drawImage(image, toxic.getGeometry().getX() - r,
				toxic.getGeometry().getY() - r,
				r * 2, r * 2, null);

		return null;
	}

	public Void visit(HealingTecton healing) {
		BufferedImage image = uTool.load("resources/ht.png");
		int r = healing.getGeometry().getRadius();
		g2.drawImage(image, healing.getGeometry().getX() - r,
				healing.getGeometry().getY() - r,
				r * 2, r * 2, null);

		return null;
	}

	public Void visit(CoarseTecton coarse) {
		BufferedImage image = uTool.load("resources/ct.png");
		int r = coarse.getGeometry().getRadius();
		g2.drawImage(image, coarse.getGeometry().getX() - r,
				coarse.getGeometry().getY() - r,
				r * 2, r * 2, null);

		return null;
	}
}
