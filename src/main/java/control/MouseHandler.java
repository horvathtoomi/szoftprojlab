package main.java.control;

import main.java.GameController;
import main.java.player.PlayerVisitor;
import main.java.GamePanel;
import main.java.Geometry;
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
import java.util.ArrayList;

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

    /**
     * Létrehozza az új példányt a megfelelő paraméterekkel
     * @param gc A GameController példány, amihez tartozik
     * @param repaintCallback A képernyő újrarajzolását végző metódus
     * @param gamePanel A játék, amihez tartozik
     * @param keyHandler A keyHandler pédlány, amely a játékhoz tartozik
     */
    public MouseHandler(GameController gc, Runnable repaintCallback, GamePanel gamePanel, KeyHandler keyHandler) {
        this.gc = gc;
        this.repaintCallback = repaintCallback;
        this.keyHandler = keyHandler;
        this.gamePanel = gamePanel;
    }

    /**
     * Visszaállítja a kijelölést, és a kiválasztott objektumokat semmire a második kattintás után.
     */
    public void reset(){
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
    public void selectTecton(int x, int y) {
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
    public void selectMushroomBody(int x, int y) {
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
    public void selectInsect(int x, int y) {
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
    public void selectSpore(int x, int y) {
        // Beállítjuk a kiemelést a spórákra
        gamePanel.setShineOn(GamePanel.ShineOn.SPORE);
        repaintCallback.run();
        for (Spore s : gc.getPlanet().getSpores()) {
            float tx = s.getGeometry().getX();
            float ty = s.getGeometry().getY();
            int radius = 35;
            if ((x <= (tx + radius) && x >= (tx - radius)) && (y <= (ty + radius) && y >= (ty - radius))) {
                clickedSpore = s;
                //System.out.println("Selected spore: " + s.getName());
                break;
            }
        }
    }

    /**
     * Egy fonal kiválasztása. Megvizsgálja, hogy a kattintás pozíciója ráesik-e egy objektumra.
     * @param x a kattintás x koordinátája
     * @param y a kattintás y koordinátája
     */
    public void selectMushroomString(int x, int y) {
        
        // Beállítjuk a kiemelést a gombafonalokra
        gamePanel.setShineOn(GamePanel.ShineOn.MUSHSTRING);
        repaintCallback.run();
        for (MushroomString ms : gc.getPlanet().getMushstrings()) {
        	if(ms.getConnection().get(0).getGeometry() == null
        			&& ms.getConnection().get(1).getGeometry() == null && ms.getConnection().size() != 2)
        		continue;
        	ArrayList<Tecton> connections = ms.getConnection();

            Geometry geom1;
            Geometry geom2;
            if(connections.get(1) != null){
                 geom1 = connections.get(0).getGeometry();
                 geom2 = connections.get(1).getGeometry();
                 //System.out.println("2 tektont köt össze");
            }
            else{
                geom1 = new Geometry(ms.getGeometry().getX(), ms.getGeometry().getY());
                geom2 = connections.get(0).getGeometry();
                //System.out.println("új fonal");
            }

            if (isClickNearLine(x, y, geom1.getX(), geom1.getY(), geom2.getX(), geom2.getY())) {
                clickedMushroomString = null;
                gc.getCurrentPlayer().accept(new PlayerVisitor() {
                    @Override
                    public void visit(Shroomer shroomer) {
                        if (ms.getMushroom() == shroomer.getMushroom() && !ms.getDead()) {
                            clickedMushroomString = ms;
                        }
                    }
                    @Override
                    public void visit(Insecter insecter) {
                        if (!ms.getDead()) {
                            clickedMushroomString = ms;
                        }
                    }
                });
                if (clickedMushroomString != null) {
                    return;
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
        //System.out.println("Line length: " + lineLength);
        if (lineLength == 0) return false;

        double distance = Math.abs((x2 - x1) * (y1 - clickY) - (x1 - clickX) * (y2 - y1)) / lineLength;
        //System.out.println("Distance: " + distance);
        if (distance > THRESHOLD) return false;

        double dotProduct = ((clickX - x1) * (x2 - x1) + (clickY - y1) * (y2 - y1)) / (lineLength * lineLength);
        //System.out.println("Dot product: " + dotProduct);
        return dotProduct >= 0 && dotProduct <= 1;
    }

    /**
     * Az inicializációs fázis kattintása (ha shroomer, akkor kifejlett gombatestet, máskülönben rovart tesz le a kattintott tektonra)
     * @param p A játékos, aki végzi a kattintást
     * @param mouseX a kattintás x koordinátája
     * @param mouseY a kattintás y koordinátája
     */
    private void initClick(Player p, int mouseX, int mouseY) {
        selectTecton(mouseX, mouseY);

        if (clickedTecton != null) {
            p.accept(new PlayerVisitor() {
                @Override
                public void visit(Shroomer shroomer) {
                    int mushCountBefore = gc.getPlanet().getMushbodies().size();
                    handleGameClick(shroomer, mouseX, mouseY);
                    int mushCountAfter = gc.getPlanet().getMushbodies().size();
                    if (mushCountAfter > mushCountBefore) {
                        gc.setInitCheck();
                        gc.setCurrentPlayerToNextPlayer();
                        reset();
                    }
                }
                @Override
                public void visit(Insecter insecter) {
                    handleGameClick(insecter, mouseX, mouseY);
                    gc.setInitCheck();
                    gc.setCurrentPlayerToNextPlayer();
                    reset();
                }
            });
        }
    }

    /**
     * Az éles játék kattintásának feldolgozása aszerint, hogy ez a játékos "első kattintása" a legutóbbi akció óta, vagy sem
     * @param p A játékos, aki végzi a kattintást
     * @param mouseX a kattintás x koordinátája
     * @param mouseY a kattintás y koordinátája
     */
    void handleGameClick(Player p, int mouseX, int mouseY) {
        ClickAction action = p.getClickAction(gc.getInit(), firstClick, keyHandler, this);
        action.execute(gc, mouseX, mouseY);
        repaintCallback.run();
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
            handleGameClick(p, x, y);
        }
        repaintCallback.run();
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
     * Az objektumok megfelelő módon történő kiemeléséért felelős függvény
     * @param e A kattintás eseménye
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // Ha a játék inicializáló fázisban van
        Player p = gc.getCurrentPlayer();
        if (gc.getInit()) {
            gamePanel.setShineOn(GamePanel.ShineOn.TECTON);   // mindkét játékos-típusnál ugyanaz
        } else {
            int key = keyHandler.getKeyCode();
            p.accept(new PlayerVisitor() {
                @Override
                public void visit(Shroomer shroomer) {
                    if (firstClick) {
                        if (key == KeyHandler.KEY_GROW_BODY) {
                            gamePanel.setShineOn(GamePanel.ShineOn.TECTON);
                        } else if (key == KeyHandler.KEY_MUSHROOM) {
                            gamePanel.setShineOn(GamePanel.ShineOn.MUSHBODY);
                        } else if (key == KeyHandler.KEY_BRANCH) {
                            gamePanel.setShineOn(GamePanel.ShineOn.MUSHSTRING);
                        }
                    } else {
                        if (clickedMushroomBody != null && key == KeyHandler.KEY_SPREAD_SPORE
                                || clickedMushroomString != null) {
                            gamePanel.setShineOn(GamePanel.ShineOn.TECTON);
                        }
                    }
                }
                @Override
                public void visit(Insecter insecter) {
                    if (firstClick) {
                        gamePanel.setShineOn(GamePanel.ShineOn.INSECT);
                    } else {
                        if (key == KeyHandler.KEY_MOVE) {
                            gamePanel.setShineOn(GamePanel.ShineOn.TECTON);
                        } else if (key == KeyHandler.KEY_EAT) {
                            gamePanel.setShineOn(GamePanel.ShineOn.SPORE);
                        } else if (key == KeyHandler.KEY_CUT) {
                            gamePanel.setShineOn(GamePanel.ShineOn.MUSHSTRING);
                        }
                    }
                }
            });
        }
        repaintCallback.run();
    }


    @Override
    public void mouseExited(MouseEvent e) {}

    public MushroomBody getClickedMushroomBody() {return clickedMushroomBody;}
    public MushroomString getClickedMushroomString() {return clickedMushroomString;}
    public Tecton getClickedTecton() {return clickedTecton;}
    public Insect getClickedInsect() {return clickedInsect;}
    public Spore getClickedSpore() {return clickedSpore;}
    public void setFirstClick(boolean firstClick) {this.firstClick = firstClick;}
    public GameController getGameController(){return gc;}
}