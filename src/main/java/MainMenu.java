package main.java;

import javax.swing.*;

import main.java.mushroom.Mushroom;
import main.java.player.Insecter;
import main.java.player.Player;
import main.java.player.Shroomer;
import main.java.view.UtilityTool;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * A főmenüt megvalósító osztály
 */
public class MainMenu extends JPanel {
	
	public static String prefix = "resources/";  //Intellij-ben írjátok be a resources/-t, eclipseben legyen üres sztring
	private static final UtilityTool uTool = new UtilityTool();
    private static JFrame frame;

	public static JFrame getFrame() {
		return frame;
	}

	/**
	 * Létrehoz egy új példányt, és elhelyezi a kapott frame-en
	 * @param frame A kapott ablak
	 */
	public MainMenu(JFrame frame) {
		MainMenu.frame = frame;

		UtilityTool uTool = new UtilityTool();
		BufferedImage originalBgImage = uTool.load(prefix + "menu_bg6.png");

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		int targetWidth = (int)(screenWidth * 0.8);
		int targetHeight = (int)(screenHeight * 0.8);

		Image scaledBgImage = originalBgImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
		ImageIcon backgroundIcon = new ImageIcon(scaledBgImage);
		JLabel backgroundLabel = new JLabel(backgroundIcon);

		frame.setSize(targetWidth, targetHeight);
		frame.setLocationRelativeTo(null);

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

		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(Box.createVerticalStrut(20));
		buttonPanel.add(newGameButton);
		buttonPanel.add(Box.createVerticalStrut(15));
		buttonPanel.add(loadGameButton);
		buttonPanel.add(Box.createVerticalStrut(15));
		buttonPanel.add(exitButton);

		backgroundLabel.add(buttonPanel);

		newGameButton.addActionListener(e -> createPlayerSelector());

		loadGameButton.addActionListener(e -> {
			GameState loadedState = GameFileChooser.loadGame(frame);
			if (loadedState != null) {
				JOptionPane.showMessageDialog(frame, "Játékállapot sikeresen betöltve!", "Betöltés sikeres", JOptionPane.INFORMATION_MESSAGE);
				startGameFromLoad(loadedState);
			}
		});

		exitButton.addActionListener(e -> System.exit(0));
	}

	/**
	 * A főmenübeli gombok stílusát állítja be.
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

	GamePanel gamePanel;

	/**
	 * A betöltött játék elindításáért felelős gomb hatása.
	 */
	public static void startGameFromLoad(GameState state){
		if (state == null) {
			return;
		}
		GameController loadedController = new GameController(false, 20, frame::repaint);
		loadedController.setPlanet(state.planet());

		for(Player player : state.players()) {
			loadedController.addPlayer(player);
		}

		// Kör számláló és az aktuális játékos beállítása
		loadedController.setTurnCounter(state.turnCounter());
		loadedController.setCurrentPlayer(state.currentPlayer());
		loadedController.setInit(state.isInit());

		// Fontos: a topológiai kapcsolatok újraépítése
		loadedController.getPlanet().recalcNeighbours();
		loadedController.getPlanet().checkForBodyConnection();

		// Új GamePanel létrehozása a betöltött kontrollerrel
		GamePanel gamePanel = new GamePanel(loadedController);

		// Frame beállítása
		frame.getContentPane().removeAll();
		frame.setJMenuBar(new GameMenu(frame, loadedController));
		frame.add(gamePanel, BorderLayout.CENTER);
		frame.revalidate();
		frame.repaint();
		gamePanel.requestFocusInWindow();
	}
	/**
	 * Az új ablak létrehozását végző függvény.
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

	JTextField insecter1Text;
	JTextField insecter2Text;
	JTextField shroomer1Text;
	JTextField shroomer2Text;

	/**
	 * A játékosok felvételét végző ablak létrehozásáért felelős függvény.
	 */
	private void createPlayerSelector(){

		JFrame frame = new JFrame();
		BufferedImage logo = uTool.load(MainMenu.prefix + "mb_big.png");
		frame.setIconImage(logo);
		JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		frame.add(panel);

		BufferedImage cursorImage = uTool.load(MainMenu.prefix + "cursor3.png");
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "cursor");
		frame.setCursor(cursor);

		JLabel insecter1 = new JLabel("Insecter1:");
		insecter1Text = new JTextField();
		setStyle(insecter1, insecter1Text, panel);

