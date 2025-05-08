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

		setPreferredSize(new Dimension(800, 600));
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		UtilityTool ut = new UtilityTool();
		backgroundImage = ut.load("resources/Background_icon.png");
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

		drawManager.drawPlanet(g2, gameController.getPlanet());
	}


    public static void main(String[] args) {
    	JFrame frame = new JFrame("Faszgorium");
    	MainMenu mainMenu = new MainMenu(frame);
    	frame.setContentPane(mainMenu);
    	frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    	frame.setVisible(true);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
