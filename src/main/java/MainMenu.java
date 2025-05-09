package main.java;

import javax.swing.*;

import main.java.player.Insecter;
import main.java.player.Player;
import main.java.player.Shroomer;
import main.java.view.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MainMenu extends JPanel {
	
	public static String prefix = "resources/";  //Intellij-ben írjátok be a resources/-t, eclipseben legyen üres sztring

    private final JFrame frame;
	
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

        JButton newGameButton = new JButton("NEW GAME");
		styleButton(newGameButton, buttonFont);

        JButton loadGameButton = new JButton("LOAD GAME");
		styleButton(loadGameButton, buttonFont);

        JButton exitButton = new JButton("EXIT");
		styleButton(exitButton, buttonFont);

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

	private void styleButton(JButton button, Font font) {
		button.setFont(font);
		button.setFocusable(false);
        int BUTTON_WIDTH = 200;
        int BUTTON_HEIGHT = 50;
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		button.setContentAreaFilled(true);
		button.setBorderPainted(true);
		button.setOpaque(true);
		button.setForeground(Color.WHITE);

		Color baseColor = new Color(30, 30, 30);
		Color hoverColor = new Color(60, 60, 60);

		button.setBackground(baseColor);

		button.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setBackground(hoverColor);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(baseColor);
			}
		});

		button.addActionListener(e -> {
			switch (button.getText()) {
				case "NEW GAME" -> startGame();
				case "LOAD GAME" -> loadGame();
				case "EXIT" -> exit();
			}
		});
	}

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
		players.add(new Shroomer(shroomerName1, false));
		players.add(new Shroomer(shroomerName2, false));
		return players;
	}
	GamePanel gamePanel;
	private void startGame() {
		ArrayList<Player> players = addPlayers();
		if(players != null) {
           	gamePanel = new GamePanel(players);
        } else
			return;

		// Teljes képernyő mód
		frame.dispose();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		frame.getContentPane().removeAll();
		frame.setJMenuBar(new GameMenu());
		frame.add(gamePanel, BorderLayout.CENTER);
		frame.revalidate();
		frame.repaint();
		gamePanel.requestFocusInWindow();
	}

	private void loadGame() {
		// mentés betöltése
	}

	private void exit() {
		System.exit(0);
	}
}
