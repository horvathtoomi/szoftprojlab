package main.java.spore;

import main.java.tecton.Tecton;
import main.java.insect.Insect;
import main.java.mushroom.Mushroom;

/**
 *A ParalyzerSpore osztály egy mozgást megtiltó hatású spórát reprezentál.
 */
public class ParalyzerSpore extends Spore implements SporeAccept {
	/**
     * Konstruktor, amely inicializálja a bénító spórát.
     * 
     * @param nutrientValue   A spóra által biztosított tápanyag értéke.
     * @param mushroom        A gomba, amelyből a spóra származik.
     * @param location        A Tecton, ahol a spóra található.
     * @param name            A spóra neve.
     */
	public ParalyzerSpore(int nutrientValue, Mushroom mushroom, Tecton location, String name) {
		super(nutrientValue, mushroom, location, name);
	}
	
	@Override
	public void accept(SporeVisitor visitor) {
	    visitor.visit(this);
	}
	
	/**
     * A spóra hatásának alkalmazása a megadott rovarra.
     * - Kikapcsolja a mozgási képességet.
     * - Beállítja a begyűjtött tápanyagot.
     * - Elpusztítja a spórát.
     * 
     * @param insect A rovar, amelyre a spóra hatása alkalmazódik.
     */
	public void applyEffect(Insect insect) {
		insect.setCanMove(false);
		insect.setCollectedNutrients(nutrientValue);
		this.die();
	}
}
