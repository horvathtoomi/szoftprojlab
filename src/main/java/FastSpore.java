package main.java;

/**
 * A FastSpore osztály egy gyorsító hatású spórát reprezentál, amely egy rovarral 
 * történő találkozás esetén megnöveli annak sebességét.
 */
public class FastSpore extends Spore implements SporeAccept
{
	/**
     * Konstruktor, amely inicializálja a gyorsító spórát.
     * 
     * @param nutrientValue   A spóra által biztosított tápanyag értéke.
     * @param mushroom        A gomba, amelyből a spóra származik.
     * @param location        A Tecton, ahol a spóra található.
     * @param name            A spóra neve.
     */
	FastSpore(int nutrientValue, Mushroom mushroom, Tecton location, String name)
	{
		super(nutrientValue, mushroom, location, name);
	}
	
	@Override
	public void accept(SporeVisitor visitor) {
	    visitor.visit(this);
	}
	
	/**
     * A spóra hatásának alkalmazása a megadott rovarra.
     * - Beállítja a rovar sebességét FAST-re
     * - Beállítja a begyűjtött tápanyagot.
     * - Elpusztítja a spórát.
     * 
     * @param insect A rovar, amelyre a spóra hatása alkalmazódik.
     */
	public void applyEffect(Insect insect)
	{
		insect.setSpeed(Insect.Speed.FAST);
		insect.setCollectedNutrients(nutrientValue);
		this.die();
	}
}
