package main.java.view;

import java.awt.*;
import main.java.tecton.*;

public class DefaultTectonDrawer extends UtilityTool implements TectonDrawer {


    public DefaultTectonDrawer() {}

    @Override
    public void draw(Graphics2D g2, Tecton tecton){
    	TectonAccept acceptor = (TectonAccept) tecton;
    	TectonDrawerVisitor tdvisitor = new TectonDrawerVisitor(g2);
    	acceptor.accept(tdvisitor);
    }
}