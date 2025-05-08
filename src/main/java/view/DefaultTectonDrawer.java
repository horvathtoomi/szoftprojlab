package main.java.view;

import java.awt.*;
import java.awt.image.BufferedImage;

import main.java.*;

public class DefaultTectonDrawer extends UtilityTool implements TectonDrawer {

    private final int width = 32, height = 32;
    private final BufferedImage image;

    public DefaultTectonDrawer() {
        image = load("C:/Users/horvath_toomi/Music/EGYETEM/SZOFTPROJ/SzoftProjLab---csapatnev/src/circle.jpg");
    }

    @Override
    public void draw(Graphics2D g2, Tecton tecton){
    	TectonAccept acceptor = (TectonAccept) tecton;
    	TectonDrawerVisitor tdvisitor = new TectonDrawerVisitor(g2);
    	acceptor.accept(tdvisitor);
    }
}