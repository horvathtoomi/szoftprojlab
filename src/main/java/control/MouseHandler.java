package main.java.control;

import main.java.GameController;
import main.java.insect.Insect;
import main.java.mushroom.MushroomBody;
import main.java.mushroom.MushroomString;
import main.java.player.Insecter;
import main.java.player.Player;
import main.java.player.Shroomer;
import main.java.spore.Spore;
import main.java.tecton.Tecton;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A MouseHandler osztály felelős az egér események kezeléséért.
 * Kezeli a kattintásokat és az egér mozgását a felhasználói felületen.
 */
public class MouseHandler implements MouseListener, MouseMotionListener {

    private final GameController gc;
    boolean firstClick = true;
    private final Runnable repaintCallback;
    private Insect clickedInsect = null;
    private Tecton clickedTecton = null;
    private MushroomBody clickedMushroomBody = null;
    private Spore clickedSpore = null;
    private MushroomString clickedMushroomString = null;

    public MouseHandler(GameController gc, Runnable repaintCallback) {
        this.gc = gc;
        this.repaintCallback = repaintCallback;
    }

    private void reset(){
        clickedInsect = null;
        clickedTecton = null;
        clickedMushroomBody = null;
        clickedSpore = null;
        clickedMushroomString = null;
        firstClick = true;
    }

    /**
     * Egy tekton kiválasztása. Megvizsgálja, hogy a kattintás pozíciója ráesik-e egy objektumra.
     * @param x a kattintás x koordinátája
     * @param y a kattintás y koordinátája
     * @param searchForSpores Ha igaz, akkor csak az elégséges spórával rendelkező tektonokat vizsgálja - ha testet akarunk növeszteni
     */
    private void selectTecton(int x, int y, boolean searchForSpores) {
        for (Tecton t : gc.getPlanet().getTectons()) {
            int tx = t.getGeometry().getX();
            int ty = t.getGeometry().getY();
            int radius = t.getGeometry().getRadius();
            if (Math.hypot(tx - x, ty - y) <= radius) {
                if(searchForSpores){
                    int counter = 0;
                    for(Spore s : gc.getPlanet().getSpores()){
                        if (s.getLocation().equals(t))
                            counter++;
                    }
                    if(counter >= 3){
                        clickedTecton = t;
                        break;
                    }
                }
                else {
                    clickedTecton = t;
                    break;
                }
            }
        }
    }

    /**
     * Egy gombatest kiválasztása. Megvizsgálja, hogy a kattintás pozíciója ráesik-e egy objektumra.
     * @param x a kattintás x koordinátája
     * @param y a kattintás y koordinátája
     */
    private void selectMushroomBody(int x, int y) {
        for (MushroomBody mb : gc.getPlanet().getMushbodies()) {
            int tx = mb.getGeometry().getX();
            int ty = mb.getGeometry().getY();
            int radius = 45;
            if (Math.hypot(tx - x, ty - y) <= radius && ((Shroomer) gc.getCurrentPlayer()).getMushroom().equals(mb.getMushroom())) {
                clickedMushroomBody = mb;
                break;
            }
        }
    }

    /**
     * Egy rovar kiválasztása. Megvizsgálja, hogy a kattintás pozíciója ráesik-e egy objektumra.
     * @param x a kattintás x koordinátája
     * @param y a kattintás y koordinátája
     */
    private void selectInsect(int x, int y) {
        for (Insect i : gc.getPlanet().getInsects()) {
            int tx = i.getGeometry().getX();
            int ty = i.getGeometry().getY();
            int radius = 45;
            if (Math.hypot(tx - x, ty - y) <= radius && ((Insecter) gc.getCurrentPlayer()).getInsects().contains(i)) {
                clickedInsect = i;
                break;
            }
        }
    }

    /**
     * Egy spóra kiválasztása. Megvizsgálja, hogy a kattintás pozíciója ráesik-e egy objektumra.
     * @param x a kattintás x koordinátája
     * @param y a kattintás y koordinátája
     */
    private void selectSpore(int x, int y) {
        for (Spore s : gc.getPlanet().getSpores()) {
            int tx = s.getGeometry().getX();
            int ty = s.getGeometry().getY();
            int radius = 35;
            if (Math.hypot(tx - x, ty - y) <= radius) {
                clickedSpore = s;
                break;
            }
        }
    }

    /**
     * Egy fonal kiválasztása. Megvizsgálja, hogy a kattintás pozíciója ráesik-e egy objektumra.
     * @param x a kattintás x koordinátája
     * @param y a kattintás y koordinátája
     */
    private void selectMushroomString(int x, int y) {
        for (MushroomString ms : gc.getPlanet().getMushstrings()) {
            //Itt meg kellene határozni, hogy a fonal által meghatározott szakaszra esik-e a kattintás. Ha a currentPlayer shroomer, akkor ő branchelni akar, és akkor azt is meg kell nézni, hogy az ő gombájához
            //tartozik-e a fonal. Ha insecter, akkor nem kell, hiszen az egy fonalvágás lesz. Csak ez egyelőre nemtom hogy kell, mert pl a fonalnak nincs geometryje ha jól látom
        }
    }

    /**
     * Az inicializációs fázis kattintása (ha shroomer, akkor kifejlett gombatestet, máskülönben rovart tesz le a kattintott tektonra)
     * @param p A játékos, aki végzi a kattintást
     * @param mouseX a kattintás x koordinátája
     * @param mouseY a kattintás y koordinátája
     */
    private void initClick(Player p, int mouseX, int mouseY) {
        System.out.println("Clicked at " + mouseX + ", " + mouseY);
        selectTecton(mouseX, mouseY, false);

        //Most már nem is kellenek ezek a nevek nem? Csak a játékosoknak
        if (clickedTecton != null) {
            if (p instanceof Shroomer) {
                gc.getPlanet().getMushbodies().add(new MushroomBody(
                        clickedTecton,
                        ((Shroomer) p).getMushroom(),
                        2,
                        "shroom",
                        false
                ));
            } else if (p instanceof Insecter) {
                Insect i = new Insect(clickedTecton, "insect");
                gc.getPlanet().getInsects().add(i);
                ((Insecter) p).addInsect(i);
            }
            gc.setInitCheck();
            gc.setCurrentPlayerToNextPlayer();
            reset();
        }
    }

