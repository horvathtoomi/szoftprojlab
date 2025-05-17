package main.java;

import main.java.view.UtilityTool;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A Main osztály felelős a program kiindulási pontjáért, innen hívódik meg a main függvény.
 */
public class Main {

    private static final UtilityTool uTool = new UtilityTool();

	/**
	 * A program indulásakor hívódó main függvény
	 */
	public static void main(String[] args) {
        JFrame frame = new JFrame("Fungorium");

		BufferedImage logo = uTool.load(MainMenu.prefix + "mb_big.png");
		frame.setIconImage(logo);

		BufferedImage cursorImage = uTool.load(MainMenu.prefix + "cursor2.png");
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "cursor");
		frame.setCursor(cursor);

        MainMenu menu = new MainMenu(frame);
		menu.setBackground(new Color(6, 26, 14));

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.add(menu);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
