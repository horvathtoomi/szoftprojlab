package main.java.control;

import main.java.GameController;
import main.java.insect.Insect;
import main.java.player.Insecter;
import main.java.tecton.GeometryTecton;
import main.java.tecton.Tecton;

/**
 * Ez az osztály felüldefiniálja a ClickAction execute fv-ét.
 * Az dönti el, hogy egy kattintást hogyan kell feldolgozni, ha azt egy rovarász végzi, és a játék inicializációs fázisban van.
 */
public class InsecterInitClickAction implements ClickAction {

    private final MouseHandler mouseHandler;

    /**
     * Konstruktor, létrehozza a példányt a megadott MH példánnyal
     */
    public InsecterInitClickAction(MouseHandler mouseHandler) {
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
        Tecton clickedTecton = mouseHandler.getClickedTecton();
        Insecter p = (Insecter) gc.getCurrentPlayer();

        for(Insect i : gc.getPlanet().getInsects()) {
            if(i.getLocation().equals(clickedTecton)) {
                return;
            }
        }
        Insect i = new Insect(clickedTecton);

        GeometryTecton tectonGeometry = clickedTecton.getGeometry();
        i.setGeometry(gc.randomOffsetInsideCircle(tectonGeometry));

        gc.getPlanet().getInsects().add(i);
        p.addInsect(i);
    }
}
