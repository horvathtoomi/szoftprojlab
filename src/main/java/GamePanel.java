package main.java;

import main.java.control.MouseHandler;
import main.java.player.Player;
import main.java.view.DrawManager;
import main.java.view.UtilityTool;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel {

	private final GameController gameController;
	private final DrawManager drawManager;
	private final BufferedImage backgroundImage;
	private MouseHandler mouseHandler;

	public GamePanel(ArrayList<Player> players) {
		gameController = new GameController(false, 20);
		gameController.setPlanet(gameController.buildPlanet());
        for (Player player : players) {
            gameController.addPlayer(player);
        }
		drawManager = new DrawManager();
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		UtilityTool ut = new UtilityTool();
		backgroundImage = ut.load(MainMenu.prefix + "Background_icon3.png");
		mouseHandler = new MouseHandler(gameController, this::repaint);
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (backgroundImage != null) {
			g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		}
		drawManager.drawPlanet(g2, gameController.getPlanet());

		ArrayList<Player> players = gameController.getPlayers();
		Font font = new Font("SansSerif", Font.BOLD, 14);
		g2.setFont(font);
		g2.setColor(Color.WHITE);

		int padding = 10;
		int lineHeight = g2.getFontMetrics().getHeight();
		int startX = getWidth() - 150;
		int startY = padding + lineHeight;

		for (int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			String text = p.getName() + " - " + p.getScore();
			g2.drawString(text, startX, startY + i * lineHeight);
		}
	}
}
