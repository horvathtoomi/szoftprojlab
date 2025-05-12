package main.java;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import main.java.insect.*;
import main.java.player.Player;
import main.java.player.PlayerAccept;
import main.java.tecton.*;
import main.java.mushroom.*;
import main.java.spore.*;

/**
 * A Planet osztály felelős a játék világának kezeléséért, amely magába foglalja a különböző objektumokat
 * (például tektonok, gombák, spórák, rovarok).
 */
public class Planet implements Updatable, Serializable {
    private static final long serialVersionUID = 1L;

    private final ArrayList<Tecton> tectons;
    private final ArrayList<Mushroom> mushrooms;
    private final ArrayList<MushroomBody> mushbodies;
    private final ArrayList<MushroomString> mushstrings;
    private final ArrayList<Insect> insects;
    private final ArrayList<Spore> spores;

    
    /**
     * A Planet osztály konstruktora, amely inicializálja az objektumo listáit.
     */
    public Planet() {
        tectons = new ArrayList<>();
        mushrooms = new ArrayList<>();
        mushbodies = new ArrayList<>();
        mushstrings = new ArrayList<>();
        insects = new ArrayList<>();
        spores = new ArrayList<>();
    }

    /**
     * Hozzáad egy tekton objektumot a listához.
     *
     * @param tecton a hozzáadandó Tecton
     */
    public void addTecton(Tecton tecton) {
        tectons.add(tecton);
    }
    
    //Getterek, Setterek
    public ArrayList<Tecton> getTectons() {
        return tectons;
    }

    public ArrayList<Mushroom> getMushrooms() {
    	return mushrooms;
    }

    public ArrayList<MushroomBody> getMushbodies() {
    	return mushbodies;
    }

    public ArrayList<MushroomString> getMushstrings() {
    	return mushstrings;
    }

    public ArrayList<Insect> getInsects() {
    	return insects;
    }

    public ArrayList<Spore> getSpores() {
    	return spores;
    }
    
    /**
     * Megnézi, hogy van-e spóra egy adott mushroomString tektonján.
     *
     * @param ms A vizsgált MString
     */
    private boolean checkForSpores(MushroomString ms) {
	    if (ms.getConnection().isEmpty()) return false;
	    Tecton location = ms.getConnection().get(0);
	    return spores.stream().anyMatch(spore -> spore.getLocation() == location);
	}
    
    public void checkForBodyConnection() {
    	for (MushroomString ms : mushstrings) {
    	    ms.setConnectedToBody(false);
    	}

    	for (MushroomBody mb : mushbodies) {
    	    if (mb.getDead()) continue;

    	    ArrayList<MushroomString> q = new ArrayList<>();
    	    for (MushroomString s : mushstrings) {
    	        if (!s.getDead() && s.getConnection().contains(mb.getLocation())) {
    	            q.add(s);
    	        }
    	    }

    	 while (!q.isEmpty()) {
    	     MushroomString cur = q.remove(0);
    	     if (cur.isConnectedToBody()) continue;
    	     cur.setConnectedToBody(true);
    	     for (MushroomString n : cur.getNeighbours()) {
    	         if (n != null && !n.getDead()) q.add(n);
    	     }
    	 }
    	}
    }
    
    /**
     * Ha egy Tectonon egyszerre van
     *   – legalább egy élő, bénult rovar (canMove == false) ÉS
     *   – legalább egy élő MushroomString, amelynek egyik vége ezen a Tectonon van,
     * akkor a helyen nőjön egy új MushroomBody ugyanahhoz a gombához, mint a fonal.
     */
    public void growBodyOnParalyzedInsect() {

        // végigmegyünk az összes Tectonon
        for (Tecton t : tectons) {

            // 1) van-e bénult (canMove == false) élő rovar?
            boolean hasParalyzed = insects.stream()
                    .anyMatch(in -> !in.getDead() && in.getLocation() == t && !in.getCanMove());
            if (!hasParalyzed) continue;

            // 2) élő fonal a Tectonon?
            for (MushroomString ms : mushstrings) {
                if (ms.getDead()) continue;
                if (!ms.getConnection().contains(t)) continue;

                // 3) nincs-e már ott gombatest?
                boolean bodyAlreadyHere = mushbodies.stream()
                        .anyMatch(mb -> !mb.getDead() && mb.getLocation() == t);
                if (bodyAlreadyHere) break;

                // 4) létrehozzuk az új testet
                MushroomBody nb = new MushroomBody(
                        t,                                     // hely
                        ms.getMushroom(),                      // ugyanahhoz a gombához tartozzon
                        0,                                     // kezdeti state = kicsi
                        MushroomBody.nextBodyName("mb"),
                        false                                  // testing flag
                );

                mushbodies.add(nb);

                // 5) a fonalat összekötjük a testtel, hogy élő kapcsolat legyen
                ms.setConnectedToBody(true);
               
                // 6) a bénult rovar(ok) elpusztítása ugyanazon a Tectonon
                for (Insect in : insects) {
                    if (!in.getDead() && in.getLocation() == t && !in.getCanMove()) {
                        in.die();
                    }
                }
                
                // csak egy testet csíráztatunk fonalonként
                break;
            }
        }
    }
    
