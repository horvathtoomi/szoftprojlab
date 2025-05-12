package main.java.mushroom;

import java.util.ArrayList;
import main.java.tecton.Tecton;

/**
 * A Mushroom osztály egy gombát reprezentál a játékban, amelyhez tartozik egy gombász (shroomer),
 * egy név, valamint lehet élő vagy halott állapotban.
 */
public class Mushroom {
	boolean dead;
	boolean testing;
	
	/**
     * Konstruktor, amely inicializál egy új Mushroom példányt.
     *
     * @param name A gomba neve.
     * @param testing Tesztelési mód jelzője. Ha igaz, a gomba tesztelési üzemmódban jött létre.
     */
	public Mushroom(boolean testing) {
		dead = false;
		this.testing = testing;
	}

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
	/**
     * Létrehoz és visszaad egy teljesen kifejlett MushroomBody példányt a megadott tektonra helyezve.
     *
     * @param tecton A tekton, amelyre a gombatestet helyezzük.
     * @param name Az új gombatest neve.
     * @return A létrehozott MushroomBody példány.
     */
	public MushroomBody placeGrownMushroom(Tecton tecton, String name) {
        return new MushroomBody(tecton, this, 2, name, testing);
	}
}