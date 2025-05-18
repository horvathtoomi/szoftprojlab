package main.java.control;

import main.java.GameController;
import main.java.mushroom.MushroomString;

/**
 * Ez az osztály felüldefiniálja a ClickAction execute fv-ét.
 * Az dönti el, hogy egy kattintást hogyan kell feldolgozni, ha azt egy gombász végzi, és ez az "első" kattintás
 */
public class ShroomerFirstClickAction implements ClickAction {
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;

    /**
     * Konstruktor, létrehozza a példányt a megadott MH és KH példánnyal
     */
    public ShroomerFirstClickAction(KeyHandler keyHandler, MouseHandler mouseHandler) {
        this.keyHandler = keyHandler;
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
        /*int code = keyHandler.getKeyCode();
            System.out.println("Current keyCode: " + code);
            A gombára,vagy fonalra mehet*/
        MushroomString clickedMushroomString = mouseHandler.getClickedMushroomString();
        if(keyHandler.getKeyCode() == KeyHandler.KEY_MUSHROOM){ // click on mushroom
            System.out.println("Mushroom at " + mouseX + ", " + mouseY);
            mouseHandler.selectMushroomBody(mouseX, mouseY);
            keyHandler.resetKeyCode();
        }
        else if(keyHandler.getKeyCode() == KeyHandler.KEY_BRANCH) { // branch existing hypha
            mouseHandler.selectMushroomString(mouseX, mouseY);
        }

        if(mouseHandler.getClickedMushroomBody() != null || clickedMushroomString != null)
            mouseHandler.setFirstClick(false); //Ha spórás tektonra kattint, akkor a firstClick marad true, hiszen az új test növesztése már nem igényel további kattintást
    }
}