    /**
     * Az Updatable interfész felüldefiniált update függvénye. 
     * A plnaet által tárolt objektumok mindegyikén meghívja az update()-t
     *
     * @param random A tesztelő állapot eldöntését meghatározó boolean. Be/kikapcsolja a randomitást.
     */
    @Override
    public void update(boolean random) {
    	growBodyOnParalyzedInsect();
        tectons.forEach(t -> t.update(random));
        insects.forEach(i -> i.update(random));
        mushbodies.forEach(mb -> mb.update(random));

        for (MushroomString ms : mushstrings) {
            boolean shouldUpdateRandomly = (ms.getLifeCycle() != MushroomString.LifeCycle.Child) || !checkForSpores(ms);
            ms.update(shouldUpdateRandomly);
        }
    }

    /**
     * Törli azokat az objektumokat a világban, amelyek meghaltak. Fonalak esetén vár egy, vagy két kört annak függvényében, hogy mennyire kifejlett a fonal.
     * @param currentTurn Az aktuális kör száma
     */
    public void deleteDeadObjects(int currentTurn, ArrayList<Player> players) {
    	mushrooms.removeIf(Mushroom::getDead);
    	mushbodies.removeIf(MushroomBody::getDead);
    	mushstrings.removeIf(ms ->
        ms.getDead() &&
        ((ms.getLifeCycle() == MushroomString.LifeCycle.Child && currentTurn - ms.getLifeLine() >= 1) ||
         (ms.getLifeCycle() == MushroomString.LifeCycle.Grown && currentTurn - ms.getLifeLine() >= 2)));
    	insects.removeIf(Insect::getDead);
    	spores.removeIf(Spore::getDead);
    	RemoveInsectVisitor v = new RemoveInsectVisitor();
    	for(Player player : players) {
    		((PlayerAccept) player).accept(v);
    	}
    }
    
    /**
     * Újraszámolja az összes tekton szomszédait a Planet aktuális tekton listája alapján.
     */
    public void recalcNeighbours() {
        for (Tecton t : tectons) {
            ArrayList<Tecton> newNeighbours = t.determineNeighbours(tectons);
            t.setNeighbours(newNeighbours);
        }
    }

    /**
     * Feldarabol egy meglévő tekton objektumot két újra, és frissíti a szomszédsági viszonyokat.
     * Ha van rajta bármilyen objektum, az meghal.
     *
     * @param tecton     az eredeti tekton
     * @param newName1   az első új tekton neve
     * @param newName2   a második új tekton neve
     */
    public void splitTecton(Tecton tecton, String newName1, String newName2) {
    	if (!tectons.contains(tecton)) return;
        removeThingsSplit(tecton);
        Tecton[] newTectons = tecton.createSplitTectons(newName1, newName2);
        tectons.remove(tecton);
        Collections.addAll(tectons, newTectons);
        recalcNeighbours();
    }
    
    /**
     * Megkeresi az összes lehetséges objektumot amit tartalmaz a megadott tekton, 
     * és dead-re állítja őket.
     *
     * @param tecton A vizsgált tekton
     */
    public void removeThingsSplit(Tecton tecton) {
        containedInsects(tecton).forEach(Insect::die);
        containedStrings(tecton).forEach(ms -> ms.die(mushstrings));
        containedSpores(tecton).forEach(Spore::die);
        containedBodies(tecton).forEach(mb -> mb.die(mushstrings));
    }
    
    /**
     * Megkeresi, hogy van-e rovar a megadott tektonon.
     *
     * @param tecton a vizsgált tekton
     * @return a rovarok listája, amik rajta vannak
     */
    public ArrayList<Insect> containedInsects(Tecton tecton) {
    	ArrayList <Insect> in = new ArrayList<>();
        for (Insect insect : insects)
            if (insect.getLocation() == tecton)in.add(insect);
    	return in;
    }
    /**
     * Megkeresi, hogy van-e fonal a megadott tektonon.
     *
     * @param tecton a vizsgált tekton
     * @return a fonalok listája, amik rajta vannak
     */
    public ArrayList<MushroomString> containedStrings(Tecton tecton){
    	ArrayList<MushroomString> st = new ArrayList<>();
    	for(MushroomString s : mushstrings)
    		if(s.getConnection().contains(tecton))
    			st.add(s);
    	return st;
    }
    /**
     * Visszaadja a megadott tektonon található spórák listáját.
     *
     * @param tecton a vizsgált tekton
     * @return a spórák listája, amik rajta vannak
     */
    public ArrayList<Spore> containedSpores(Tecton tecton) {
    	ArrayList <Spore> sp = new ArrayList<>();
        for (Spore spore : spores)
            if (spore.getLocation() == tecton)sp.add(spore);
    	return sp;
    }
    /**
     * Visszaadja a megadott tektonon található gombatestek listáját.
     *
     * @param tecton a vizsgált tekton
     * @return a gombatestek listája, amik rajta vannak
     */
    public ArrayList<MushroomBody> containedBodies(Tecton tecton) {
    	ArrayList <MushroomBody> mb = new ArrayList<>();
        for (MushroomBody body : mushbodies) 
        	if (body.getLocation() == tecton) mb.add(body);
    	return mb;
    }
    
    
    /**
     * Megkeresi a dead állapotó gombatesteket, és dead-re állítja a hozzá kapcsolódó MString-eket is.
     *
     */
    public void checkForDeadShrooms(){
    	for(MushroomBody b : mushbodies)
        	if(b.getRemainingSporulation() == 0) b.die(mushstrings);
    }
}
