package main.java.tecton;

import main.java.mushroom.MushroomString;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Absztrakt Tecton osztály, amely a játék mezőit reprezentálja.
 * Egyedi névvel rendelkezik, ezért a Nameable leszármazottja, valamint vannak szomszédai és eltárolja, hogy mennyi fonal lehet rajta maximálisan
 */
public abstract class Tecton implements Serializable{

    private ArrayList<Tecton> neighbours;
    private final int maxStrings;
    private boolean bodyGrown = false;
    private GeometryTecton geometry;
    boolean dead = false;

    /**
     * Konstruktor: létrehoz egy új Tecton példányt megadott névvel és maximális fonalszámmal.
     *
     * @param maxStrings A maximálisan tárolható gombafonalak száma.
     */
    public Tecton(int maxStrings) {
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

    public GeometryTecton getGeometry() {
        return this.geometry;
    }

    public void setGeometry(GeometryTecton geometry) {
        this.geometry = geometry;
    }

    public int getMaxStrings() {
        return maxStrings;
    }
    public boolean getDead() {return dead;}
    public void setDead(boolean dead) {this.dead = dead;}
    
    /** @return true, ha már nőtt rajta egy MushroomBody. */
    public boolean hasSpace() {
        return !bodyGrown;
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
     * Meghatározza, hogy egy másik Tecton szomszédja-e az aktuálisnak. Ez akkor igaz, ha 400-nál kisebb a kettő távolsága
     */
    public boolean isNeighbour(Tecton t) {
        if (this.geometry == null || t.geometry == null) return false;

        double dx = this.geometry.getX() - t.geometry.getX();
        double dy = this.geometry.getY() - t.geometry.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        System.out.println(distance);

        return distance <= 300;
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
    public abstract void createSplitTectons(ArrayList<Tecton> tectons);
}
