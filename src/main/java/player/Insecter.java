package main.java.player;

import main.java.control.*;
import main.java.insect.Insect;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Az Insecter osztály a játékos egy fajtáját reprezentálja,
 * aki rovarokat irányít a játék során.
 */
public class Insecter extends Player implements PlayerAccept, Serializable {

    List<Insect> insects;

    /**
     * Létrehoz egy új {@code Insecter} játékost a megadott névvel és akciókorlátozással.
     *
     * @param name a játékos neve
     * @param infinite ha true, akkor a játékosnak gyakorlatilag végtelen akciója van. Teszteléshez szükséges.
     */
    public Insecter(String name, boolean infinite) {
        super(name, infinite);
        insects = new ArrayList<>();
    }

    /**
     * Visszaadja a játékos által irányított rovarok listáját.
     *
     * @return a rovarok listája
     */
    public List<Insect> getInsects() {
        return insects;
    }
    
    /**
     * Hozzáad egy rovart a játékos által irányított rovarokhoz.
     *
     * @param i a hozzáadandó rovar
     */
    public void addInsect(Insect i) {
    	insects.add(i);
    }
    
    /**
     * Beállítja a játékos által irányított rovarok listáját.
     *
     * @param insects az új rovarlista
     */
    public void setInsects(List<Insect> insects) {
        this.insects = insects;
    }
    
    /**
     * Meghívja a látogatót a Visitor tervezési minta alapján.
     *
     * @param visitor a látogató objektum
     */
    @Override
    public void accept(PlayerVisitor visitor) {
    	visitor.visit(this);
    }

    /**
     * Az adott játékos (jelenlegi állapotában és inputkóddal) mit csináljon a következő kattintásra.
     *
     * @param init A játék inicializációs fázisban van?
     * @param isFirstClick Ez az "első" kattintás?
     * @param keyHandler A kH példány, ami a végrehajtható akciókat szabályozza
     * @param mouseHandler A mH példány, amiből az inputot kapta
     */
    @Override
    public ClickAction getClickAction(boolean init, boolean isFirstClick, KeyHandler keyHandler, MouseHandler mouseHandler) {
        if(init){
            return new InsecterInitClickAction(mouseHandler);
        }
        if (isFirstClick) {
            return new InsecterFirstClickAction(mouseHandler);
        } else {
            return new InsecterSecondClickAction(keyHandler, mouseHandler);
        }
    }

    /**
     * Visszaadja az adott játékos típus "színét", amivel meg kell őt jeleniteni játék közben a jobb alsó sarokban
     *
     * @return A játékos színe (sárga)
     */
    @Override
    public Color getPlayerColor() {
        return new Color(255, 215, 0);
    }
}
