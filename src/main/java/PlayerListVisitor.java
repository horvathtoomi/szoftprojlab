package main.java;

import java.util.ArrayList;

/**
 * A code PlayerListVisitor egy látogató, amely a játékos adatait
 * sorokba formázza megjelenítés céljából.
 * 
 * Egy Shroomer vagy Insecter játékos típushoz különféle
 * adatokat listáz ki (név, pontszám, gombák vagy rovarok).
 */
public class PlayerListVisitor implements PlayerVisitor {

    private ArrayList<String> lines = new ArrayList<>();
    private String playerName;

    /**
     * Létrehozza a PlayerListVisitor példányt a megadott játékosnévvel.
     * 
     * @param playerName a játékos neve
     */
    public PlayerListVisitor(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Visszaadja az összegyűjtött szöveges sorokat.
     * 
     * @return a sorok listája
     */
    public ArrayList<String> getLines() {
        return lines;
    }

    /**
     * Látogatja a Shroomer típusú játékost, és összegyűjti az adatait.
     * 
     * @param shroomer a meglátogatott Shroomer példány
     */
    @Override
    public void visit(Shroomer shroomer) {
        lines.add("Type: Shroomer");
        lines.add("Name: " + playerName);
        lines.add("Score: " + shroomer.getScore());
        lines.add("Mushroom: " + shroomer.getMushroom().getName());
    }

    /**
     * Látogatja az Insecter típusú játékost, és összegyűjti az adatait.
     * 
     * @param insecter a meglátogatott Insecter példány
     */
    @Override
    public void visit(Insecter insecter) {
        lines.add("Type: Insecter");
        lines.add("Name: " + playerName);
        lines.add("Score: " + insecter.getScore());
        lines.add("Insects: ");
        for (Insect insect : insecter.getInsects()) {
            lines.add(" - " + insect.getName());
        }
    }
}
