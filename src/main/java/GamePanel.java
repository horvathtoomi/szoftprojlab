package main.java;

import main.java.control.*;
import main.java.player.*;
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
	private final MouseHandler mouseHandler;
	private final KeyHandler keyHandler;

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
		keyHandler = new KeyHandler(gameController);
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		this.addKeyListener(keyHandler);
		this.setFocusable(true);
	}
	/**
	 * Draws the game status information including player scores and current game state
 	 * @param g2 The Graphics2D context to draw on
 	*/
	private void drawGameStatus(Graphics2D g2) {
		// Draw player scores
		ArrayList<Player> players = gameController.getPlayers();
		Font regularFont = new Font("SansSerif", Font.BOLD, 14);
		g2.setFont(regularFont);
		g2.setColor(Color.WHITE);

		int padding = 10;
		int lineHeight = g2.getFontMetrics().getHeight();
		int startX = getWidth() - 150;
		int startY = padding + lineHeight;

		// Draw player list with scores
		for (int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			String text = p.getName() + " - " + p.getScore();
			g2.drawString(text, startX, startY + i * lineHeight);
		}

		// Draw current player and remaining rounds at bottom right
		Font statusFont = new Font("SansSerif", Font.BOLD, 16);
		g2.setFont(statusFont);

		// Create a semi-transparent background for better readability
		int statusHeight = lineHeight * 2 + padding * 2;
		int statusWidth = 250;
		int statusX = getWidth() - statusWidth - padding;
		int statusY = getHeight() - statusHeight - padding;

		// Draw status background
		g2.setColor(new Color(0, 0, 0, 180)); // Semi-transparent black
		g2.fillRoundRect(statusX, statusY, statusWidth, statusHeight, 10, 10);

		// Draw status text
		g2.setColor(Color.WHITE);
		Player currentPlayer = gameController.getCurrentPlayer();
		int remainingRounds = gameController.getMaxTurn() - gameController.getTurnCounter();
		String playerText = "Current Player: " + (currentPlayer != null ? currentPlayer.getName() : "None");
		String roundText = "Remaining Rounds: " + remainingRounds;

		// Draw the status text
		g2.drawString(playerText, statusX + padding, statusY + lineHeight);
		g2.drawString(roundText, statusX + padding, statusY + lineHeight * 2);

		// If you want to highlight the current player's turn with a color
		if (currentPlayer != null) {
			Color playerColor;
			if (currentPlayer instanceof Shroomer) {
				playerColor = new Color(200, 100, 100); // Red for Shroomer
			} else {
				playerColor = new Color(255, 215, 0);   // Yellow for Insecter
			}

			g2.setColor(playerColor);
			g2.fillRoundRect(statusX + 160, statusY + lineHeight - 12, 10, 10, 5, 5);

			// Add player's remaining actions
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
			String actionsText = "Actions left: " + currentPlayer.getActions();
			g2.drawString(actionsText, statusX + padding, statusY + lineHeight * 3);
			ArrayList<Player> winners = gameController.nextTurnCheck();
			if (!winners.isEmpty()) {
				gameEndsPopup(winners);
			}
		}
	}

	private void gameEndsPopup(ArrayList<Player> winners) {
        String message = "The winners are: " +
                winners.get(0).getName() +
                " and " +
               // winners.get(1).getName() +
                "!";

		JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);

		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
		frame.setJMenuBar(null);
		frame.getContentPane().removeAll();
		MainMenu menu = new MainMenu(frame);
		menu.setBackground(new Color(6, 26, 14));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.add(menu);
		frame.revalidate();
		frame.repaint();
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
		drawGameStatus(g2);
	}
}
