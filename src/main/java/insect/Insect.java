package main.java.insect;

import java.io.Serial;
import java.io.Serializable;

import main.java.tecton.*;
import main.java.mushroom.*;
import main.java.Geometry;
import main.java.Updatable;

/**
 * Az insect osztály a játékban megjelenő rovarokat reprezentálja.
 * A Nameable leszármazottja, így nevet tárol, és különféle képességekkel rendelkezik:
 * mozgás, fonalvágás, spórák elfogyasztása és állapotváltozás.
 */
public class Insect implements Updatable, Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private int collectedNutrients;
	private Tecton location;
	private boolean canCutString;
	private boolean canMove;
	/**
     * A rovar sebességét meghatározó enum.
     */
	public enum Speed { SLOW, NORMAL, FAST }
	private Speed speed;
	private boolean dead;
	private Geometry geometry;

	/**
     * Konstruktor – új rovar létrehozása.
     *
     * @param location A rovar kezdeti helye.
     */
	public Insect(Tecton location) {
		canCutString = true;
		canMove = true;
		speed = Speed.NORMAL;
		collectedNutrients = 0;
		this.location = location;
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
	public boolean getCanMove()
	{
		return !canMove;
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
	
	public Geometry getGeometry() {
		return geometry;
	}
	
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
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
		if(canMove && location != destination){
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
		return -3;
	}

	 /**
     * A megadott gombafonal elvágása.
     *
     * @param ms Az elvágandó gombafonal.
     */
	public boolean cutHypha(MushroomString ms)
	{
		if(!canCutString){return false;}
	    // 1) ellenőrizzük, hogy azon a Tectonon állunk-e, ahol a fonal egyik vége van
	    if (!ms.getConnection().contains(location)) return false;

	    // 2) keressük meg a szomszéd fonalakat
	    for (MushroomString nb : ms.getNeighbours()) {
	        if (nb == null) continue;

	        // a neighbour listájából is eltávolítjuk ms-t
	        if (nb.getNeighbours().get(0) == ms) nb.getNeighbours().set(0, null);
	        if (nb.getNeighbours().get(1) == ms) nb.getNeighbours().set(1, null);

	        // ms neighbour-listájából töröljük nb-t
	        if (ms.getNeighbours().get(0) == nb) ms.getNeighbours().set(0, null);
	        if (ms.getNeighbours().get(1) == nb) ms.getNeighbours().set(1, null);
	    }
		ms.die();
		return true;
	}
	
	/**
     * A rovar elpusztul – halott állapotba kerül.
     */
	public void die()
	{
		dead = true;
	}

}
