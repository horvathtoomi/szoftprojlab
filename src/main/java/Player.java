package main.java;


/**
 * Az absztrakt Player osztály egy általános játékost reprezentál, amelynek van neve, pontszáma
 * és hátralévő akciói. Kétféle játékos származhat belőle: Shroomer és Insecter.
 */
public abstract class Player extends Nameable implements Updatable {
	

    protected int score;
    protected int remainingActions;

    /**
     * Létrehoz egy új játékost.
     *
     * @param name a játékos neve
     * @param infinite ha true, akkor a játékos gyakorlatilag végtelen akcióval rendelkezik. Teszteléshez kell.
     */
    Player(String name, boolean testing) {
        setName(name);
        this.score = 0;
        update(testing);
    }
    
    /**
     * Visszaadja a játékos pontszámát.
     *
     * @return a játékos aktuális pontszáma
     */
    public int getScore() {
        return score;
    }

    /**
     * Beállítja a játékos pontszámát.
     *
     * @param score az új pontszám
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Visszaadja a játékos hátralévő akcióinak számát.
     *
     * @return a hátralévő akciók száma
     */
    public int getActions(){
        return remainingActions;
    }

    /**
     * Beállítja a játékos hátralévő akcióinak számát.
     *
     * @param actions az új akciószám
     */
    public void setActions(int actions){
        this.remainingActions = actions;
    }
    
    /**
     * Meghatározza, hogy a játékos végrehajthatja-e az adott parancsot.
     *
     * @param cmd a parancs szövege
     * @return true, ha a játékos végrehajthatja a parancsot, különben false.
     */
    protected abstract boolean canExecuteCommand(String cmd);

    /**
     * Csökkenti a hátralévő akciók számát eggyel.
     */
    protected void takeAction() {
        remainingActions--;
    }

    /**
     * Passzolja a játékos körét: az akciók száma nullára csökken.
     */
    public void pass() {
        remainingActions = 0;
    }
    
    /**
     * Az Updatable interfész felüldefiniált update függvénye. 
     * Visszaállítja a játékos akcióinak számát alapértelmezettre.
     *
     * @param testing A tesztelő állapot eldöntését meghatározó boolean.
     */
    public void update(boolean testing) {
    	if(!testing) {
        	setActions(3);
        }
        else {
        	setActions(10000);
        }
    }
}