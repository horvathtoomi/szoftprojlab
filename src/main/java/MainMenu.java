package main.java;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {
	private JButton newGameButton;
	private JButton loadGameButton;
	private JButton exitButton;
	private final int BUTTON_WIDTH = 200;
	private final int BUTTON_HEIGHT = 50;

	public MainMenu() {
		// Háttérkép betöltése
		ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/menu_bg.png"));
		JLabel backgroundLabel = new JLabel(backgroundIcon);
		backgroundLabel.setLayout(new GridBagLayout()); // A JLabel-re tesszük a gombokat
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
		button.setOpaque(false);               // átlátszó háttér
		button.setContentAreaFilled(false);    // ne fedje le a háttérképet
		button.setBorderPainted(true);         // keret maradjon meg (vagy false ha nem kell)
		button.addActionListener(e -> {
			if (button.getText().equals("NEW GAME")) startGame();
			else if (button.getText().equals("LOAD GAME")) loadGame();
			else if (button.getText().equals("EXIT")) exit();
		});
	}

	private void startGame() {
		// játék indítása
	}

	private void loadGame() {
		// mentés betöltése
	}

	private void exit() {
		System.exit(0);
	}
}
