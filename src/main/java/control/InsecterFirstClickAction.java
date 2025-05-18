package main.java.control;

import main.java.GameController;
import main.java.insect.Insect;

/**
 * Ez az osztály felüldefiniálja a ClickAction execute fv-ét.
 * Az dönti el, hogy egy kattintást hogyan kell feldolgozni, ha azt egy rovarász végzi, és ez az "első" kattintás
 */
public class InsecterFirstClickAction implements ClickAction {
    private final MouseHandler mouseHandler;

    /**
     * Konstruktor, létrehozza a példányt a megadott MH példánnyal
     */
    public InsecterFirstClickAction(MouseHandler mouseHandler) {
        this.mouseHandler = mouseHandler;
    }
    /**
     * A fent említett execute fv.
     *
     * @param gc A GC pédány, a játékos lekéréséhez kell
     * @param mouseX A kattintás X koordinátája
     * @param mouseY A kattintás Y koordinátája
     */
    @Override
    public void execute(GameController gc, int mouseX, int mouseY) {
        //A kattintás egy rovarra mehet, amivel cselekedni akarunk
        mouseHandler.selectInsect(mouseX, mouseY);
        Insect clickedInsect = mouseHandler.getClickedInsect();
        if (clickedInsect != null) {
            mouseHandler.setFirstClick(false);
        }
    }
}
