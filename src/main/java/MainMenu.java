package main.java;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {
	private JButton newGameButton;
	private JButton loadGameButton;
	private JButton exitButton;
	private final int BUTTON_WIDTH = 200;
	private final int BUTTON_HEIGHT = 50;

	private JFrame frame;
	
	public MainMenu(JFrame frame) {
		this.frame = frame;
		
		// Háttérkép betöltése
		ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/menu_bg6.png"));
		JLabel backgroundLabel = new JLabel(backgroundIcon);
		System.out.println(backgroundIcon.getIconWidth() + backgroundIcon.getIconWidth());
		frame.setSize(backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
		backgroundLabel.setLayout(new GridBagLayout());
		this.setLayout(new BorderLayout());
		this.add(backgroundLabel, BorderLayout.CENTER);

		Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

		newGameButton = new JButton("NEW GAME");
		styleButton(newGameButton, buttonFont);

		loadGameButton = new JButton("LOAD GAME");
		styleButton(loadGameButton, buttonFont);

		exitButton = new JButton("EXIT");
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

	private void startGame() {
		GamePanel gamePanel = new GamePanel();
		frame.getContentPane().removeAll();
		frame.setJMenuBar(new GameMenu());
		frame.add(gamePanel);
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
