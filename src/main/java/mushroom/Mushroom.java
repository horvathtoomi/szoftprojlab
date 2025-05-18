package main.java.mushroom;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A Mushroom osztály egy gombát reprezentál a játékban, amelyhez tartozik egy gombász (shroomer),
 * egy név, valamint lehet élő vagy halott állapotban.
 */
public class Mushroom implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	boolean dead;
	boolean testing;
	
	/**
     * Konstruktor, amely inicializál egy új Mushroom példányt.
     *
     * @param testing Tesztelési mód jelzője. Ha igaz, a gomba tesztelési üzemmódban jött létre.
     */
	public Mushroom(boolean testing) {
		dead = false;
		this.testing = testing;
	}

	/**
	 * Visszaadja, hogy egy gomba hallott-e
	 *
	 * @return i/h, állapotától függően
	 */
	public boolean getDead() {
		return dead;
	}
	
	/**
     * Meghívása esetén a gomba elpusztul: minden hozzá tartozó MushroomBody objektumot is elpusztít,
     * majd a saját állapotát halottra állítja.
     *
     * @param strings Az összes gombafonal listája.
     * @param bodies Az összes MushroomBody gombatest listája.
     */
	public void die(ArrayList<MushroomString> strings, ArrayList<MushroomBody> bodies) {
        for (MushroomBody body : bodies) {
            if (body.getMushroom() == this) {
                body.die(strings);
            }
        }
		dead = true;
	}
}