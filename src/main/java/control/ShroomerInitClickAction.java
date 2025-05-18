package main.java.control;

import main.java.GameController;
import main.java.mushroom.CanGrowBodyVisitor;
import main.java.mushroom.MushroomBody;
import main.java.player.Shroomer;
import main.java.tecton.GeometryTecton;
import main.java.tecton.Tecton;
import main.java.tecton.TectonAccept;

/**
 * Ez az osztály felüldefiniálja a ClickAction execute fv-ét.
 * Az dönti el, hogy egy kattintást hogyan kell feldolgozni, ha azt egy gombász végzi, és a játék inicializációs fázisban van.
 */
public class ShroomerInitClickAction implements ClickAction {
    private final MouseHandler mouseHandler;

    /**
     * Konstruktor, létrehozza a példányt a megadott MH példánnyal
     */
    public ShroomerInitClickAction(MouseHandler mouseHandler) {
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
        Shroomer p = (Shroomer) gc.getCurrentPlayer();

        CanGrowBodyVisitor v = new CanGrowBodyVisitor();
        TectonAccept acceptor = (TectonAccept) clickedTecton;
        acceptor.accept(v);
        if(v.canPerformAction()){
            MushroomBody mb = new MushroomBody(clickedTecton, p.getMushroom(), 2, false);
            for(MushroomBody m : gc.getPlanet().getMushbodies()) {
                if(m.getLocation().equals(clickedTecton)) {
                    return;
                }
            }
            GeometryTecton tectonGeometry = clickedTecton.getGeometry();
            mb.setGeometry(gc.randomOffsetInsideCircle(tectonGeometry));
            gc.getPlanet().getMushbodies().add(mb);
        }
    }
}
