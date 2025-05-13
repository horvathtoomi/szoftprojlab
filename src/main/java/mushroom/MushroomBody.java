package main.java.mushroom;

import main.java.Geometry;
import main.java.Updatable;
import main.java.tecton.*;
import main.java.spore.*;
import main.java.player.Shroomer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * A MushroomBody osztály a gombatest viselkedését modellezi, beleértve a spórák terjesztését, 
 * a növekedést és a halált. Ezen kívül kezeli a gomba elhelyezkedését és annak állapotát.
 */
public class MushroomBody implements Updatable, Serializable {
	private final Tecton location;
	private final Mushroom mushroom;
	private int remainingSporulations;
	private boolean availableSpores;
	private int state;
	boolean dead;
	boolean testing;
	Geometry geometry;
	
	/**
     * Konstruktor, amely inicializálja a gombatest helyét, gombáját, állapotát, nevét, és tesztelési állapotát.
     *
     * @param location A gombatest tektonja, amin elhelyezkedik.
     * @param mushroom A gomba, amelyhez a gombatest tartozik.
     * @param state A gombatest állapota (0 - Kicsi, 1 - Közepes, 2 - Kifejlett).
     * @param testing Tesztelési jelző.
     */
	public MushroomBody(Tecton location, Mushroom mushroom, int state, boolean testing) { //ctor
		this.location = location;
		this.mushroom = mushroom;
		if(state == 2)
			availableSpores = true;
		else
			availableSpores = testing;
		this.testing = testing;
		this.state = state;
		remainingSporulations = 3;
		dead = false;
	}

	//Getterek
		public Tecton getLocation() {
			return location;
		}

		public int getRemainingSporulation() {
			return remainingSporulations;
		}

		public Mushroom getMushroom() {
			return mushroom;
		}

		public boolean getDead() {
			return dead;
		}
		
		public Geometry getGeometry() {
			return geometry;
		}
		
		public void setGeometry(Geometry geometry) {
			this.geometry = geometry;
		}
	
	/**
     * Az Updatable interfész felüldefiniált update függvénye. 
     * Elvégzi a gomba növekedését, és a spórák termelését.
     *
     * @param random A véletlenséget be/kikapcsoló változó.
     */
	public void update(boolean random){
		if(random){
			Random rng = new Random();
			int n = rng.nextInt(3);
			System.out.println("generált gombatest növekvős szám: " + n);
			if(n == 0)
				grow();
			n = rng.nextInt(4);
			System.out.println("generált spóratermelős szám: " + n);
			if(n == 0)
				produceSpores();
		}
		else
			grow();
	}
	
	/**
     * Spóra termelését végzi el, ha van elérhető spóra.
     * Csökkenti a hátralévő spóratermelések számát.
     */
	public void produceSpores() {
		availableSpores = true;
	}
	
	/**
     * Megvizsgálja, hogy a cél tekton elég közel van-e a gombatesthez.
     *
     * @param target A cél tekton.
     * @return true, ha a cél tekton elég közel van, false egyébként.
     */
	private boolean isTargetInRange(Tecton target) {
		 ArrayList<Tecton> neighbours;
		 neighbours = location.getNeighbours();
		 if (state == 1) return neighbours.contains(target);
		 if (state == 2) {
		    for (Tecton neighbour : neighbours) {
		    	if (neighbour == target || neighbour.getNeighbours().contains(target))
		            return true;
		    }
		 }
		 return false;
	}
	
	/**
     * Létrehoz egy véletlenszerű spórát a megadott cél tektonra.
     *
     * @param target A cél tekton.
     * @param newName Az új spóra neve.
     * @return A létrehozott spóra.
     */
	private Spore createRandomSpore(Tecton target, String newName) {
	    Random rand = new Random();
	    int type = rand.nextInt(5);
        return switch (type) {
case 0 -> new FastSpore(Spore.randomNutrientValue(), mushroom, target, newName);
case 1 -> new SlowSpore(Spore.randomNutrientValue(), mushroom, target, newName);
case 2 -> new GentleSpore(Spore.randomNutrientValue(), mushroom, target, newName);
case 3 -> new ParalyzerSpore(Spore.randomNutrientValue(), mushroom, target, newName);
case 4 -> new MultiplierSpore(Spore.randomNutrientValue(), mushroom, target, newName);
default -> null;
};
    }
	