		JLabel insecter2 = new JLabel("Insecter2:");
		insecter2Text = new JTextField();
		setStyle(insecter2, insecter2Text, panel);

		JLabel shroomer1 = new JLabel("Shroomer1:");
		shroomer1Text = new JTextField();
		setStyle(shroomer1, shroomer1Text, panel);

		JLabel shroomer2 = new JLabel("Shroomer2:");
		shroomer2Text = new JTextField();
		setStyle(shroomer2, shroomer2Text, panel);

		BufferedImage b1Image = uTool.load(MainMenu.prefix + "bStart.png");
		BufferedImage b1Imageh = uTool.load(MainMenu.prefix + "bStarth.png");
		JButton startButton = new JButton("");
		panel.add(Box.createVerticalStrut(30));
		setButton(startButton, b1Image, b1Imageh, panel);
		startButton.addActionListener(e -> {startGame(frame);});

		BufferedImage b2Image = uTool.load(MainMenu.prefix + "bCancel.png");
		BufferedImage b2Imageh = uTool.load(MainMenu.prefix + "bCancelh.png");
		JButton exitButton = new JButton("");
		panel.add(Box.createVerticalStrut(30));
		setButton(exitButton, b2Image, b2Imageh, panel);
		exitButton.addActionListener(e -> {frame.dispose();});

		panel.setBackground(new Color(47, 84, 39));
		frame.setSize(new Dimension(300, 500));
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setVisible(true);
	}
	/**
	 * A játékosmenübeli gombok stílusát állítja be.
	 * @param button A JButton, amit beállít
	 * @param image Az alap gomb textúra
	 * @param hovered Az átfedéses gomb textúra
	 * @param panel A JPanel, ami tartalmazza az elemeket
	 */
	private void setButton(JButton button, BufferedImage image, BufferedImage hovered, JPanel panel) {
		button.setIcon(new ImageIcon(image));
		button.setFocusable(false);
		int BUTTON_WIDTH = 130;
		int BUTTON_HEIGHT = 50;
		button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		button.setMinimumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		button.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setMargin(new Insets(0,5,0,0));

		button.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setIcon(new ImageIcon(hovered));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setIcon(new ImageIcon(image));
			}
		});

		panel.add(button);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	/**
	 * A játékosmenübeli feliratok stílusát állítja be.
	 * @param label A JLabel, amit beállít
	 * @param field A JTextField, amit beállít
	 * @param panel A JPanel, ami tartalmazza az elemeket
	 */
	private void setStyle(JLabel label, JTextField field, JPanel panel) {
		Font regularFont = new Font("SansSerif", Font.BOLD, 14);
		label.setFont(regularFont);
		label.setForeground(Color.WHITE);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);

		//field.setPreferredSize(new Dimension(120, 20));
		//field.setMinimumSize(new Dimension(120, 20));
		field.setMaximumSize(new Dimension(120, 20));
		field.setEditable(true);
		field.setBackground(Color.LIGHT_GRAY);
		field.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		field.setAlignmentX(Component.CENTER_ALIGNMENT);

		panel.add(Box.createVerticalStrut(30));
		panel.add(label);
		panel.add(Box.createVerticalStrut(10));
		panel.add(field);
	}

	String insecterName1;
	String insecterName2;
	String shroomerName1;
	String shroomerName2;

	/**
	 * Az új játék elindításáért felelős gomb hatása.
	 */
	private void startGame(JFrame frame) {
		insecterName1 = insecter1Text.getText();
		insecterName2 = insecter2Text.getText();
		shroomerName1 = shroomer1Text.getText();
		shroomerName2 = shroomer2Text.getText();

		if (insecterName1 == null || insecterName2 == null || shroomerName1 == null || shroomerName2 == null ||
				insecterName1.isBlank() || insecterName2.isBlank() || shroomerName1.isBlank() || shroomerName2.isBlank()) {
			JOptionPane.showMessageDialog(null, "All names must be provided.", "Input Error", JOptionPane.ERROR_MESSAGE);
		}else {
			ArrayList<Player> players = new ArrayList<>();
			players.add(new Insecter(insecterName1, false));
			players.add(new Insecter(insecterName2, false));
			players.add(new Shroomer(shroomerName1, false, new Mushroom( false)));
			players.add(new Shroomer(shroomerName2, false, new Mushroom( false)));
			gamePanel = new GamePanel(players);
			makeFrame();
			frame.dispose();
		}
	}
	/**
	 * A kilépés gomb hatása
	 */
	private void exit() {
		System.exit(0);
	}
}