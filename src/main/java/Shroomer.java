package main.java;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A Shroomer osztály egy játékos típust reprezentál, amely egyetlen
 * Mushroom entitás felett rendelkezik.
 * A Shroomer meghatározott parancsokat hajthat végre, mint például:
 * GrowHypha, MushroomBody, SpreadSpore, valamint Pass.
 */
public class Shroomer extends Player implements PlayerAccept {

    Mushroom mushroom;
    private boolean placedGrownMushBody = false;

    /**
     * Egy új Shroomer példányt hoz létre megadott névvel, gombával és akciószámmal.
	 *
     * @param name     a játékos neve.
     * @param infinite ha true, akkor végtelen számú akcióval rendelkezik. Teszteléshez szükséges.
     */
    public Shroomer(String name, boolean infinite) {
        super(name, infinite);
    }
    
    /**
     * Visszaadja a játékoshoz tartozó Mushroom példányt.
     *
     * @return a játékos gombája.
     */
    public Mushroom getMushroom() {
        return mushroom;
    }

    /**
     * Beállítja a játékoshoz tartozó gombát.
     *
     * @param mushroom az új Mushroom példány
     */
    public void setMushroom(Mushroom mushroom) {
        this.mushroom = mushroom;
    }
    
    /**
     * Visszaadja a játékos játék eleji kifejlett gombáját.
     */
    public boolean havePlacedGrownMushBody() {
        return placedGrownMushBody;
    }
    
    /**
     * Ha már a játékos tett le kifejlett gombát, akkor a placedGrownMushBody igaz lesz.
     *
     */
    public void placedGrownMushBody() {
        placedGrownMushBody = true;
    }

    /**
     * Meghatározza, hogy a játékos végrehajthatja-e az adott parancsot.
     *
     * @param cmd a parancs neve.
     * @return true, ha a parancs engedélyezett, különben false.
     */
    @Override
    protected boolean canExecuteCommand(String cmd){
        ArrayList<String> allowed = new ArrayList<>(Arrays.asList("MushroomString", "Branch", "MushroomBody", "SpreadSpore", "Pass", "mushroomstring", "branch", "mushroombody", "spreadspore", "pass"));
        return allowed.contains(cmd);
    }

    /**
     * Elfogadja a látogatót a Visitor minta szerint.
     *
     * @param visitor a PlayerVisitor példány, amely meglátogatja ezt a játékost
     */
    @Override
    public void accept(PlayerVisitor visitor) {
    	if(mushroom != null)
    		visitor.visit(this);
    }
}

