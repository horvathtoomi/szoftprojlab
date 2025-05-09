package main.java;

import main.java.view.DrawManager;
import main.java.view.UtilityTool;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {

	private final GameController gameController;
	private final DrawManager drawManager;
	private BufferedImage backgroundImage;

	public GamePanel() {
		gameController = new GameController(false, 100);
		drawManager = new DrawManager();
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		UtilityTool ut = new UtilityTool();
		backgroundImage = ut.load(MainMenu.prefix + "Background_icon3.png");
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (backgroundImage != null) {
			// méretezés a panel méretéhez
			g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		}

		drawManager.drawPlanet(g2, gameController.buildPlanet());
	}
}
