package main.java.mushroom;

import java.util.ArrayList;
import main.java.Nameable;
import main.java.Updatable;

import main.java.tecton.*;

/**
 * A gombafonalat megvalósító osztály, melynek tulajdonsága az általa összekötött két tekton, a hozzá kapcsolódó
 * fonalak, a gombája, érettségi szintje, és az állapota, hogy életben van-e, vagy már elpusztult
 */
public class MushroomString extends Nameable implements Updatable {
	
	private static class CloneCounter {
	    private static int counter = 1;
	    static synchronized int next() { return counter++; }
	}
	
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

	/**
	 * A MushroomString osztály konstruktora, amely inicializálja a fonal nevét, a hozzá tartozó gombát, 
	 * a kapcsolódó tektonokat és a szomszédos fonalakat.
	 * 
	 * @param name A fonal neve.
	 * @param mushroom A gomba, amelyhez a fonal tartozik.
	 * @param connection A tektonok listája, amelyekhez a fonal csatlakozik.
	 * @param neighbours A szomszédos fonalak listája, amelyek a fonal szomszédai.
	 * @param lifeLine A születésének aktuális körszáma
	 */
	public MushroomString(String name, Mushroom mushroom, ArrayList<Tecton> connection, ArrayList<MushroomString> neighbours, int lifeLine) {
		this.mushroom = mushroom;
		this.connection = connection;
		this.neighbours  = (neighbours == null)
                ? new ArrayList<>(java.util.Arrays.asList(null, null))
                : neighbours;   
		dead = false;
		setName(name);
		this.lifeLine = lifeLine;
	}
	
