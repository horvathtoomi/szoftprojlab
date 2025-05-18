package main.java.control;

import main.java.GameController;

/**
 * A ClickAction interfész egy execute függvényt definiál, amit a játékos leszármazottakhoz tartozó  ...ClickAction osztályok valósítanak meg
 * Ez dönti el, hogy egy kattintást hogyan kell feldolgozni.
 */
public interface ClickAction {
    /**
     * A fent említett execute fv.
     *
     * @param gc A GC pédány, a játékos lekéréséhez kell
     * @param mouseX A kattintás X koordinátája
     * @param mouseY A kattintás Y koordinátája
     */
    void execute(GameController gc, int mouseX, int mouseY);
}