    /**
     * Az éles játék kattintásának feldolgozása, ha ez az "első kattintás"
     * @param p A játékos, aki végzi a kattintást
     * @param mouseX a kattintás x koordinátája
     * @param mouseY a kattintás y koordinátája
     */
    private void firstGameClick(Player p, int mouseX, int mouseY) {
        if(p instanceof Shroomer) {
            System.out.println("Clicked at " + mouseX + ", " + mouseY);
            //A gombára, spórás tektonra, vagy fonalra mehet, de ezt gombokkal lehessen kapcsolni. Addig placeholder if else
            if(true){
                selectTecton(mouseX, mouseY, true);
                gc.nextTurnCheck(); //Ez akció, szóval ellenőrizzük a kört
            }
            else if(false){
                selectMushroomBody(mouseX, mouseY);
            }
            else {
                selectMushroomString(mouseX, mouseY);
            }

            if(clickedMushroomBody != null || clickedMushroomString != null)
                firstClick = false; //Ha spórás tektonra kattint, akkor a firstClick marad true, hiszen az új test növesztése már nem igényel további kattintást
        }
        else if(p instanceof Insecter) {
            System.out.println("Clicked at " + mouseX + ", " + mouseY);
            //A kattintás egy rovarra mehet, amivel cselekedni akarunk
            selectInsect(mouseX, mouseY);
            if (clickedInsect != null) {
                firstClick = false;
            }
        }
    }

    /**
     * Az éles játék kattintásának feldolgozása, ha ez a "második kattintás"
     * @param p A játékos, aki végzi a kattintást
     * @param mouseX a kattintás x koordinátája
     * @param mouseY a kattintás y koordinátája
     */
    private void secondGameClick(Player p, int mouseX, int mouseY){
        if(p instanceof Shroomer) {
            System.out.println("Clicked at " + mouseX + ", " + mouseY);
            //Tektonra mehet, ahova a spórát szórjuk, vagy fonalat növesztünk oda. Egyelőre ideiglenes elágazás, és a gombafonalas cuccok is csak így láthatatlanban első gondolatra így kellene
            selectTecton(mouseX, mouseY, false);
            if(clickedTecton != null){
                if(true){
                    clickedMushroomBody.spreadSpores(clickedTecton, "spore", "random");
                    gc.nextTurnCheck();
                }
                else if(clickedTecton.canGrowHypha(gc.getPlanet().getMushstrings())){
                    if(clickedMushroomString != null){
                        clickedMushroomString.branch(clickedTecton, gc.getPlanet().getMushstrings());
                        gc.nextTurnCheck();
                    }
                    else if(clickedMushroomBody != null){
                            if(clickedMushroomBody.getLocation().equals(clickedTecton)){
                                ArrayList<Tecton> connection = new ArrayList<>();
                                connection.add(clickedTecton);
                                connection.add(null);
                                gc.getPlanet().getMushstrings().add(new MushroomString("hypha", ((Shroomer) gc.getCurrentPlayer()).getMushroom(), connection, new ArrayList<>(Arrays.asList(null, null)), gc.getTurnCounter()));
                                gc.nextTurnCheck();
                            }
                    }
                }
            }
            reset();
        }
        else if(p instanceof Insecter) {
            System.out.println("Clicked at " + mouseX + ", " + mouseY);
            //A kattintás egy tektonra, spórára, vagy fonalra mehet, de lehet itt is célszerű lenne gombokkal szabályozni, hogy ne lehessen véletlenül mondjuk félrekattintani, emiatt az if-ek ideiglenesek
            if(true){
                selectTecton(mouseX, mouseY, false);
            }
            else if(false){
                selectSpore(mouseX, mouseY);
            }
            else{
                selectMushroomString(mouseX, mouseY);
            }


            if(clickedTecton != null) {
                clickedInsect.move(clickedTecton);
                gc.nextTurnCheck();
            }
            else if(clickedSpore != null) {
                clickedInsect.eatSpore(clickedSpore);
                gc.nextTurnCheck();
            }
            else if(clickedMushroomString != null) {
                clickedInsect.cutHypha(clickedMushroomString, gc.getPlanet().getMushstrings());
                gc.nextTurnCheck();
            }
          reset();
        }
    }

    /**
     * Az éles játék kattintásának feldolgozása aszerint, hogy ez a játékos "első kattintása" a legutóbbi akció óta, vagy sem
     * @param p A játékos, aki végzi a kattintást
     * @param mouseX a kattintás x koordinátája
     * @param mouseY a kattintás y koordinátája
     */
    private void gameClick(Player p, int mouseX, int mouseY) {
        if(firstClick) {
            firstGameClick(p, mouseX, mouseY);
        }
        else{
            secondGameClick(p, mouseX, mouseY);
        }

    }

    /**
     * A kattintás logikájáért felelős metódus, ami aszerint bontja ketté a feladatokat, hogy a játék inicializációs fázisban van-e, vagy sem
     * @param e A kattintás eseménye
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        Player p = gc.getCurrentPlayer();
        int x = e.getX();
        int y = e.getY();

        if (gc.getInit())
            initClick(p, x, y);
        else
            gameClick(p, x, y);
        repaintCallback.run();
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