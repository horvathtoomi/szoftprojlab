package main.java.spore;

import main.java.tecton.Tecton;
import main.java.insect.Insect;
import main.java.mushroom.Mushroom;

/**
 * A MultiplierSpore egy olyan spóra, amely nemcsak tápanyagot ad a rovarnak, 
 * hanem klónozza is azt – egy új rovar jön létre ugyanazon a helyen, módosított névvel.
 */
public class MultiplierSpore extends Spore implements SporeAccept
{
	
	private static class CloneCounter {
	    private static int counter = 1;
	    static synchronized int next() { return counter++; }
	}
	
	/**
     * Konstruktor, amely inicializálja a sokszorozó spórát.
     * 
     * @param nutrientValue   A spóra által biztosított tápanyag értéke.
     * @param mushroom        A gomba, amelyből a spóra származik.
     * @param location        A Tecton, ahol a spóra található.
     * @param name            A spóra neve.
     */
    public MultiplierSpore(int nutrientValue, Mushroom mushroom, Tecton location, String name)
    {
        super(nutrientValue, mushroom, location, name);
    }
    
    @Override
    public void accept(SporeVisitor visitor) {
        visitor.visit(this);
    }
    
    /**
     * A spóra hatásának alkalmazása a megadott rovarra.
     * - Beállítja a begyűjtött tápanyagot.
     * - Elpusztítja a spórát.
     * 
     * @param insect A rovar, amelyre a spóra hatása alkalmazódik.
     */
    public void applyEffect(Insect insect)
    {
        insect.setCollectedNutrients(nutrientValue);
        
        this.die();
    }
    
    /**
     * Egy új rovar létrehozása a meglévő alapján.
     * A klón ugyanott, és "_clone" utótaggal ellátott névvel jelenik meg.
     * Ez a metódus meghívja az `applyEffect()` függvényt is az eredeti rovaron.
     * 
     * @param insect Az eredeti rovar
     * @return Az új, klónozott rovar
     */
    public Insect makeNewInsect(Insect insect)
    {
        Tecton location = insect.getLocation();
        return new Insect(location, insect.getName() + "_clone" + CloneCounter.next());
    }
}
