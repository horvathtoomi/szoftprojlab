package main.java;

import java.util.ArrayList;
import java.util.Random;

/**
 * Absztrakt Tecton osztály, amely a játék mezőit reprezentálja.
 * Egyedi névvel rendelkezik, ezért a Nameable leszármazottja, valamint vannak szomszédai és eltárolja, hogy mennyi fonal lehet rajta maximálisan
 */
public abstract class Tecton extends Nameable implements Updatable {

    private ArrayList<Tecton> neighbours;
    private final int maxStrings;
    private boolean bodyGrown = false;

    /**
     * Konstruktor: létrehoz egy új Tecton példányt megadott névvel és maximális fonalszámmal.
     *
     * @param name       A tecton neve.
     * @param maxStrings A maximálisan tárolható gombafonalak száma.
     */
    Tecton(String name, int maxStrings) {
        setName(name);
        this.maxStrings = maxStrings;
        neighbours = new ArrayList<>();
    }

    // Getterek, Setterek
    public ArrayList<Tecton> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(ArrayList<Tecton> neighbours) {
        this.neighbours = neighbours;
    }

    public void addNeighbour(Tecton tecton) {
        this.neighbours.add(tecton);
    }

    public int getMaxStrings() {
        return maxStrings;
    }
    
    /** @return true, ha már nőtt rajta egy MushroomBody. */
    public boolean hasBodyGrown() {
        return bodyGrown;
    }
    
    /** Jelzi, hogy ezután már nem lehet rajta új body. */
    public void markBodyGrown() {
        this.bodyGrown = true;
    }

    /**
     * Meghatározza az aktuális Tecton szomszédait egy lista alapján.
     *
     * @param allTectons Az összes tectont tartalmazó lista.
     * @return Az aktuális tecton szomszédainak listája.
     */
    public ArrayList<Tecton> determineNeighbours(ArrayList<Tecton> allTectons) {
        ArrayList<Tecton> result = new ArrayList<>();
        for (Tecton t : allTectons) {
            if (t != this && this.isNeighbour(t)) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * Az updatable interfész megvalósítása. Ha a randomitás igaz, akkor 1/5-öd eséllyel eltörik a tekton a körben.
     *
     * @param random A randomitás kapcsolója
     */
    public void update(boolean random){
    	if(random){
			Random rng = new Random();
			int n = rng.nextInt(5); 
			if(n == 0)
				createSplitTectons(this.getName() + "_1", this.getName() + "_2");
    	}
    }

    /**
     * Meghatározza, hogy egy másik Tecton szomszédja-e az aktuálisnak.
     * Ez majd csak a grafikus felület megcsinálása után fog megvalósulni. Egyelőre minden tekton szomszéd lesz.
     */
    public boolean isNeighbour(Tecton t) {
        return true;
    }

    /**
     * Eldonti, hogy novesztheto-e új fonal. Atveszi a fonalak listáját, amin megvizsgálja, hogy hány fonal található ezen a tektonon.
     * Ha a maxStrings-mél kisebb, akkor van még rajta hely, különben nincs.
     *
     * @param strings A gombafonalakat tartalmazó lista.
     * @return true, ha még lehet új fonalat növeszteni; egyébként false.
     */
    public boolean canGrowHypha(ArrayList<MushroomString> strings) {
        int counter = 0;
        for (MushroomString string : strings) {
            if (string.getConnection().contains(this)) {
                counter++;
            }
        }
        return counter < maxStrings;
    }
    
    /**
     * A split logikáját konkrét leszármazottak valósítják meg.
     * Két új példányt ad vissza.
     */
    protected abstract Tecton[] createSplitTectons(String newName1, String newName2);
}