	public static String nextCloneName(String base) {
	    return base + "_clone" + CloneCounter.next();
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

	public int getLifeLine()
	{
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
	public void incrementAge()
	{
		lifeLine++;
		age++;
	}
	
	/**
	 * A fonal terjedését megvalósító függvény.
	 * Elvégzi a szükséges feltételek ellenőrzését, majd hozzáadja az étvett tektont a connections-höz,
	 * illetve megkeresi, hogy van-e egy másik fonal, amihez ez az új csatlakozni tud
	**/
	
	public boolean branch(Tecton target, ArrayList<MushroomString> allStrings) {

	    // Nem tudunk nőni, ha:
	    if (dead || !connection.get(connection.size()-1).getNeighbours().contains(target))
	        return false;

	    /* ------------------------------------------------------------------
	     * A) Ha ez még csak „félig” fonal (csak egy Tectont ismer), akkor
	     *    egyszerűen ránőhetünk a második, szomszédos Tectonra.
	     * ------------------------------------------------------------------ */
	    if (connection.size() == 1) {
	        connection.add(target);
	        return true;
	    }

	    /* ------------------------------------------------------------------
	     * B) Már összeköt két Tectont → új fonalat kell létrehozni,
	     *    amely a második Tectonról (connection.get(1)) indul tovább.
	     * ------------------------------------------------------------------ */
	    Tecton start = connection.get(1);          // a „híd” másik vége
	    ArrayList<Tecton> newConn = new ArrayList<>();
	    newConn.add(start);
	    newConn.add(target);

	    ArrayList<MushroomString> newNb = new ArrayList<>();
	    newNb.add(this);         // az 1. szomszéd az „anyafonal”
	    newNb.add(null);

	    MushroomString child = new MushroomString(
	    		getName() + "_clone" + CloneCounter.next(), // valami egyedi név
	            mushroom,
	            newConn,
	            newNb,
	            lifeLine                                   // a körszámot örökli
	    );

	    /* A régi fonal egyik szomszédjaként bejegyezzük az újat            */
	    if (neighbours.get(0) == null) neighbours.set(0, child);
	    else                           neighbours.set(1, child);

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

	    /* 1) öregszünk */
		if(!dead)
		{
			lifeLine++;
		    age++;
		}
		
	    /* 2) végig­nézzük, van-e toxikus Tecton */
	    boolean toxicReachedLimit = false;

	    CanKillStringVisitor v = new CanKillStringVisitor();
	    for (Tecton t : new ArrayList<>(connection)) {       // biztonság kedvéért másolaton iterálunk
	        ((TectonAccept) t).accept(v);

	        /* csak ToxicTectonra ad true-t a visitor */
	        if (v.canPerformAction() && age >= TOXIC_AGE_LIMIT) {
	            toxicReachedLimit = true;
	            connection.remove(t);        // ez a Tecton „leesik” a fonalról
	        }
	    }

	    /* 3) ha minden ág leesett, tényleg meghalunk */
	    if (toxicReachedLimit && connection.isEmpty()) {
	        dead = true;
	        return;
	    }
	    
	    /*  if a fonal már nem kapcsolódik a gomba­testhez ÉS nem Healing-en áll,
	    akkor öregszik s végül elpusztul  */
	    /*  if a fonal már nem kapcsolódik a gombatesthez ÉS nem Healing-en áll,
	    akkor öregszik s végül elpusztul  */
		if (!connectedToBody) {
		    v = new CanKillStringVisitor();                       // csak példányosítás
		    ((TectonAccept) connection.get(0)).accept(v);     // de NEM új deklaráció
	
		    if (v.canPerformAction() && age >= TOXIC_AGE_LIMIT) {
		        dead = true;
		        return;
		    }
		}

	    /* 4) ha már nincs toxikus águnk, indulunk elölről a számlálással,
	          különben a maradék ág is feleslegesen tovább öregedne */
	    if (toxicReachedLimit) {
	        age = 0;
	    }
	    
	    /* 4/b) – árva-e?  (nincs gyökér-kapcsolat) */
	    if (!connectedToBody) {

	        orphanAge++;                 // <<<  minden körben NÖVELJÜK

	        /* Healing-vég keresése visitorral */
	        boolean hasHealingEnd = false;
	        for (Tecton t : connection) {
	            KeepStringAliveVisitor healV = new KeepStringAliveVisitor();
	            ((TectonAccept) t).accept(healV);
	            if (healV.canPerformAction()) {      // Healing -> true
	                hasHealingEnd = true;
	                break;
	            }
	        }

	        /* 3 kör után minden NEM-Healing ág leesik – akkor is, ha van Healing-vég */
	        if (orphanAge >= ORPHAN_AGE_LIMIT) {

	            connection.removeIf(t -> {
	                KeepStringAliveVisitor healV = new KeepStringAliveVisitor();
	                ((TectonAccept) t).accept(healV);
	                return !healV.canPerformAction();      // true -> nem Healing, leesik
	            });

	            if (connection.isEmpty()) {                // csak akkor hal meg, ha SEMMI nem maradt
	                dead = true;
	                return;
	            }
	            orphanAge = 0;                             // számláló újraindul
	        }

	    } else {
	        orphanAge = 0;                                 // ismét van gyökér
	    }

	    /* 5) normál növekedés (változatlanul) */
	    if (random) {
	        if (new java.util.Random().nextInt(3) == 0) {
	            lifeCycle = LifeCycle.Grown;
	        }
	    } else {
	        lifeCycle = LifeCycle.Grown;
	    }
	}
	
	/**
	 * A fonal halálát megvalósító függvény.
	 * Visitor modell segítségével határozza meg, hogy milyen tektonon van a fonal. Ha healing típusún, akkor nem hal meg
	 * 
	 * @param strings A fonalakat tartalmazó lista, amely segít meghatározni a fonal halálát.
	 */
	public void die(ArrayList<MushroomString> strings) {
		KeepStringAliveVisitor v = new KeepStringAliveVisitor();
        for (MushroomString string : strings) {
            if (string.getMushroom() == mushroom && string.getNeighbours().get(0) == this) {
                TectonAccept acceptor = (TectonAccept) string.getConnection().get(0);
                acceptor.accept(v);
                if (!v.canPerformAction()) {
                    dead = true;
                }
            }
        }
	}
}