	/**
     * Létrehoz egy konkrét típusú spórát a megadott cél tektonra, csak teszteléshez.
     *
     * @param target A cél tekton.
     * @param newName Az új spóra neve.
     * @param type A spóra típusa.
     * @return A létrehozott spóra.
     */
	private Spore createExactSpore(Tecton target, String newName, String type) {
		Spore spore;
        switch (type) {
            case "Fast" :
			case "fast" : {
				spore = new FastSpore(1, mushroom, target, newName);
				break;
			}
			case "Slow" : 
			case "slow" : { 
				spore = new SlowSpore(2, mushroom, target, newName);
				break;
			}
			case "Gentle" : 
			case "gentle" : { 
				spore = new GentleSpore(2, mushroom, target, newName);
				break;
			}
			case "Paralyzer" : 
			case "paralyzer" : { 
				spore = new ParalyzerSpore(2, mushroom, target, newName);
				break;
			}
			case "Multiplier" : 
			case "multiplier" : { 
				spore = new MultiplierSpore(2, mushroom, target, newName);
				break;
            }
			default : {
                System.out.println("A megadott típus érvénytelen, egy alap gyorsító spórát hozok létre");
                spore = new FastSpore(1, mushroom, target, newName);
				break;
            }
        }
		return spore;
	}
	
	/**
     * Spórát helyez el a megadott cél tektonon.
     * Megvizsgálja, hogy a cél tekton a megfelelő távolságra van-e, és hogy van-e elérhető spóra.
     *
     * @param target A cél tekton.
     * @param newName Az új spóra neve.
     * @param type A spóra típusa.
     */
	public Spore spreadSpores(Tecton target, String newName, String type) {
		Spore sp;
		if (!isTargetInRange(target)) return null;
		if(!availableSpores) return null;
		if(!testing) sp = createRandomSpore(target, newName);
		else sp = createExactSpore(target, newName, type);
		availableSpores = false;	
		remainingSporulations--;
		return sp;
	}
	
	/**
     * Növeszti a gombát, ha a gombatest állapota még nem kifejlett.
     */
	void grow() {
		if(state != 2) state++;
	}
	
	/**
     * Megvizsgálja, hogy van-e elég spóra az adott tektonon egy gombatest növesztéséhez.
     *
     * @param spores Az összes spóra listája.
     * @param tecton A tekton, amit vizsgálunk.
     * @return true, ha van elég spóra, false egyébként.
     */
	public boolean hasEnoughSpores(ArrayList<Spore> spores, Tecton tecton) {
		int counter = 0;
        for (Spore spore : spores)
            if (spore.getLocation() == tecton) counter++;
        return counter > 2;
	}
	
	/**
     * Megvizsgálja, hogy tektonon teljesül-e minden feltétel egy gombatest növesztéséhez.
     *
     * @param tecton A tekton, amit vizsgálunk.
     * @param spores Az összes spóra listája.
     * @param bodies Az összes gombatest.
     * @param strings Az összes gombafonal listája.
     * @return true, ha van elég spóra, false egyébként.
     */
	private boolean validTarget(Tecton tecton, ArrayList<Spore> spores, ArrayList<MushroomBody> bodies, ArrayList<MushroomString> strings) {
		CanGrowBodyVisitor v = new CanGrowBodyVisitor();
		TectonAccept acceptor = (TectonAccept) tecton;
		acceptor.accept(v);
		for (MushroomBody body : bodies)
			if (body.getLocation() == tecton)return false;
		boolean hasString = false;
		for (MushroomString string : strings) {
            if (string.getConnection().contains(tecton)) {
                hasString = true;
                break;
            }
		}
		hasString = true;
        return hasEnoughSpores(spores, tecton) && v.canPerformAction() && hasString;
    }
	
	/**
     * Egy új gombatest kinövését megvalósító függvény.
     *
     * @param spores Az összes spóra listája.
     * @param tecton A tekton, amelyen kinő.
     */
	public MushroomBody giveBirth(ArrayList<Spore> spores, Tecton tecton, ArrayList<MushroomBody> bodies, ArrayList<MushroomString> strings, Shroomer shroomer) {
		if(!validTarget(tecton, spores, bodies, strings))
			return null;
		
		tecton.markBodyGrown();
        for (Spore spore : spores)
            if (spore.getLocation() == tecton) spore.die();
        shroomer.score++;
        return new MushroomBody(tecton, this.mushroom, 0, false);
	}
	
	public String getState() 
	{
		if(state == 0)
			return "SMALL";
		
		if(state == 1)
			return "MEDIUM";
		
		return "BIG";
	}
	
	/**
     * A gombatest halálát végrehajtó metódus, amely meghívja az összes kapcsolódó fonal halálát is.
     *S
     * @param strings A gombafonalak listája.
     */
	public void die(ArrayList<MushroomString> strings) {
		for(int i = 0; i < strings.size(); i++)
			if(strings.get(i).getNeighbours().get(0) == null)
				strings.get(i).die(strings);
		dead = true;
	}
}
