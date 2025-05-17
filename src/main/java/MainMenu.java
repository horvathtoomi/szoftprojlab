package main.java;

import javax.swing.*;

import main.java.mushroom.Mushroom;
import main.java.player.Insecter;
import main.java.player.Player;
import main.java.player.Shroomer;
import main.java.view.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * A főmenüt megvalósító osztály
 */
public class MainMenu extends JPanel {
	
	public static String prefix = "resources/";  //Intellij-ben írjátok be a resources/-t, eclipseben legyen üres sztring

    private final JFrame frame;

	/**
	 * Létrehoz egy új példányt, és elhelyezi a kapott frame-en
	 * @param frame A kapott ablak
	 */
	public MainMenu(JFrame frame) {
		this.frame = frame;

		UtilityTool uTool = new UtilityTool();
		BufferedImage bgImage = uTool.load(prefix + "menu_bg6.png");

		ImageIcon backgroundIcon = new ImageIcon(bgImage);
		JLabel backgroundLabel = new JLabel(backgroundIcon);
		frame.setSize(bgImage.getWidth(), bgImage.getHeight());

		backgroundLabel.setLayout(new GridBagLayout());
		this.setLayout(new BorderLayout());
		this.add(backgroundLabel, BorderLayout.CENTER);

		Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        JButton newGameButton = new JButton("");
		BufferedImage b1Image = uTool.load(MainMenu.prefix + "bNewGame3.png");
		BufferedImage b1ImageHovered = uTool.load(MainMenu.prefix + "bNewGame3h.png");
		styleButton(newGameButton, buttonFont, b1Image, b1ImageHovered);

        JButton loadGameButton = new JButton("");
		BufferedImage b2Image = uTool.load(MainMenu.prefix + "bLoad3.png");
		BufferedImage b2ImageHovered = uTool.load(MainMenu.prefix + "bLoad3h.png");
		styleButton(loadGameButton, buttonFont, b2Image, b2ImageHovered);

        JButton exitButton = new JButton("");
		BufferedImage b3Image = uTool.load(MainMenu.prefix + "bExit3.png");
		BufferedImage b3ImageHovered = uTool.load(MainMenu.prefix + "bExit3h.png");
		styleButton(exitButton, buttonFont, b3Image, b3ImageHovered);

		newGameButton.addActionListener(e -> startGame());

		loadGameButton.addActionListener(e -> {
			if (GameFileChooser.loadGame(frame, frame)) {
				JOptionPane.showMessageDialog(frame, "Játékállapot sikeresen betöltve!", "Betöltés sikeres", JOptionPane.INFORMATION_MESSAGE);
				GameState state = null; //TODO
				startGameFromLoad(state);
			}
		});

		exitButton.addActionListener(e -> exit());

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(25, 0, 25, 0);
		c.gridx = 0;

		c.gridy = 0;
		backgroundLabel.add(newGameButton, c);

		c.gridy = 1;
		backgroundLabel.add(loadGameButton, c);

		c.gridy = 2;
		backgroundLabel.add(exitButton, c);
	}

	/**
	 * A játékbeli gombok stílusát állítja be.
	 */
	private void styleButton(JButton button, Font font, BufferedImage image, BufferedImage hovered) {
		button.setFont(font);
		button.setFocusable(false);
        int BUTTON_WIDTH = 210;
        int BUTTON_HEIGHT = 95;
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setForeground(Color.WHITE);

		Color baseColor = new Color(47, 84, 39);

		button.setBackground(baseColor);

		button.setIcon(new ImageIcon(image));
		button.setMargin(new Insets(0,20,0,0));

		button.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setIcon(new ImageIcon(hovered));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setIcon(new ImageIcon(image));
			}
		});
	}

	/**
	 * A játékosok felvételéért felel.
	 */
	private ArrayList<Player> addPlayers()	{
		String insecterName1 = JOptionPane.showInputDialog(frame, "Enter name for Insecter 1:");
		String insecterName2 = JOptionPane.showInputDialog(frame, "Enter name for Insecter 2:");
		String shroomerName1 = JOptionPane.showInputDialog(frame, "Enter name for Shroomer 1:");
		String shroomerName2 = JOptionPane.showInputDialog(frame, "Enter name for Shroomer 2:");

		if (insecterName1 == null || insecterName2 == null || shroomerName1 == null || shroomerName2 == null ||
				insecterName1.isBlank() || insecterName2.isBlank() || shroomerName1.isBlank() || shroomerName2.isBlank()) {
			JOptionPane.showMessageDialog(frame, "All names must be provided.", "Input Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		ArrayList<Player> players = new ArrayList<>();
		players.add(new Insecter(insecterName1, false));
		players.add(new Insecter(insecterName2, false));
		players.add(new Shroomer(shroomerName1, false, new Mushroom( false)));
		players.add(new Shroomer(shroomerName2, false, new Mushroom( false)));
		return players;
	}

	GamePanel gamePanel;

	/**
	 * Az új játék elindításáért felelős gomb hatása
	 */
	private void startGame() {
		ArrayList<Player> players = addPlayers();
		if(players != null) {
           	gamePanel = new GamePanel(players);
        } else {
			return;
		}
		makeFrame();
	}
	/**
	 * A betöltött játék elindításáért felelős gomb hatása
	 */
	private void startGameFromLoad(GameState state){
		makeFrame();
	}
	/**
	 * Az új ablak létrehozását végző függvény
	 */
	private void makeFrame() {
		frame.dispose();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		frame.getContentPane().removeAll();
		frame.setJMenuBar(new GameMenu(frame, gamePanel.getGameController()));
		frame.add(gamePanel, BorderLayout.CENTER);
		frame.revalidate();
		frame.repaint();
		gamePanel.requestFocusInWindow();
	}
	/**
	 * A kilépés gomb hatása
	 */
	private void exit() {
		System.exit(0);
	}
}
