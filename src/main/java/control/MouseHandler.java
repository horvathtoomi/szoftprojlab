package main.java.control;

import main.java.GameController;
import main.java.GamePanel;
import main.java.Geometry;
import main.java.insect.Insect;
import main.java.mushroom.GeometryString;
import main.java.mushroom.MushroomBody;
import main.java.mushroom.MushroomString;
import main.java.player.Insecter;
import main.java.player.Player;
import main.java.player.Shroomer;
import main.java.spore.Spore;
import main.java.spore.SporeAccept;
import main.java.spore.SporeConsumptionVisitor;
import main.java.tecton.GeometryTecton;
import main.java.tecton.Tecton;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A MouseHandler osztály felelős az egér események kezeléséért.
 * Kezeli a kattintásokat és az egér mozgását a felhasználói felületen.
 */
public class MouseHandler implements MouseListener {

    private final GameController gc;
    private boolean firstClick = true;
    private Spore clickedSpore = null;
    private Insect clickedInsect = null;
    private Tecton clickedTecton = null;
    private final KeyHandler keyHandler;
    private final Runnable repaintCallback;
    private MushroomBody clickedMushroomBody = null;
    private MushroomString clickedMushroomString = null;
    private final GamePanel gamePanel;

    public MouseHandler(GameController gc, Runnable repaintCallback, GamePanel gamePanel, KeyHandler keyHandler) {
        this.gc = gc;
        this.repaintCallback = repaintCallback;
        this.keyHandler = keyHandler;
        this.gamePanel = gamePanel;
    }

    private void reset(){
        firstClick = true;
        clickedSpore = null;
        clickedInsect = null;
        clickedTecton = null;
        clickedMushroomBody = null;
        clickedMushroomString = null;
        gamePanel.setShineOn(GamePanel.ShineOn.NONE);
    }

    /**
     * Egy tekton kiválasztása. Megvizsgálja, hogy a kattintás pozíciója ráesik-e egy objektumra.
     * @param x a kattintás x koordinátája
     * @param y a kattintás y koordinátája
     */
    private void selectTecton(int x, int y) {
        // Beállítjuk a kiemelést a tektonokra
        gamePanel.setShineOn(GamePanel.ShineOn.TECTON);
        repaintCallback.run();
        for (Tecton t : gc.getPlanet().getTectons()) {
            float tx = t.getGeometry().getX();
            float ty = t.getGeometry().getY();
            float radius = t.getGeometry().getRadius();
            if ((x <= (tx + radius) && x >= (tx - radius)) && (y <= (ty + radius) && y >= (ty - radius))) {
                clickedTecton = t;
                //System.out.println("selected tecton: " + clickedTecton.getName());
                break;
            }
        }
    }

