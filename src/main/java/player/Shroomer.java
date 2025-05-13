package main.java.player;

import main.java.mushroom.Mushroom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A Shroomer osztály egy játékos típust reprezentál, amely egyetlen
 * Mushroom entitás felett rendelkezik.
 * A Shroomer meghatározott parancsokat hajthat végre, mint például:
 * GrowHypha, MushroomBody, SpreadSpore, valamint Pass.
 */
public class Shroomer extends Player implements PlayerAccept, Serializable {

    Mushroom mushroom;

    /**
     * Egy új Shroomer példányt hoz létre megadott névvel, gombával és akciószámmal.
	 *
     * @param name     a játékos neve.
     * @param infinite ha true, akkor végtelen számú akcióval rendelkezik. Teszteléshez szükséges.
     */
    public Shroomer(String name, boolean infinite, Mushroom mushroom) {
        super(name, infinite);
        this.mushroom = mushroom;
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

