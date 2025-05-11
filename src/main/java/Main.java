package main.java;

import main.java.view.UtilityTool;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {

	static JFrame frame;
	static MainMenu menu;
	static UtilityTool uTool = new UtilityTool();

	public static void main(String[] args) {
		frame = new JFrame();

		BufferedImage logo = uTool.load(MainMenu.prefix + "mb_big.png");
		frame.setIconImage(logo);

		menu = new MainMenu(frame);
		menu.setBackground(new Color(6, 26, 14));

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.add(menu);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
