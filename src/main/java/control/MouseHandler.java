package main.java.control;

import main.java.GameController;
import main.java.insect.Insect;
import main.java.mushroom.MushroomBody;
import main.java.player.Insecter;
import main.java.player.Shroomer;
import main.java.tecton.Tecton;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * A MouseHandler osztály felelős az egér események kezeléséért.
 * Kezeli a kattintásokat és az egér mozgását a felhasználói felületen.
 */
public class MouseHandler implements MouseListener, MouseMotionListener {

    private GameController gc;
    boolean firstClick = true;

    public MouseHandler(GameController gc) {
        this.gc = gc;
    }

    /**
     * Az inicializációs fázis kattintása (ha shroomer, akkor kifejlett gombatestet, máskülönben rovart tesz le a kattintott tektonra)
     */
    private void initClick() {
        Tecton clicked = null;
        if(gc.getCurrentPlayer() instanceof Shroomer) {
            //A kattintás egy tektonra mehet, és kifejlett gombatestet tesz le
            gc.getPlanet().getMushbodies().add(new MushroomBody(clicked, ((Shroomer) gc.getCurrentPlayer()).getMushroom(), 2, "faszgomba", false)); //Megj.: A nameable interfészre nincs is már szükség, hiszen az csak a terminálhoz kellett. Itt már csak a játékosnak van neve, ha jól gondolom
            gc.setInitCheck();
        }
        else if(gc.getCurrentPlayer() instanceof Insecter) {
            //A kattintás egy tektonra mehet, és rovart tesz le
            Insect i = new Insect(clicked, "gecinsect");
            gc.getPlanet().getInsects().add(i);
            ((Insecter) gc.getCurrentPlayer()).addInsect(i);
            gc.setInitCheck();
        }
    }

    /**
     * Az éles játék kattintásának feldolgozása, ha ez az "első kattintás"
     */
    private void firstGameClick(){
        if(gc.getCurrentPlayer() instanceof Shroomer) {
            //A gombára, spórás tektonra, vagy fonalra mehet. Előbbi esetében mondjuk gombokkal legyen kapcsolható, hogy új fonalat növesztünk, vagy spórát választunk ki.
            firstClick = false; //Ha spórás tektonra kattint, akkor a firstClick marad true, hiszen az új test növesztése már nem igényel további kattintást
        }
        else if(gc.getCurrentPlayer() instanceof Insecter) {
            //A kattintás egy rovarra mehet, amivel cselekedni akarunk
            firstClick = false;
        }
    }

    /**
     * Az éles játék kattintásának feldolgozása, ha ez a "második kattintás"
     */
    private void secondGameClick(){
        if(gc.getCurrentPlayer() instanceof Shroomer) {
            //Tektonra mehet, ahova a spórát szórjuk, vagy fonalat növesztünk oda
            firstClick = true;
        }
        else if(gc.getCurrentPlayer() instanceof Insecter) {
            //A kattintás egy tektonra, spórára, vagy fonalra mehet, de lehet itt is célszerű lenne gombokkal szabályozni, hogy ne lehessen véletlenül mondjuk félrekattintani
            firstClick = true;
        }
    }

    /**
     * Az éles játék kattintásának feldolgozása aszerint, hogy ez a játékos "első kattintása" a legutóbbi akció óta, vagy sem
     */
    private void gameClick() {
        if(firstClick) {
            firstGameClick();
        }
        else{
            secondGameClick();
        }

    }

    /**
     * A kattintás logikájáért felelős metódus, ami aszerint bontja ketté a feladatokat, hogy a játék inicializációs fázisban van-e, vagy sem
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if(gc.getInit())
            initClick(); //Ezekben persze a kattintás helyességét is nézni kell, pl valóban egy jó tektonra nyomott a felhasználó, erre jó a geometry
        else
            gameClick();
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

}