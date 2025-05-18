package main.java.control;

import main.java.GameController;
import main.java.insect.Insect;
import main.java.mushroom.MushroomString;
import main.java.player.Insecter;
import main.java.spore.Spore;
import main.java.spore.SporeAccept;
import main.java.spore.SporeConsumptionVisitor;
import main.java.tecton.Tecton;

/**
 * Ez az osztály felüldefiniálja a ClickAction execute fv-ét.
 * Az dönti el, hogy egy kattintást hogyan kell feldolgozni, ha azt egy rovarász végzi, és ez a "második" kattintás
 */
public class InsecterSecondClickAction implements ClickAction {
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;

    /**
     * Konstruktor, létrehozza a példányt a megadott MH és KH példánnyal
     */
    public InsecterSecondClickAction(KeyHandler keyHandler, MouseHandler mouseHandler) {
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
        //A kattintás egy tektonra, spórára, vagy fonalra mehet
        if(keyHandler.getKeyCode() == KeyHandler.KEY_MOVE){ // M = move
            mouseHandler.selectTecton(mouseX, mouseY);
        }
        else if(keyHandler.getKeyCode() == KeyHandler.KEY_EAT){ //E = eat spore
            mouseHandler.selectSpore(mouseX, mouseY);
        }
        else if(keyHandler.getKeyCode() == KeyHandler.KEY_CUT) { //C = cut
            mouseHandler.selectMushroomString(mouseX, mouseY);
        }

        Tecton clickedTecton = mouseHandler.getClickedTecton();
        Insect clickedInsect = mouseHandler.getClickedInsect();
        Spore clickedSpore = mouseHandler.getClickedSpore();
        MushroomString clickedMushroomString = mouseHandler.getClickedMushroomString();
        Insecter p = (Insecter) mouseHandler.getGameController().getCurrentPlayer();

        if(clickedTecton != null && clickedInsect.getLocation() != clickedTecton && clickedTecton.isNeighbour(clickedInsect.getLocation()) && gc.getPlanet().getMushstrings().stream()
                .anyMatch(ms -> ms.getConnection().contains(clickedInsect.getLocation()) && ms.getConnection().contains(clickedTecton))) {
            int actionNumber = clickedInsect.move(clickedTecton);
            if(actionNumber != -3){
                clickedInsect.setGeometry(gc.randomOffsetInsideCircle(clickedTecton.getGeometry()));
                gc.nextTurnCheck();
                p.setActions(p.getActions() + actionNumber);
            }

        }
        else if(clickedSpore != null) {
            if (clickedInsect.getLocation() == clickedSpore.getLocation()) {
                SporeConsumptionVisitor v = new SporeConsumptionVisitor(clickedInsect, gc);
                ((SporeAccept) clickedSpore).accept(v);
                gc.getCurrentPlayer().setScore(clickedInsect.getNutrients());
                gc.nextTurnCheck();
            }
        }
        else if(clickedMushroomString != null) {
            if(clickedInsect.cutHypha(clickedMushroomString))
                gc.nextTurnCheck();
        }
        mouseHandler.reset();
    }

}
