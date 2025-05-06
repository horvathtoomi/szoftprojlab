package main.java;

/**
 * A SlowSpore osztály egy lassító hatású spórát reprezentál, amely egy rovarral 
 * történő találkozás esetén csökkenti annak sebességét.
 */
public class SlowSpore extends Spore implements SporeAccept
{

	/**
     * Konstruktor, amely inicializálja a lassító spórát.
     * 
     * @param nutrientValue   A spóra által biztosított tápanyag értéke.
     * @param mushroom        A gomba, amelyből a spóra származik.
     * @param location        A Tecton, ahol a spóra található.
     * @param name            A spóra neve.
     */
	SlowSpore(int nutrientValue, Mushroom mushroom, Tecton location, String name) {
		super(nutrientValue, mushroom, location, name);
	}
	
	@Override
	public void accept(SporeVisitor visitor) {
	    visitor.visit(this);
	}

	/**
     * A spóra hatásának alkalmazása a megadott rovarra.
     * - Beállítja a rovar sebességét SLOW-ra
     * - Beállítja a begyűjtött tápanyagot.
     * - Elpusztítja a spórát.
     * 
     * @param insect A rovar, amelyre a spóra hatása alkalmazódik.
     */
	public void applyEffect(Insect insect) {
		insect.setSpeed(Insect.Speed.SLOW);
		insect.setCollectedNutrients(nutrientValue);
		this.die();
	}
}