    /**
     * Egy gombatest kiválasztása. Megvizsgálja, hogy a kattintás pozíciója ráesik-e egy objektumra.
     * @param x a kattintás x koordinátája
     * @param y a kattintás y koordinátája
     */
    private void selectMushroomBody(int x, int y) {
        // Beállítjuk a kiemelést a gombatestekre
        gamePanel.setShineOn(GamePanel.ShineOn.MUSHBODY);
        repaintCallback.run();
        for (MushroomBody mb : gc.getPlanet().getMushbodies()) {
            float tx = mb.getGeometry().getX();
            float ty = mb.getGeometry().getY();
            int radius = 45;
            if ((x <= (tx + radius) && x >= (tx - radius)) && (y <= (ty + radius) && y >= (ty - radius))
                    && ((Shroomer) gc.getCurrentPlayer()).getMushroom().equals(mb.getMushroom())) {
                clickedMushroomBody = mb;
                //System.out.println("Selected body: " + mb);
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
        // Beállítjuk a kiemelést a rovarokra
        gamePanel.setShineOn(GamePanel.ShineOn.INSECT);
        repaintCallback.run();
        for (Insect i : gc.getPlanet().getInsects()) {
            float tx = i.getGeometry().getX();
            float ty = i.getGeometry().getY();
            int radius = 45;
            if ((x <= (tx + radius) && x >= (tx - radius)) && (y <= (ty + radius) && y >= (ty - radius))
                    && ((Insecter) gc.getCurrentPlayer()).getInsects().contains(i)) {
                clickedInsect = i;
                //System.out.println("Selected insect: " + i);
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
        // Beállítjuk a kiemelést a spórákra
        gamePanel.setShineOn(GamePanel.ShineOn.SPORE);
        repaintCallback.run();
        for (Spore s : gc.getPlanet().getSpores()) {
            float tx = s.getGeometry().getX();
            float ty = s.getGeometry().getY();
            int radius = 35;
            if ((x <= (tx + radius) && x >= (tx - radius)) && (y <= (ty + radius) && y >= (ty - radius))) {
                clickedSpore = s;
                System.out.println("Selected spore: " + s.getName());
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
        // Beállítjuk a kiemelést a gombafonalokra
        gamePanel.setShineOn(GamePanel.ShineOn.MUSHSTRING);
        repaintCallback.run();
        for (MushroomString ms : gc.getPlanet().getMushstrings()) {
            ArrayList<Tecton> connections = ms.getConnection();

            Geometry geom1;
            Geometry geom2;
            // Az összekötött tektonok geometriáját lekérjük
            if(connections.get(1) != null){
                 geom1 = connections.get(0).getGeometry();
                 geom2 = connections.get(1).getGeometry();
                 System.out.println("Büdös kurva anyád");
            }
            else{
                geom1 = new Geometry(ms.getGeometry().getX(), ms.getGeometry().getY());
                geom2 = connections.get(0).getGeometry();
                System.out.println("Buzi románok");
            }

            // Csak akkor folytatjuk, ha elég közel van a klikk a fonalhoz
            if (isClickNearLine(x, y, geom1.getX(), geom1.getY(), geom2.getX(), geom2.getY())) {
                System.out.println("Clicked on a mushroom string");
                // Ha a current player Shroomer, akkor megnézzük, hogy az ő gombájának fonala-e
                if (gc.getCurrentPlayer() instanceof Shroomer shroomer) {
                    if (ms.getMushroom() == shroomer.getMushroom() && !ms.getDead()) {
                        clickedMushroomString = ms;
                        System.out.println("Selected mushroom string: " + ms.getName());
                        return;
                    }
                } else if (gc.getCurrentPlayer() instanceof Insecter) {
                    if (!ms.getDead()) {
                        clickedMushroomString = ms;
                        System.out.println("Selected mushroom string: " + ms.getName());
                        return;
                    }
                }
            }
        }
    }

    /**
     * Eldönti, hogy egy kattintás eléggé közel volt-e egy fonalhoz
     * @param clickX X koordinátája a kattintásnak
     * @param clickY Y koordinátája a kattintásnak
     * @param x1 X koordinátája a fonal elejének
     * @param y1 Y koordinátája a fonal elejének
     * @param x2 X koordinátája a fonal végének
     * @param y2 Y koordinátája a fonal végének
     * @return igaz, ha a kattintás eléggé közel esik a fonalhoz
     */
    private boolean isClickNearLine(int clickX, int clickY, float x1, float y1, float x2, float y2) {
        final int THRESHOLD = 10;

        double lineLength = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
        System.out.println("Line length: " + lineLength);
        if (lineLength == 0) return false;

        double distance = Math.abs((x2 - x1) * (y1 - clickY) - (x1 - clickX) * (y2 - y1)) / lineLength;
        System.out.println("Distance: " + distance);
        if (distance > THRESHOLD) return false;

        double dotProduct = ((clickX - x1) * (x2 - x1) + (clickY - y1) * (y2 - y1)) / (lineLength * lineLength);
        System.out.println("Dot product: " + dotProduct);
        return dotProduct >= 0 && dotProduct <= 1;
    }

    /**
     * Az inicializációs fázis kattintása (ha shroomer, akkor kifejlett gombatestet, máskülönben rovart tesz le a kattintott tektonra)
     * @param p A játékos, aki végzi a kattintást
     * @param mouseX a kattintás x koordinátája
     * @param mouseY a kattintás y koordinátája
     */
    private void initClick(Player p, int mouseX, int mouseY) {
        System.out.println("Clicked at " + mouseX + ", " + mouseY);
        selectTecton(mouseX, mouseY);

        if (clickedTecton != null) {
            if (p instanceof Shroomer) {
                MushroomBody mb = new MushroomBody(clickedTecton, ((Shroomer) p).getMushroom(), 2, false);
                for(MushroomBody m : gc.getPlanet().getMushbodies()) {
                    if(m.getLocation().equals(clickedTecton)) {
                        return;
                    }
                }

                // Setting geometry for mushroom body
                GeometryTecton tectonGeometry = clickedTecton.getGeometry();
                mb.setGeometry(gc.randomOffsetInsideCircle(tectonGeometry));

                gc.getPlanet().getMushbodies().add(mb);
            } else if (p instanceof Insecter) {
                for(Insect i : gc.getPlanet().getInsects()) {
                    if(i.getLocation().equals(clickedTecton)) {
                        return;
                    }
                }
                Insect i = new Insect(clickedTecton);

                // THIS IS THE FIX - Set geometry for the insect
                GeometryTecton tectonGeometry = clickedTecton.getGeometry();
                i.setGeometry(gc.randomOffsetInsideCircle(tectonGeometry));

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
            int code = keyHandler.getKeyCode();
            System.out.println("Current keyCode: " + code);
            //System.out.println("Clicked at " + mouseX + ", " + mouseY);
            //A gombára,vagy fonalra mehet
            if(keyHandler.getKeyCode() == KeyHandler.KEY_MUSHROOM){ // click on mushroom
                System.out.println("Mushroom at " + mouseX + ", " + mouseY);
                selectMushroomBody(mouseX, mouseY);
                keyHandler.resetKeyCode();
            }
            else if(keyHandler.getKeyCode() == KeyHandler.KEY_BRANCH) { // branch existing hypha
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
        System.out.println("Second click at " + mouseX + ", " + mouseY);
        if(p instanceof Shroomer) {
            //Tektonra mehet, ahova a spórát szórjuk, vagy fonalat növesztünk oda. Egyelőre ideiglenes elágazás, és a gombafonalas cuccok is csak így láthatatlanban első gondolatra így kellene
            selectTecton(mouseX, mouseY);
            if(clickedTecton != null){
                if(keyHandler.getKeyCode() == KeyHandler.KEY_SPREAD_SPORE){ //S = spread spores
                    Spore sp = clickedMushroomBody.spreadSpores(clickedTecton, "spore", "random");
                    if(sp != null){
                        gc.getPlanet().getSpores().add(sp);
                        System.out.println("Spore added");
                        GeometryTecton tectonGeometry = clickedTecton.getGeometry();
                        sp.setGeometry(gc.randomOffsetInsideCircle(tectonGeometry));
                        gc.nextTurnCheck();
                    }
                }
                else if(keyHandler.getKeyCode() == KeyHandler.KEY_GROW_BODY) {
                    MushroomBody mb = clickedMushroomBody.giveBirth(gc.getPlanet().getSpores(), clickedTecton, gc.getPlanet().getMushbodies(), gc.getPlanet().getMushstrings(),(Shroomer) gc.getCurrentPlayer());
                    if(mb != null){
                        gc.getPlanet().getMushbodies().add(mb);
                        System.out.println("MushroomBody added");
                        GeometryTecton tectonGeometry = clickedTecton.getGeometry();
                        mb.setGeometry(gc.randomOffsetInsideCircle(tectonGeometry));
                        gc.nextTurnCheck();
                    }
                }
                else if(clickedTecton.canGrowHypha(gc.getPlanet().getMushstrings())){
                    if(clickedMushroomString != null){
                        if(clickedMushroomString.branch(clickedTecton, gc.getPlanet().getMushstrings()))
                            gc.nextTurnCheck();
                    }
                    else if(clickedMushroomBody != null && keyHandler.getKeyCode() == KeyHandler.KEY_HYPHA){ // H = hypha
                            if(clickedMushroomBody.getLocation().equals(clickedTecton)){
                                ArrayList<Tecton> connection = new ArrayList<>();
                                connection.add(clickedTecton);
                                connection.add(null);
                                GeometryString geom = new GeometryString(clickedMushroomBody.getGeometry().getX(), clickedMushroomBody.getGeometry().getY(), clickedTecton.getGeometry().getX(), clickedTecton.getGeometry().getY());
                                gc.getPlanet().getMushstrings().add(new MushroomString("hypha", ((Shroomer) gc.getCurrentPlayer()).getMushroom(), connection, new ArrayList<>(Arrays.asList(null, null)), gc.getTurnCounter(), geom));
                                gc.nextTurnCheck();
                            }
                    }
                }
            }
            reset();
        }
        else if(p instanceof Insecter) {
            //A kattintás egy tektonra, spórára, vagy fonalra mehet, de lehet itt is célszerű lenne gombokkal szabályozni, hogy ne lehessen véletlenül mondjuk félrekattintani, emiatt az if-ek ideiglenesek
            if(keyHandler.getKeyCode() == KeyHandler.KEY_MOVE){ // M = move
                selectTecton(mouseX, mouseY);
            }
            else if(keyHandler.getKeyCode() == KeyHandler.KEY_EAT){ //E = eat spore
                selectSpore(mouseX, mouseY);
            }
            else if(keyHandler.getKeyCode() == KeyHandler.KEY_CUT) { //C = cut
                selectMushroomString(mouseX, mouseY);
            }

            if(clickedTecton != null && clickedInsect.getLocation() != clickedTecton && clickedTecton.isNeighbour(clickedInsect.getLocation()) && gc.getPlanet().getMushstrings().stream()
                    .anyMatch(ms -> ms.getConnection().contains(clickedInsect.getLocation()) && ms.getConnection().contains(clickedTecton))) {
                clickedInsect.move(clickedTecton);
                gc.nextTurnCheck();
            }
            else if(clickedSpore != null) {
                if (clickedInsect.getLocation() == clickedSpore.getLocation()) {
                    SporeConsumptionVisitor v = new SporeConsumptionVisitor(clickedInsect, gc);
                    ((SporeAccept) clickedSpore).accept(v);
                    gc.nextTurnCheck();
                }
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
        if (firstClick) {
            firstGameClick(p, mouseX, mouseY);
        } else {
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
        if (gc.getInit()) {
            initClick(p, x, y);
        } else {
            gameClick(p, x, y);
        }
        repaintCallback.run();
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        // Ha a játék inicializáló fázisban van
        Player p = gc.getCurrentPlayer();
        if (gc.getInit()) {
            if (p instanceof Shroomer) {
                gamePanel.setShineOn(GamePanel.ShineOn.TECTON);
            } else if (p instanceof Insecter) {
                gamePanel.setShineOn(GamePanel.ShineOn.TECTON);
            }
        } else {
            // Normál játékmenet esetén
            int key = keyHandler.getKeyCode();

            if (p instanceof Shroomer) {
                if (firstClick) {
                    // Első kattintás esetén a megfelelő kiemelés beállítása a billentyű alapján
                    if (key == KeyHandler.KEY_GROW_BODY) {
                        gamePanel.setShineOn(GamePanel.ShineOn.TECTON);
                    } else if (key == KeyHandler.KEY_MUSHROOM) {
                        gamePanel.setShineOn(GamePanel.ShineOn.MUSHBODY);
                    } else if (key == KeyHandler.KEY_BRANCH) {
                        gamePanel.setShineOn(GamePanel.ShineOn.MUSHSTRING);
                    }
                } else {
                    // Második kattintás esetén
                    if (clickedMushroomBody != null && key == KeyHandler.KEY_SPREAD_SPORE) {
                        gamePanel.setShineOn(GamePanel.ShineOn.TECTON);
                    } else if (clickedMushroomString != null) {
                        gamePanel.setShineOn(GamePanel.ShineOn.TECTON);
                    }
                }
            } else if (p instanceof Insecter) {
                if (firstClick) {
                    gamePanel.setShineOn(GamePanel.ShineOn.INSECT);
                } else {
                    // Második kattintás esetén az insecter akcióinak megfelelő kiemelés
                    if (key == KeyHandler.KEY_MOVE) {
                        gamePanel.setShineOn(GamePanel.ShineOn.TECTON);
                    } else if (key == KeyHandler.KEY_EAT) {
                        gamePanel.setShineOn(GamePanel.ShineOn.SPORE);
                    } else if (key == KeyHandler.KEY_CUT) {
                        gamePanel.setShineOn(GamePanel.ShineOn.MUSHSTRING);
                    }
                }
            }
        }
        repaintCallback.run();
    }


    @Override
    public void mouseExited(MouseEvent e) {}

}