package main.java.player;

import main.java.insect.Insect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
    public void addInsect(Insect i){
    	insects.add(i);
    }
    
    /**
     * Beállítja a játékos által irányított rovarok listáját.
     *
     * @param insects az új rovarlista
     */
    public void setInsects(List<Insect> insects) {this.insects = insects;}

    /**
     * Meghatározza, hogy a játékos végrehajthatja-e az adott parancsot.
     * Az Insecter az alábbi parancsokat használhatja: Move, Consume, Cut, Pass.
     *
     * @param cmd a parancs szövege
     * @return true, ha a parancs engedélyezett, különben false
     */
    @Override
    public boolean canExecuteCommand(String cmd){
        ArrayList<String> allowed = new ArrayList<>(Arrays.asList("Move", "Consume", "Cut", "Pass", "move", "consume", "cut", "pass"));
        return allowed.contains(cmd);
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
}
