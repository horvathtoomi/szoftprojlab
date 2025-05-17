package main.java.tecton;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.java.MainMenu;
import main.java.view.UtilityTool;

/**
 * Ez a visitor dönti el a tektonok esetbében, hogy hogyan is kell azt megjeleníteni
 */
public class TectonDrawerVisitor implements TectonVisitor<Void> {

	private final Graphics2D g2;
	UtilityTool uTool = new UtilityTool();

	/**
	 * Létrehoz egy új példányt a megadott paraméterekkel.
	 * @param g2 A kirajzolásért felelős példány
	 */
	public TectonDrawerVisitor(Graphics2D g2) {
		this.g2 = g2;
	}

	/**
	 * BigTecton esetén a megjelenítés
	 * @param big a tekton, amit ki kell rajzolni
	 */
	public Void visit(BigTecton big) {
		BufferedImage image = uTool.load(MainMenu.prefix + "bt.png");
		float r = big.getGeometry().getRadius();
		g2.drawImage(image, (int) (big.getGeometry().getX() - r),
				(int) (big.getGeometry().getY() - r),
				(int)r * 2, (int)r * 2, null);
		return null;
	}

	/**
	 * SmallTecton esetén a megjelenítés
	 * @param small a tekton, amit ki kell rajzolni
	 */
	public Void visit(SmallTecton small) {
		BufferedImage image = uTool.load(MainMenu.prefix + "st.png");
		float r = small.getGeometry().getRadius();
		g2.drawImage(image, (int) (small.getGeometry().getX() - r),
                (int) (small.getGeometry().getY() - r),
                (int) (r * 2), (int) (r * 2), null);

		return null;
	}

	/**
	 * ToxicTecton esetén a megjelenítés
	 * @param toxic a tekton, amit ki kell rajzolni
	 */
	public Void visit(ToxicTecton toxic) {
		BufferedImage image = uTool.load(MainMenu.prefix + "tt.png");
		float r = toxic.getGeometry().getRadius();
		g2.drawImage(image, (int) (toxic.getGeometry().getX() - r),
                (int) (toxic.getGeometry().getY() - r),
                (int) (r * 2), (int) (r * 2), null);

		return null;
	}

	/**
	 * HealingTecton esetén a megjelenítés
	 * @param healing a tekton, amit ki kell rajzolni
	 */
	public Void visit(HealingTecton healing) {
		BufferedImage image = uTool.load(MainMenu.prefix + "ht.png");
		float r = healing.getGeometry().getRadius();
		g2.drawImage(image, (int) (healing.getGeometry().getX() - r),
                (int) (healing.getGeometry().getY() - r),
                (int) (r * 2), (int) (r * 2), null);

		return null;
	}

	/**
	 * CoarseTecton esetén a megjelenítés
	 * @param coarse a tekton, amit ki kell rajzolni
	 */
	public Void visit(CoarseTecton coarse) {
		BufferedImage image = uTool.load(MainMenu.prefix + "ct.png");
		float r = coarse.getGeometry().getRadius();
		g2.drawImage(image, (int) (coarse.getGeometry().getX() - r),
                (int) (coarse.getGeometry().getY() - r),
                (int) (r * 2), (int) (r * 2), null);

		return null;
	}
}
