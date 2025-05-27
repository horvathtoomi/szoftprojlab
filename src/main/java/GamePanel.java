package main.java;

import main.java.control.*;
import main.java.view.GameOverPanel;
import main.java.player.*;
import main.java.view.DrawManager;
import main.java.view.UtilityTool;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * A GamePanel osztály felelős a játék megjelenítéséért.
 */
public class GamePanel extends JPanel {

	public enum ShineOn {TECTON, MUSHBODY, MUSHSTRING, SPORE, INSECT, NONE}
	private ShineOn shineOn = ShineOn.NONE;

	private final GameController gameController;
	private final DrawManager drawManager;
	private final BufferedImage backgroundImage;

	/**
	 * Létrehozza magát a játékot, és minden paramétert beállít.
	 * @param players A játékot játszó játékosok listája
	 */
    public GamePanel(ArrayList<Player> players) {
		gameController = new GameController(false, 20, this::repaint);
		gameController.setPlanet(gameController.buildPlanet());
        for (Player player : players) {
            gameController.addPlayer(player);
        }
		drawManager = new DrawManager();
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		UtilityTool ut = new UtilityTool();
		backgroundImage = ut.load(MainMenu.prefix + "Background_icon3.png");
        KeyHandler keyHandler = new KeyHandler(gameController, this::repaint, this);
        MouseHandler mouseHandler = new MouseHandler(gameController, this::repaint, this, keyHandler);
		this.addMouseListener(mouseHandler);
		this.addKeyListener(keyHandler);
		this.setFocusable(true);
	}

	public GamePanel(GameController existingController) {
		gameController = existingController;
		drawManager = new DrawManager();
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		UtilityTool ut = new UtilityTool();
		backgroundImage = ut.load(MainMenu.prefix + "Background_icon3.png");
		KeyHandler keyHandler = new KeyHandler(gameController, this::repaint, this);
		MouseHandler mouseHandler = new MouseHandler(gameController, this::repaint, this, keyHandler);
		this.addMouseListener(mouseHandler);
		this.addKeyListener(keyHandler);
		this.setFocusable(true);
	}


	public GameController getGameController() {
		return gameController;
	}

	/**
	 * Kiemeli az interaktálható objektumokat
	 * @param shineOn A kijelölendő objektum
	 */
	public void setShineOn(ShineOn shineOn) {
		this.shineOn = shineOn;
	}

	/**
	 * Kirajzolja a játék követéséhez szükséges információkat
 	 * @param g2 A kirajzoló komponens
 	*/
	private void drawGameStatus(Graphics2D g2) {
		ArrayList<Player> players = gameController.getPlayers();
		Font regularFont = new Font("SansSerif", Font.BOLD, 14);
		g2.setFont(regularFont);
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

		Font statusFont = new Font("SansSerif", Font.BOLD, 16);
		g2.setFont(statusFont);

		int statusHeight = lineHeight * 2 + padding * 2;
		int statusWidth = 250;
		int statusX = getWidth() - statusWidth - padding;
		int statusY = getHeight() - statusHeight - padding;

		g2.setColor(new Color(0, 0, 0, 180));
		g2.fillRoundRect(statusX, statusY, statusWidth, statusHeight, 10, 10);

		g2.setColor(Color.WHITE);
		Player currentPlayer = gameController.getCurrentPlayer();
		int remainingRounds = gameController.getMaxTurn() - gameController.getTurnCounter();
		String playerText = "Current Player: " + (currentPlayer != null ? currentPlayer.getName() : "None");
		String roundText = "Remaining Rounds: " + remainingRounds;

		g2.drawString(playerText, statusX + padding, statusY + lineHeight);
		g2.drawString(roundText, statusX + padding, statusY + lineHeight * 2);

		if (currentPlayer != null) {
			Color playerColor = currentPlayer.getPlayerColor();
			g2.setColor(playerColor);
			g2.fillRoundRect(statusX + 160, statusY + lineHeight - 12, 10, 10, 5, 5);

			g2.setColor(Color.WHITE);
			g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
			String actionsText = "Actions left: " + currentPlayer.getActions();
			g2.drawString(actionsText, statusX + padding, statusY + lineHeight * 3);
			if(gameController.getTurnCounter() == gameController.getMaxTurn())
			{
				ArrayList<Player> winners = gameController.nextTurnCheck();
				if (!winners.isEmpty()) {
					gameEndsPopup(winners);
				}
			}
		}
	}

	/**
	 * A játék végét jelző pop-up ablak megvalósítása
	 * @param winners A győztesek listája
	 */
	private void gameEndsPopup(ArrayList<Player> winners) {

	    /* ----- kinyerjük a nevek szerintük típusát ----- */
	    final String[] shName = {""};
	    final String[] inName = {""};
	    for (Player w : winners) {
	        w.accept(new PlayerVisitor() {            // CHANGED: visitor-t használunk
	            @Override public void visit(Shroomer s)  { shName[0] = s.getName(); }
	            @Override public void visit(Insecter i) { inName[0] = i.getName(); }
	        });
	    }

	    /* ----- a GameOverPanel betöltése a keretbe ----- */
	    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
	    frame.getContentPane().removeAll();           // előző tartalom törlése
	    GameOverPanel gop = new GameOverPanel(shName[0], inName[0]); // CHANGED
	    frame.add(gop);
	    frame.revalidate();
	    frame.repaint();
	}

	/**
	 * A játék megjelenítéséért felelős függvény
	 * @param g A kirajzoló komponens
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (backgroundImage != null) {
			g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		}
		drawManager.drawPlanet(g2, gameController.getPlanet(), shineOn);
		drawGameStatus(g2);
	}
}
