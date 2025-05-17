package main.java.view;

import java.awt.*;
import main.java.tecton.*;

/**
 * A tektonokat kirajzoló osztály
 */
public class DefaultTectonDrawer extends UtilityTool implements TectonDrawer {

    /**
     * Létrehoz egy új példányt
     */
    public DefaultTectonDrawer() {}

    /**
     * A kirajzoló függvény. Visitor segítségével dönti el, hogy melyik tektont hogyan rajzolja ki
     * @param g2 A kirajzoló objektum
     * @param tecton A kirajzolandó objektum
     */
    @Override
    public void draw(Graphics2D g2, Tecton tecton){
    	TectonAccept acceptor = (TectonAccept) tecton;
    	TectonDrawerVisitor tdvisitor = new TectonDrawerVisitor(g2);
    	acceptor.accept(tdvisitor);
    }
}