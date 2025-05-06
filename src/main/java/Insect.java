package main.java;

import java.util.ArrayList;

/**
 * Az insect osztály a játékban megjelenő rovarokat reprezentálja.
 * A Nameable leszármazottja, így nevet tárol, és különféle képességekkel rendelkezik:
 * mozgás, fonalvágás, spórák elfogyasztása és állapotváltozás.
 */
public class Insect extends Nameable implements Updatable {

	private int collectedNutrients;
	private Tecton location;
	private boolean canCutString;
	private boolean canMove;
	/**
     * A rovar sebességét meghatározó enum.
     */
	public enum Speed { SLOW, NORMAL, FAST };
	private Speed speed;
	private final String name;
	private boolean dead;

	/**
     * Konstruktor – új rovar létrehozása.
     *
     * @param location A rovar kezdeti helye.
     * @param name     A rovar neve.
     */
	Insect(Tecton location, String name) {
		canCutString = true;
		canMove = true;
		speed = Speed.NORMAL;
		collectedNutrients = 0;
		this.location = location;
		this.name = name;
		dead = false;
	}

	/**
     * Az Updatable interfész felüldefiniált update függvénye. 
     * Visszaállítja a rovar állapotát alapértelmezettre.
     *
     * @param testing A tesztelő állapot eldöntését meghatározó boolean.
     */
	public void update(boolean testing){
		speed = Speed.NORMAL;
		canMove = true;
		canCutString = true;
	}

	//Getterek, Setterek
	public String getName()
	{
		return name;
	}
	public boolean getCanCutString() 
	{
		return canCutString;
	}
	public boolean getCanMove()
	{
		return canMove;
	}
	public boolean getDead()
	{
		return dead;
	}
	public Tecton getLocation()
	{
		return location;
	}
	public int getNutrients(){
		return collectedNutrients;
	}
	public String getEffect(){
		if(!canCutString) return "GENTLE";
		if(!canMove) return "PARALYZED";
		if(speed == Speed.FAST) return "FAST";
		else if(speed == Speed.NORMAL && canCutString && canMove) return "NO EFFECT";
		else return "SLOW";
	}
	
	public void setCollectedNutrients(int n)
	{
		collectedNutrients += n;
	}
	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}
	public void setCanCutString(boolean canCutString) {
		this.canCutString = canCutString;
	}
	public void setSpeed(Speed speed)
	{
		this.speed = speed;
	}

	/**
     * A rovar mozgatása egy új tectonra.
     * A mozgás sebességtől függően extra vagy csökkentett akcióval jár.
     *
     * @param destination Az új tecton, ahova mozogni szeretne.
     * @return +1, ha FAST; -1, ha SLOW; 0 egyébként vagy ha nem tud mozogni.
     */
	public int move(Tecton destination) {
		if(canMove) {
			location = destination;
			if(speed == Insect.Speed.FAST) {
                return 1;
			}
			else if(speed == Insect.Speed.SLOW) {
				return -1;
			}
			else {
				return 0;
			}
		}
		return 0;
	}

	 /**
     * A megadott gombafonal elvágása.
     *
     * @param ms      Az elvágandó gombafonal.
     * @param strings Az összes fonalat tartalmazó lista.
     */
	public void cutHypha(MushroomString ms, ArrayList<MushroomString> strings)
	{
	    // 1) ellenőrizzük, hogy azon a Tectonon állunk-e, ahol a fonal egyik vége van
	    if (!ms.getConnection().contains(location)) return;

	    /* 2) keressük meg a szomszéd fonalat (ha volt) */
	    for (MushroomString nb : ms.getNeighbours()) {
	        if (nb == null) continue;

	        // a neighbour listájából is eltávolítjuk ms-t
	        if (nb.getNeighbours().get(0) == ms) nb.getNeighbours().set(0, null);
	        if (nb.getNeighbours().size() > 1 && nb.getNeighbours().get(1) == ms)
	            nb.getNeighbours().set(1, null);

	        // ms neighbour-listájából töröljük nb-t
	        if (ms.getNeighbours().get(0) == nb) ms.getNeighbours().set(0, null);
	        if (ms.getNeighbours().size() > 1 && ms.getNeighbours().get(1) == nb)
	            ms.getNeighbours().set(1, null);
	    }

	    /* 3) az elvágott fonalról levágjuk a másik Tectont,
	          így „félfonal” marad (csak location marad benne) */
	    if (ms.getConnection().size() == 2) {

	        // 2.a  Meghatározzuk, melyik végpont marad a rovar alatt,
	        //       és melyik „esik le”
	        Tecton dropped;
	        if (ms.getConnection().get(0) == location) {
	            dropped = ms.getConnection().remove(1);
	        } else {
	            dropped = ms.getConnection().remove(0);
	        }

	        // 2.b  Új fél-fonal létrehozása a leeső Tectonon
	        ArrayList<Tecton> newConn = new ArrayList<>();
	        newConn.add(dropped);

	        MushroomString clone = new MushroomString(
	            MushroomString.nextCloneName(ms.getName()),
	            ms.getMushroom(),
	            newConn,
	            new ArrayList<>(java.util.Arrays.asList(null, null)),
	            ms.getLifeLine()
	        );

	        // 2.c  Szomszédlista frissítése
	        ms.getNeighbours().removeIf(clone::equals);           // biztosan kiveszi a klónt
	        while (ms.getNeighbours().size() < 2) ms.getNeighbours().add(null);  // feltölt null-lal

	        // 2.d  Belerakjuk a bolygó globális listájába, hogy a LIST parancs is lássa
	        strings.add(clone);
	    }

	    // 4) NEM ölünk meg semmit azonnal – a MushroomString.update végzi az öregedést
	}

	 /**
     * A rovar elfogyaszt egy spórát, megnövelve tápanyagkészletét,
     * majd alkalmazza a spóra hatását.
     *
     * @param spore A fogyasztandó spóra.
     */
	public void eatSpore(Spore spore)
	{
		//collectedNutrients += spore.getNutrientValue();
		spore.applyEffect(this);
	}
	
	/**
     * A rovar elpusztul – halott állapotba kerül.
     */
	public void die()
	{
		dead = true;
	}

}
