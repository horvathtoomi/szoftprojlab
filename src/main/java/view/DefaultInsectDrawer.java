package main.java.view;

import java.awt.*;
import java.awt.image.BufferedImage;

import main.java.MainMenu;
import main.java.insect.Insect;


public class DefaultInsectDrawer extends UtilityTool implements InsectDrawer {

    private final int width = 40, height = 40;
    private BufferedImage image;

    DefaultInsectDrawer(){
        image = load(MainMenu.prefix + "insect_icon3.png");
    }

    @Override
    public void draw(Graphics2D g2, Insect insect) {
        g2.drawImage(image, insect.getGeometry().getX(), insect.getGeometry().getY(), width, height, null);
    }

}