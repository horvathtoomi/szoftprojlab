package main.java.mushroom;

import java.io.Serializable;
import java.util.ArrayList;
import main.java.Updatable;

import main.java.tecton.*;

/**
 * A gombafonalat megvalósító osztály, melynek tulajdonsága az általa összekötött két tekton, a hozzá kapcsolódó
 * fonalak, a gombája, érettségi szintje, és az állapota, hogy életben van-e, vagy már elpusztult
 */
public class MushroomString implements Updatable, Serializable {
	private final ArrayList<Tecton> connection;
	private final ArrayList<MushroomString> neighbours;
	private final Mushroom mushroom;
	private static final int TOXIC_AGE_LIMIT = 3; // 3 kör után elpusztul a toxic tektonon

	public enum LifeCycle {Child, Grown}
	private LifeCycle lifeCycle = LifeCycle.Child;
	
	private boolean connectedToBody = false;
	boolean dead;
	int lifeLine;
	int age = 0;
	
	private int orphanAge = 0;                // hány kör óta árva?
	private static final int ORPHAN_AGE_LIMIT = 3;

	GeometryString geometry;

	/**
	 * A MushroomString osztály konstruktora, amely inicializálja a fonal nevét, a hozzá tartozó gombát, 
	 * a kapcsolódó tektonokat és a szomszédos fonalakat.
	 *
	 * @param mushroom A gomba, amelyhez a fonal tartozik.
	 * @param connection A tektonok listája, amelyekhez a fonal csatlakozik.
	 * @param neighbours A szomszédos fonalak listája, amelyek a fonal szomszédai.
	 * @param lifeLine A születésének aktuális körszáma
	 */
	public MushroomString(Mushroom mushroom, ArrayList<Tecton> connection, ArrayList<MushroomString> neighbours, int lifeLine, GeometryString geometry) {
		this.mushroom = mushroom;
		this.connection = connection;
		this.neighbours  = (neighbours == null)
                ? new ArrayList<>(java.util.Arrays.asList(null, null))
                : neighbours;   
		dead = false;
		this.lifeLine = lifeLine;
		this.geometry = geometry;
	}
	
	//Getterek, Setterek
	public Mushroom getMushroom() {
		return mushroom;
	}

	public ArrayList<MushroomString> getNeighbours() {
		return neighbours;
	}

	public ArrayList<Tecton> getConnection() {
		return connection;
	}

	public boolean getDead() {
		return dead;
	}

	public int getLifeLine() {
		return lifeLine;
	}
	
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}
	
	public boolean isConnectedToBody() {
	    return connectedToBody;
	}

	public void setConnectedToBody(boolean b) {
	    connectedToBody = b;
	}

	
	/**
	 * Megnöveli a fonal aktuális "életszámlálóját" 1-gyel
	 */
	public void incrementAge() {
		lifeLine++;
		age++;
	}
	
	/**
	 * A fonal terjedését megvalósító függvény.
	 * Elvégzi a szükséges feltételek ellenőrzését, majd hozzáadja az étvett tektont a connections-höz,
	 * illetve megkeresi, hogy van-e egy másik fonal, amihez ez az új csatlakozni tud
	**/
	
	public boolean branch(Tecton target, ArrayList<MushroomString> allStrings) {

		//Ha halott, vagy nem szomszédos a céllal, akkor nem tud nőni
		if (dead || !connection.get(0).getNeighbours().contains(target)) {
			return false;
		}
		//Ha még nem nőtt át sehova, akkor átnő
		if (connection.get(1) == null) {
			connection.set(1, target);
			geometry.setX2(target.getGeometry().getX());
			geometry.setY2(target.getGeometry().getY());
			return true;
		}

		//Különben egy új fonal jön létre. A kezdete az előző vége, a vége pedig a cél tekton
	    Tecton start = connection.get(1);
	    ArrayList<Tecton> newConn = new ArrayList<>();
	    newConn.add(start);
	    newConn.add(target);

		//Szomszédja az eredeti, és null egyelőre
	    ArrayList<MushroomString> newNb = new ArrayList<>();
	    newNb.add(this);
	    newNb.add(null);

	    MushroomString child = new MushroomString(
	            mushroom,
	            newConn,
	            newNb,
	            lifeLine,
				new GeometryString(this.getGeometry().getX2(), this.getGeometry().getY2(), target.getGeometry().getX(),target.getGeometry().getY()) // új geometria
	    );

	    // A régi fonal végén az új fonal lesz
		neighbours.set(1, child);
	    allStrings.add(child);
	    return true;
	}
	
	/**
     * Az Updatable interfész felüldefiniált update függvénye. 
     * Elvégzi a fonal fejlődését, és ha még nem halott, akkor megnöveli a lifeLine-t.
     *
     * @param random A véletlenséget be/kikapcsoló változó.
     */
	@Override
	public void update(boolean random) {

		//Csak ha nem halott, akkor öregszik
		if (!dead) incrementAge();

		//Ha nem köt össze semmit, akkor meghal
		if(connection.get(0) == null && connection.get(1) == null) die();

		//Ha valamelyik vége toxic-on van, és legalább 3 kör óta él, akkor meghal
		CanKillStringVisitor v = new CanKillStringVisitor();
        for (Tecton t : connection) {
            if (t != null) {
                ((TectonAccept) t).accept(v);
                if (v.canPerformAction() && age >= TOXIC_AGE_LIMIT) {
                    die();
                }
            }
        }

		//Ha nem kapcsolódik testhez, meg van nőve, és eltelt 3 kör, akkor meghal. Ha kicsi, egyből meghal
		if (neighbours.get(0) == null && neighbours.get(1) != null) {
			if(lifeCycle == LifeCycle.Child) die();
			else{
				orphanAge++;
				if (orphanAge >= ORPHAN_AGE_LIMIT) die();
			}
		}

		//1/3-ad eséllyel megnő
		if(lifeCycle == LifeCycle.Child) {
			if (random) {
				if (new java.util.Random().nextInt(3) == 0) {
					lifeCycle = LifeCycle.Grown;
				}
			} else {
				lifeCycle = LifeCycle.Grown;
			}
		}
	}
	
	/**
	 * A fonal halálát megvalósító függvény.
	 * Visitor modell segítségével határozza meg, hogy milyen tektonon van a fonal. Ha healing típusún, akkor nem hal meg
	 */
	public void die() {
		dead = true;
		if(neighbours.get(1) != null){
			MushroomString ms = neighbours.get(1);
			KeepStringAliveVisitor v = new KeepStringAliveVisitor();
			TectonAccept acceptor = (TectonAccept) ms.getConnection().get(0);
			acceptor.accept(v);
			if (!v.canPerformAction()) {
				neighbours.set(1, null);
				ms.neighbours.set(0, null);
			}
		}
	}

	public GeometryString getGeometry() {
		return geometry;
	}
}
