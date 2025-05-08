package main.java;

import javax.swing.JFrame;

public class Main {
	
	static JFrame frame;
	static MainMenu menu;

	public static void main(String[] args) {
		frame = new JFrame();
		menu = new MainMenu(frame);
		frame.add(menu);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}

}
