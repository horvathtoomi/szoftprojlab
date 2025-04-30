package main.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * A parancssori futtatáshoz, parancsok értelmezéséhez létrehozott osztály. Felelőssége az inputot és az outputot kezelni.
 */
public class Terminal {

    public GameController gc;
    
    /**
     * Konstruktor, létrehoz egy új pédányt, és inicializálja a megadott paraméterekkel a GameControllerét.
     * @param testing T/F, annak függvényében, hogy éles játékra lett létrehozva, vagy tesztelési célból. Utóbbi esetében a random funkciók (többek között) nem működnek.
     * @param maxTurn A körök száma, mielőtt a játék befejeződik.
     */
    public Terminal(boolean testing, int maxTurn) {
    	gc = new GameController(testing, maxTurn);
    }

    /**
     * Az összes lehetséges parancs kilistázása.
     */
    private static void printHelp() {
        System.out.println("Command -> List");
        System.out.println("Description: It lists all the objects on the planet, providing a picture of the planet's current state. \n");
    
        System.out.println("Command -> Help or ?");
        System.out.println("Description: Lists all the available commands, and the usage\n");

        System.out.println("Command -> Tecton <tecton_name> <tecton_type>");
        System.out.println("Description: Creates a tecton on the planet with the given name, and type,\nwhere the type can be {big, small, toxic, coarse}\n");

        System.out.println("Command -> SetNeighbours <tecton1_name> <tecton2_name>");
        System.out.println("Description: Sets the tectons, as neighbours\n");
        
        System.out.println("Command -> Shroomer <shroomer_name>");
        System.out.println("Description: Creates a new shroomer player\n");
        
        System.out.println("Command -> Insecter <insecter_name>");
        System.out.println("Description: Creates a new insecter player\n");

        System.out.println("Command -> Insect <insect_name> <tecton_name> <insecter_name>");
        System.out.println("Description: Creates an insect with the given name on the given tecton for the given player\n");

        System.out.println("Command -> Mushroom <mushroom_name> <shroomer_name>");
        System.out.println("Description: Creates a mushroom with the given name for the given player\n");

        System.out.println("Command -> MushroomBody <body_name> <owner_mushroom_name> <location> <state> <shroomer_name>");
        System.out.println("Description: Creates a MushroomBody with the given name, adding it to the given mushroom, on the given tecton, with the given state,\n where the state is {big, small}, and counts it as an action of shroomer_name\n");
    
        System.out.println("Command -> SpreadSpore <spore_name> <mushroombody_name> <dest_tecton> <spore_type> <shroomer_name>");
        System.out.println("Description: Spreads a new spore with the given name by the given body on the given tecton with the given type,\nwhere type {fast, slow, gentle, paralyzer}, and counts it as an action of shroomer_name\n");

        System.out.println("Command -> MushroomString <hypha_name> <owner_mushroom_name> <tecton_name> <shroomer_name>");
        System.out.println("Description: Creates a new hypha on the given tecton, and counts it as an action of shroomer_name \n");

        System.out.println("Command -> Branch <hypha_name> <destination_tecton_name> <shroomer_name>");
        System.out.println("Description: Branches the hypha to the destination tecton, and counts it as an action of shroomer_name\n");

        System.out.println("Command -> Move <insect_name> <destination_tecton_name> <insecter_name>");
        System.out.println("Description: Moves the given insect to the given tecton, and counts it as an action of insecter_name\n");

        System.out.println("Command -> Consume <insect_name> <spore_name> <insecter_name>");
        System.out.println("Description: The given insect consumes the given spore, and counts it as an action of insecter_name\n");

        System.out.println("Command -> Cut <insect_name> <hypha_name> <insecter_name>");
        System.out.println("Description: The given insect cuts the given hypha, and counts it as an action of insecter_name\n");

        System.out.println("Command -> Split <splittable_tecton_name> <new_tecton_1_name> <new_tecton_2_name>");
        System.out.println("Description: Splits the given tecton into 2 new tectons\n");
        
        System.out.println("Command -> Pass <player_name>");
        System.out.println("Description: Skips the turn of given player\n");

        System.out.println("Command -> Save <filename/path>");
        System.out.println("Description: Saves the state of the planet into a file with the given name,\nabsolute path is also usable\n");

        System.out.println("Command -> Load <filename/path>");
        System.out.println("Description: Loads the state of the planet from a file with the given name,\nabsolute path is also usable");
    }

    /**
     * Tekton létrehozása
     * 
     * @param name Az létrehozni kívánt tekton neve
     * @param type A tekton típusa
     */
    private void createTecton(String name, String type) {
        if (gc.getPlanet().hasTecton(name)) {
            System.out.println("[TECTON] ERROR! -> tekton '" + name + "' already exists");
            return;
        }
        Tecton t;
        switch (type.toLowerCase()) {
            case "big":    t = new BigTecton(name, 5);    break;
            case "small":  t = new SmallTecton(name, 2);  break;
            case "coarse": t = new CoarseTecton(name, 3); break;
            case "toxic":  t = new ToxicTecton(name, 3);  break;
            case "healing":t = new HealingTecton(name, 3);break;
            default:
                System.out.println("[TECTON] ERROR! -> unknown type: " + type);
                return;
        }
        gc.getPlanet().addTecton(t);
        System.out.println("[TECTON] " + name + " type: " + type.toUpperCase() + " created");
    }

    /**
     * Szomszédosság beállítása. Kölcsönösen elvégzi a szomszédosság beállítását.
     * 
     * @param tecton1name Az egyik tekton neve
     * @param tecton2name A másik tekton neve
     */
    private void setNeighbours(String tecton1name, String tecton2name){
        if(gc.getPlanet().hasTecton(tecton1name) && gc.getPlanet().hasTecton(tecton2name)){
            Tecton t1 = gc.getPlanet().getTectons().stream().filter(t -> t.getName().equalsIgnoreCase(tecton1name)).findFirst().orElseThrow();
            Tecton t2 = gc.getPlanet().getTectons().stream().filter(t -> t.getName().equalsIgnoreCase(tecton2name)).findFirst().orElseThrow();
            t1.addNeighbour(t2);
            t2.addNeighbour(t1);
            System.out.println("[NEIGHBOUR] " + tecton1name + " and " + tecton2name + " are now neighbours");
        } else {
            System.out.println("[NEIGHBOUR] ERROR! -> " + tecton1name + " or " + tecton2name + " does not exist");
        }
    }
    
    /**
     * Rovarász létrehozása
     * 
     * @param name A létrehozni kívánt rovarász neve
     */
    private void createInsecter(String name){
        if(gc.hasPlayer(name)){
            System.out.println("[INSECTER] ERROR! -> " + name + " named player already exists");
            return;
        }
        if(gc.getPlayers().size() == 4)
        {
        	System.out.println("[INSECTER] ERROR! -> Game already full!");
        	return;
        }
    	Insecter i = new Insecter(name, gc.getTesting());
    	gc.addPlayer(i);
    	System.out.println("[INSECTER] " + name + " created");
    }
    
    /**
     * Gombász létrehozása
     * 
     * @param name A létrehozni kívánt gombász neve
     */
    private void createShroomer(String name){
        if(gc.hasPlayer(name)){
            System.out.println("[SHROOMER] ERROR! -> " + name + " named player already exists");
            return;
        }
        if(gc.getPlayers().size() == 4)
        {
        	System.out.println("[INSECTER] ERROR! -> ready full!");
        	return;
        }
    	Shroomer s = new Shroomer(name, gc.getTesting());
    	gc.addPlayer(s);
    	System.out.println("[SHROOMER] " + name + " created");
    }
    
    /**
     * Gomba létrehozása
     * 
     * @param insect_name A létrehozni kívánt rovar neve
     * @param tecton_name A rovar kezdeti pozíciója
     * @param insecter_name A rovarász neve, akihez tartozik
     */
    private void createInsect(String insect_name, String tecton_name, String insecter_name){
        if(gc.getPlanet().hasInsect(insect_name)) {
            System.out.println("[INSECT] ERROR! -> " + insect_name + " already exists");
            return;
        }
        if(gc.getPlanet().hasTecton(tecton_name) && gc.hasPlayer(insecter_name)){
            Tecton location = gc.getPlanet().getTectons().stream().filter(t -> t.getName().equalsIgnoreCase(tecton_name)).findFirst().orElseThrow();
            Insecter insecter = (Insecter) gc.getPlayers().stream().filter(t -> t.getName().equalsIgnoreCase(insecter_name)).findFirst().orElseThrow();
            Insect i = new Insect(location, insect_name);
            insecter.addInsect(i);
            gc.getPlanet().getInsects().add(i);
            System.out.println("[INSECT] " + insect_name + " created");
        } else {
            System.out.println("[INSECT] ERROR! -> " + tecton_name + " or " + insecter_name + " does not exists");
        }
    }

    /**
     * Gomba létrehozása
     * 
     * @param mushroom_name A létrehozni kívánt gomba neve
     * @param shroomer_name A gombász neve, akihez tartozik
     */
    private void createMushroom(String mushroom_name, String shroomer_name){
        if(gc.getPlanet().hasMushroom(mushroom_name)) {
            System.out.println("[MUSHROOM] ERROR! -> " + mushroom_name + " already exists");
            return;
        }
        if(gc.hasPlayer(shroomer_name)){
            Player shroomer = gc.getPlayers().stream().filter(t -> t.getName().equalsIgnoreCase(shroomer_name)).findFirst().orElseThrow();
            Mushroom m = new Mushroom(mushroom_name, gc.getTesting());
            gc.getPlanet().getMushrooms().add(m);
            ((Shroomer)shroomer).setMushroom(m);
            System.out.println("[MUSHROOM] " + mushroom_name + " created");
        } else {
            System.out.println("[MUSHROOM] ERROR! -> " + shroomer_name + " does not exists");
        }
    }

    /**
     * Gombatest létrehozása. Kezeli az újonnan született gombák, és a teljesen kifejlett gombatest letételét is
     * 
     * @param body_name A létrehozni kívánt gombatest neve
     * @param owner_mushroom_name A hozzá tartozó gomba neve
     * @param tecton_name A cél tekton, ahol kinő
     * @param state A gomba fejlettségi szintje (small/big)
     * @param player Az akciót végrehajtó játékos neve
     */
    private void createMushroomBody(String body_name, String owner_mushroom_name, String tecton_name, String state, String player){
        if(!(gc.getPlanet().hasTecton(tecton_name) && gc.getPlanet().hasMushroom(owner_mushroom_name))){
            System.out.println("[MUSHROOMBODY] ERROR! -> " + owner_mushroom_name + " named mushroom or " + tecton_name + " named tecton does not exists");
            return;
        }
        Tecton tec = gc.getPlanet().getTectons().stream().filter(t -> t.getName().equalsIgnoreCase(tecton_name)).findFirst().orElseThrow();
        Mushroom mush = gc.getPlanet().getMushrooms().stream().filter(m -> m.getName().equalsIgnoreCase(owner_mushroom_name)).findFirst().orElseThrow();
        Player pl =  gc.getPlayers().stream().filter(p -> p.getName().equalsIgnoreCase(player)).findFirst().orElseThrow();
        Shroomer shroom = (Shroomer)pl;
        switch(state) {
        	case "Big" :
        	case "big" :
                if(gc.getTurnCounter() == 1 && !shroom.havePlacedGrownMushBody()) {
                    CanGrowBodyVisitor v = new CanGrowBodyVisitor();
            		TectonAccept acceptor = (TectonAccept) tec;
            		acceptor.accept(v);
            		if(v.canPerformAction()) {
            			gc.getPlanet().getMushbodies().add(mush.placeGrownMushroom(tec, body_name));
            			shroom.placedGrownMushBody();
            		}
            		else {
            			System.out.println("[MUSHROOMBODY] ERROR! -> The given destination tectonite is not suitable for a new mushroom body!");
            			return;
            		}
                } else if(shroom.havePlacedGrownMushBody()) {
                    System.out.println("[MUSHROOMBODY] Shroomer: " + player + " already placed a grown mushroom body");
                    return;
                } else {
                    System.out.println("[MUSHROOMBODY] The game already started, can not place grown MushroomBody");
                    return;
                }
                break;
        	case "Small" :
        	case "small" :
        		MushroomBody mb = new MushroomBody(tec,mush,0,body_name, gc.getTesting());
        		if(!mb.giveBirth(gc.getPlanet().getSpores(), tec, gc.getPlanet().getMushbodies(), gc.getPlanet().getMushstrings(), shroom )) {
        			System.out.println("[MUSHROOMBODY] ERROR! -> The given destination tectonite is not suitable for a new mushroom body!");
        			return;
                }
        		gc.getPlanet().getMushbodies().add(mb);
        		gc.nextTurnCheck(); 
        		break;
        	default:
        		System.out.println("[MUSHROOMBODY] ERROR! -> " + state +" is not a valid state.");
        		return;
        }
        System.out.println("[MUSHROOMBODY] " + body_name + " created");
    }

    /**
     * Spóra szórása
     * 
     * @param spore_name A létrehozni kívánt spóra neve
     * @param owner_mushroombody Az őt termelő test neve
     * @param dest_tecton A cél tekton
     * @param spore_type A spóra típusa
     * @param player Az akciót végrehajtó játékos neve
     */
    private void spreadSpore(String spore_name, String owner_mushroombody, String dest_tecton, String spore_type, String player){
        if(!legitSporeType(spore_type)) {
            System.out.println("[SPORE] ERROR! -> " + spore_type + " is not a spore type");
            return;
        } else if(!gc.hasPlayer(player)) {
            System.out.println("[SPORE] ERROR! -> " + player + " player does not exists");
            return;
        }
        if(gc.getPlanet().hasMushbody(owner_mushroombody) && gc.getPlanet().hasTecton(dest_tecton)){
            MushroomBody mb = gc.getPlanet().getMushbodies().stream().filter(m -> m.getName().equalsIgnoreCase(owner_mushroombody)).findFirst().orElseThrow();
            Tecton tecton = gc.getPlanet().getTectons().stream().filter(t -> t.getName().equalsIgnoreCase(dest_tecton)).findFirst().orElseThrow();
            Player pl =  gc.getPlayers().stream().filter(p -> p.getName().equalsIgnoreCase(player)).findFirst().orElseThrow();
            Shroomer shroom = (Shroomer)pl;
            Spore sp = mb.spreadSpores(tecton, spore_name, spore_type, shroom);
            if(sp != null) {
            	gc.getPlanet().getSpores().add(sp);
            	gc.nextTurnCheck(); 
            	System.out.println("[SPORE] " + spore_name + " created");
            }
            else {
            	System.out.println("[SPORE] ERROR! -> No available spores, or the selected tecton is out of range.");
            }
        } else {
            System.out.println("[SPORE] ERROR! -> " + owner_mushroombody + " or " + dest_tecton + " does not exists");
        }
    }

    /**
     * Helyes spóra típus vizsgálata
     * 
     * @param spore_type A létrehozni kívánt spóra típusa
     * @return true, ha a spóra típusa értelmezett, false különben
     */
    private boolean legitSporeType(String spore_type){
        String type = spore_type.toLowerCase();
        return type.equals("fast") || type.equals("gentle") || type.equals("multiplier") || type.equals("paralyzer") || type.equals("slow");
    }

    /**
     * Fonal kinövése
     * 
     * @param hypha_name Az új fonal neve
     * @param mush_name A fonal gombájának neve
     * @param tecton A fonal születésének helye
     */
    private void growHypha(String hypha_name, String mush_name, String tecton) {
    	boolean hasTecton = gc.getPlanet().getTectons().stream().anyMatch(t -> t.getName().equalsIgnoreCase(tecton));

        boolean hasMushroom = gc.getPlanet().getMushrooms().stream().anyMatch(m -> m.getName().equalsIgnoreCase(mush_name));

        boolean hasMatchingBodyOrString = gc.getPlanet().getMushbodies().stream().anyMatch(mb ->
    	            mb.getLocation().getName().equalsIgnoreCase(tecton) &&
    	            mb.getMushroom().getName().equalsIgnoreCase(mush_name)) ||
    	            gc.getPlanet().getMushstrings().stream().anyMatch(ms ->
    	            ms.getMushroom().getName().equalsIgnoreCase(mush_name) &&
    	            ms.getConnection().stream().anyMatch(t -> t.getName().equalsIgnoreCase(tecton)));

        if (hasTecton && hasMushroom && hasMatchingBodyOrString) {
            ArrayList<Tecton> connection = new ArrayList<>();
            Tecton t1 = gc.getPlanet().getTectons().stream()
                .filter(t -> t.getName().equalsIgnoreCase(tecton))
                .findFirst().orElseThrow(); 
            Mushroom mush = gc.getPlanet().getMushrooms().stream()
                .filter(m -> m.getName().equalsIgnoreCase(mush_name))
                .findFirst().orElseThrow();
            connection.add(0, t1);
            gc.getPlanet().getMushstrings().add(new MushroomString(hypha_name, mush, connection, new ArrayList<>(Arrays.asList(null, null)), gc.getTurnCounter()));
            gc.nextTurnCheck(); 
            System.out.println("[MUSHROOMSTRING] " + hypha_name + " created");
        } else {
            System.out.println("[MUSHROOMSTRING] ERROR! -> " + mush_name + " named mushroom does not exists, or " + tecton + " does not contain a valid mushroom body");
        }
    }

    /**
     * Fonal növekedése
     * 
     * @param hypha_name Az növekedést végrehajtó fonal neve
     * @param dest_tecton A cél tekton neve
     */
    private void branchHypha(String hypha_name, String dest_tecton){
        if(gc.getPlanet().hasHypha(hypha_name) && gc.getPlanet().hasTecton(dest_tecton)){
            Tecton tecton = gc.getPlanet().getTectons().stream().filter(t -> t.getName().equalsIgnoreCase(dest_tecton)).findFirst().orElseThrow();
            MushroomString hypha = gc.getPlanet().getMushstrings().stream().filter(h -> h.getName().equalsIgnoreCase(hypha_name)).findFirst().orElseThrow();
            if(!hypha.branch(tecton, gc.getPlanet().getMushstrings()))
            {
            	System.out.println("[BRANCH] ERROR! -> hypha: The given tecton is not a neighbour of the hypha's location, or the hypha is not grown.");
            	return;
            }
            gc.nextTurnCheck(); 
            System.out.println("[BRANCH] " + hypha_name + " branched to tecton " + dest_tecton);
        } else {
            System.out.println("[BRANCH] ERROR! -> hypha: " + hypha_name + " or tecton: " + dest_tecton + " does not exists");
        }
    }

    /**
     * Rovar mozgatása
     * 
     * @param insect_name Az akciót végrehajtó rovar neve
     * @param dest_tecton_name A cél tekton neve
     * @param player Az akciót végrehajtó játékos neve
     */
    private void moveInsect(String insect_name, String dest_tecton_name, String player){
        if(gc.getPlanet().hasInsect(insect_name) && gc.getPlanet().hasTecton(dest_tecton_name)){
            Tecton destination = gc.getPlanet().getTectons().stream().filter(t -> t.getName().equalsIgnoreCase(dest_tecton_name)).findFirst().orElseThrow();
            Insect insect = gc.getPlanet().getInsects().stream().filter(i -> i.getName().equalsIgnoreCase(insect_name)).findFirst().orElseThrow();
            Tecton oldtecton = insect.getLocation();
            Player pl =  gc.getPlayers().stream().filter(p -> p.getName().equalsIgnoreCase(player)).findFirst().orElseThrow();
            if (!oldtecton.getNeighbours().contains(destination)) {
                System.out.println("[MOVE] " + insect_name + " can't move because " + oldtecton.getName() + " and " + destination.getName() + " are not neighbours.");
                return;
            }
            boolean isConnected = gc.getPlanet().getMushstrings().stream()
                    .anyMatch(ms -> ms.getConnection().contains(oldtecton) && ms.getConnection().contains(destination));

            if (!isConnected) {
                System.out.println("[MOVE] " + insect_name + " can't move because " + oldtecton.getName() + " and " + destination.getName() + " are not connected by a MushroomString.");
                return;
            }
            int n = insect.move(destination);
            if ((pl.getActions() == 0 && n >= 0) || pl.getActions() != 0) {
                pl.setActions(pl.getActions() + n);
            }
            gc.nextTurnCheck(); 
            System.out.println("[MOVE] " + insect_name + " moved: " + oldtecton.getName() + " -> " + destination.getName());
        } else {
            System.out.println("[MOVE] ERROR! -> insect: " + insect_name + " or tecton: " + dest_tecton_name + " does not exists");
        }
    }

    /**
     * Spóra elfogyasztása
     * 
     * @param insect_name Az akciót végrehajtó rovar neve
     * @param spore_name A spóra neve
     */
    private void consumeSpore(String insect_name, String spore_name){
        if(gc.getPlanet().hasInsect(insect_name) && gc.getPlanet().hasSpore(spore_name)) {
            Insect insect = gc.getPlanet().getInsects().stream().filter(i -> i.getName().equalsIgnoreCase(insect_name)).findFirst().orElseThrow();
            Spore spore = gc.getPlanet().getSpores().stream().filter(s -> s.getName().equalsIgnoreCase(spore_name)).findFirst().orElseThrow();
            if(insect.getLocation() == spore.getLocation()) {
            	SporeConsumptionVisitor v = new SporeConsumptionVisitor(insect, gc);
                ((SporeAccept) spore).accept(v);
                //insect.eatSpore(spore);
                gc.nextTurnCheck(); 
                System.out.println("[CONSUME] " + insect_name + " successfully consumed " + spore_name);
            } else {
                System.out.println("[CONSUME] ERROR! -> insect: " + insect_name + " and spore: " + spore_name + " are not on the same tecton");
            }
        } else {
            System.out.println("[CONSUME] ERROR! -> insect: " + insect_name + " or spore: " + spore_name + " does not exists");
        }
    }

    /**
     * Gombafonal elvágása
     * 
     * @param insect_name Az akciót végrehajtó rovar neve
     * @param hypha_name A fonal neve
     */
    private void cutHypha(String insect_name, String hypha_name){
        if(gc.getPlanet().hasInsect(insect_name) && gc.getPlanet().hasHypha(hypha_name)){
            Insect insect = gc.getPlanet().getInsects().stream().filter(i -> i.getName().equalsIgnoreCase(insect_name)).findFirst().orElseThrow();
            MushroomString mushroomstring = gc.getPlanet().getMushstrings().stream().filter(s -> s.getName().equalsIgnoreCase(hypha_name)).findFirst().orElseThrow();
            if(mushroomstring.getConnection().contains(insect.getLocation())) {
                insect.cutHypha(mushroomstring, gc.getPlanet().getMushstrings());
                gc.nextTurnCheck(); 
                System.out.println("[CUT] " + insect_name + " successfully cut " + hypha_name);
            } else {
                System.out.println("[CUT] ERROR! -> insect: " + insect_name + " and mushroomstring: " + hypha_name + " are not on the same tecton");
            }
        } else {
            System.out.println("[CUT] ERROR! -> insect: " + insect_name + " or mushroomstring: " + hypha_name + " does not exists");
        }
    }

    /**
     * Tekton széttörése
     * 
     * @param separeableTecton A kettétörni kívánt tekton neve
     * @param newTecton1 Az egyik új tekton neve, ami keletkezik
     * @param newTecton2 A másik, ami keletkezik
     */
    private void splitTecton(String separeableTecton, String newTecton1, String newTecton2){
        if(gc.getPlanet().hasTecton(separeableTecton)) {
            Tecton tecton = gc.getPlanet().getTectons().stream().filter(t -> t.getName().equalsIgnoreCase(separeableTecton)).findFirst().orElseThrow();
            gc.getPlanet().splitTecton(tecton, newTecton1, newTecton2);
            System.out.println("[SPLIT] " + separeableTecton + " splitted into " + newTecton1 + " and " + newTecton2);
        } else {
            System.out.println("[SPLIT] ERROR! -> tecton: " + separeableTecton + " does not exists");
        }
    }
    
    /**
     * Passzolás
     * 
     * @param player Az akciót végrehajtó játékos neve
     */
    private void pass(String player) {
    	Player pl = gc.getPlayers().stream().filter(p -> p.getName().equalsIgnoreCase(player)).findFirst().orElseThrow();
    	pl.pass();
    	gc.nextTurnCheck(); 
    	System.out.println("[PASS] " + player + " has given up his/her remaining actions");
    }

    /**
     * A mentést megvalósító függvény. A játék állapotát parancsokként menti el.
     *
     * @param path A mentési file elérési útja
     */
    private void saveState(String path) {
        try {
            java.io.FileWriter writer = new java.io.FileWriter(path);
            Planet planet = gc.getPlanet();

            // Mentjük a tektonokat
            for (Tecton tecton : planet.getTectons()) {
                String type = "";
                if (tecton instanceof BigTecton) type = "big";
                else if (tecton instanceof SmallTecton) type = "small";
                else if (tecton instanceof CoarseTecton) type = "coarse";
                else if (tecton instanceof ToxicTecton) type = "toxic";
                else if (tecton instanceof HealingTecton) type = "healing";
                writer.write("Tecton " + tecton.getName() + " " + type + "\n");
            }

            // Mentjük a szomszédságokat
            for (Tecton tecton : planet.getTectons()) {
                for (Tecton neighbor : tecton.getNeighbours()) {
                    // Elkerüljük a duplikációt: csak akkor mentjük, ha az első tecton neve
                    // abc sorrendben előbb van, mint a második
                    if (tecton.getName().compareTo(neighbor.getName()) < 0) {
                        writer.write("SetNeighbours " + tecton.getName() + " " + neighbor.getName() + "\n");
                    }
                }
            }

            // Mentjük a játékosokat
            for (Player player : gc.getPlayers()) {
                if (player instanceof Shroomer) {
                    writer.write("Shroomer " + player.getName() + "\n");
                } else if (player instanceof Insecter) {
                    writer.write("Insecter " + player.getName() + "\n");
                }
            }

            // Mentjük a gombákat
            for (Mushroom mushroom : planet.getMushrooms()) {
                // Megkegkeressük, hogy melyik gombászhoz tartozik
                for (Player player : gc.getPlayers()) {
                    if (player instanceof Shroomer && ((Shroomer) player).getMushroom() == mushroom) {
                        writer.write("Mushroom " + mushroom.getName() + " " + player.getName() + "\n");
                        break;
                    }
                }
            }

            // Mentjük a gombatesteket
            for (MushroomBody body : planet.getMushbodies()) {
                String state = body.getState().toLowerCase();
                writer.write("MushroomBody " + body.getName() + " " + body.getMushroom().getName() + " " + body.getLocation().getName() + " " + state + " ");
                // Megkeressük meg a gombászát
                for (Player player : gc.getPlayers()) {
                    if (player instanceof Shroomer && ((Shroomer) player).getMushroom() == body.getMushroom()) {
                        writer.write(player.getName() + "\n");
                        break;
                    }
                }
            }

            // Mentjük a gombafonalakat
            for (MushroomString string : planet.getMushstrings()) {
                if (string.getConnection().isEmpty()) continue;
                Tecton firstTecton = string.getConnection().get(0);
                writer.write("MushroomString " + string.getName() + " " + string.getMushroom().getName() + " " + firstTecton.getName() + " ");
                // Megkeressük meg a gombászát
                for (Player player : gc.getPlayers()) {
                    if (player instanceof Shroomer && ((Shroomer) player).getMushroom() == string.getMushroom()) {
                        writer.write(player.getName() + "\n");
                        break;
                    }
                }

                // Ha van második kapcsolt tecton, akkor branch parancsot is kiadunk
                if (string.getConnection().size() > 1) {
                    Tecton secondTecton = string.getConnection().get(1);
                    // Megkeressük meg a gombászát
                    for (Player player : gc.getPlayers()) {
                        if (player instanceof Shroomer && ((Shroomer) player).getMushroom() == string.getMushroom()) {
                            writer.write("Branch " + string.getName() + " " + secondTecton.getName() + " " + player.getName() + "\n");
                            break;
                        }
                    }
                }
            }

            // Mentjük a rovarokat
            for (Insect insect : planet.getInsects()) {
                // Megkeressük meg, hogy melyik rovarászhoz tartozik
                for (Player player : gc.getPlayers()) {
                    if (player instanceof Insecter && ((Insecter) player).getInsects().contains(insect)) {
                        writer.write("Insect " + insect.getName() + " " + insect.getLocation().getName() + " " + player.getName() + "\n");
                        break;
                    }
                }
            }
            // Mentjük a spórákat
            for (Spore spore : planet.getSpores()) {
                if (spore.getDead()) continue; // Halott spórákat nem mentünk
                String sporeType = "";
                if (spore instanceof FastSpore) sporeType = "fast";
                else if (spore instanceof SlowSpore) sporeType = "slow";
                else if (spore instanceof GentleSpore) sporeType = "gentle";
                else if (spore instanceof ParalyzerSpore) sporeType = "paralyzer";
                else if (spore instanceof MultiplierSpore) sporeType = "multiplier";
                // Megkeressük meg a gombatestet, ami ezt a spórát létrehozta
                for (MushroomBody body : planet.getMushbodies()) {
                    if (body.getMushroom() == spore.getMushroom()) {
                        // Megkeressük meg a gombászát
                        for (Player player : gc.getPlayers()) {
                            if (player instanceof Shroomer && ((Shroomer) player).getMushroom() == body.getMushroom()) {
                                writer.write("SpreadSpore " + spore.getName() + " " + body.getName() + " " + spore.getLocation().getName() + " " + sporeType + " " + player.getName() + "\n");
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            // Mentjük a játék állapotát
            writer.write("# Game state\n");
            writer.write("TurnCounter " + gc.getTurnCounter() + "\n");
            writer.write("CurrentPlayer " + gc.getCurrentPlayer().getName() + "\n");

            // Mentjük a játékosok pontszámait
            for (Player player : gc.getPlayers()) {
                writer.write("PlayerScore " + player.getName() + " " + player.getScore() + " " + player.getActions() + "\n");
            }
            writer.close();
            System.out.println("[SAVE] State saved successfully to " + path);
        } catch (Exception e) {
            System.out.println("[SAVE] ERROR! -> Failed to save state: " + e.getMessage());
        }
    }

    /**
     * Mentés betöltését megvalósító függvény
     *
     * @param path A mentési file elérési útja
     */
    private void loadState(String path) {
        try {
            // Új játéktér létrehozása
            gc = new GameController(gc.getTesting(), gc.getMaxTurn());
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(path));
            String line;

            // Előfeldolgozás: sorok rendezése típus szerint
            ArrayList<String> tectonLines = new ArrayList<>();
            ArrayList<String> neighbourLines = new ArrayList<>();
            ArrayList<String> playerLines = new ArrayList<>();
            ArrayList<String> mushroomLines = new ArrayList<>();
            ArrayList<String> bodyLines = new ArrayList<>();
            ArrayList<String> stringLines = new ArrayList<>();
            ArrayList<String> branchLines = new ArrayList<>();
            ArrayList<String> insectLines = new ArrayList<>();
            ArrayList<String> sporeLines = new ArrayList<>();
            ArrayList<String> stateLines = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.trim().isEmpty()) continue; // Megjegyzések kihagyása

                String[] parts = line.split("\\s+");
                String command = parts[0].toLowerCase();

                switch (command) {
                    case "tecton": tectonLines.add(line); break;
                    case "setneighbours": neighbourLines.add(line); break;
                    case "shroomer": case "insecter": playerLines.add(line); break;
                    case "mushroom": mushroomLines.add(line); break;
                    case "mushroombody": bodyLines.add(line); break;
                    case "mushroomstring": stringLines.add(line); break;
                    case "branch": branchLines.add(line); break;
                    case "insect": insectLines.add(line); break;
                    case "spreadspore": sporeLines.add(line); break;
                    case "turncounter": case "currentplayer": case "playerscore":
                        stateLines.add(line); break;
                }
            }
            reader.close();
            // Tektonok feldolgozása
            for (String tectonLine : tectonLines) {
                processCommand(tectonLine, gc.getPlanet());
            }
            // Szomszédságok feldolgozása
            for (String neighbourLine : neighbourLines) {
                processCommand(neighbourLine, gc.getPlanet());
            }
            // Játékosok feldolgozása
            for (String playerLine : playerLines) {
                processCommand(playerLine, gc.getPlanet());
            }
            // Gombák feldolgozása
            for (String mushroomLine : mushroomLines) {
                processCommand(mushroomLine, gc.getPlanet());
            }
            // Gombatestek feldolgozása
            for (String bodyLine : bodyLines) {
                processCommand(bodyLine, gc.getPlanet());
            }
            // Gombafonalak feldolgozása
            for (String stringLine : stringLines) {
                processCommand(stringLine, gc.getPlanet());
            }
            // Elágazások feldolgozása
            for (String branchLine : branchLines) {
                processCommand(branchLine, gc.getPlanet());
            }
            // Rovarok feldolgozása
            for (String insectLine : insectLines) {
                processCommand(insectLine, gc.getPlanet());
            }
            // Spórák feldolgozása
            for (String sporeLine : sporeLines) {
                processCommand(sporeLine, gc.getPlanet());
            }
            // Játék állapot feldolgozása
            for (String stateLine : stateLines) {
                String[] parts = stateLine.split("\\s+");
                if (parts[0].equalsIgnoreCase("TurnCounter")) {
                    gc.setTurnCounter(Integer.parseInt(parts[1]));
                }
                else if (parts[0].equalsIgnoreCase("CurrentPlayer")) {
                    String playerName = parts[1];
                    for (Player player : gc.getPlayers()) {
                        if (player.getName().equals(playerName)) {
                            gc.setCurrentPlayer(player);
                            break;
                        }
                    }
                }
                else if (parts[0].equalsIgnoreCase("PlayerScore")) {
                    String playerName = parts[1];
                    int score = Integer.parseInt(parts[2]);
                    int actions = Integer.parseInt(parts[3]);
                    for (Player player : gc.getPlayers()) {
                        if (player.getName().equals(playerName)) {
                            player.setScore(score);
                            player.setActions(actions);
                            break;
                        }
                    }
                }
            }
            System.out.println("[LOAD] State loaded successfully from " + path);
        } catch (Exception e) {
            System.out.println("[LOAD] ERROR! -> Failed to load state: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Annak vizsgálata, hogy egy akciót "felemésztő" parancsot az adott játékos végrehajthat-e
     *
     * @param commandName A parancs neve
     * @param playerName A parancsot végrehajtani kívánó játékos neve
     */   
    private boolean isValidCommandAction(String commandName, String playerName) {
        Player currentPlayer = gc.getCurrentPlayer();
        return currentPlayer.getName().equals(playerName) &&
               currentPlayer.remainingActions > 0 &&
               currentPlayer.canExecuteCommand(commandName);
    }
    
    /**
     * Annak vizsgálata, hogy egy parancs megfelelő számú paraméterrel lett beírva
     * 
     * @param expectedLength Az elvárt hossz
     * @param actualLength Az aktuális hossz
     */  
    private boolean isValidLength(int expectedLength, int actualLength) {
    	return actualLength == expectedLength;
    }
    
    /**
     * A beírt parancs feldolgozási függvénye
     * 
     * @param commandLine A beírt sor
     * @param planet A bolygó, amin értelmezzük a parancsot
     */
    public void processCommand(String commandLine, Planet planet) {
    	String[] command;
    	
    	if (planet != null) {
    		gc.setPlanet(planet);
        }
        command = commandLine.split("\\s+");
    
        if (!command[0].equalsIgnoreCase("exit")) {
            switch(command[0]){
            	case "help":
            	case "?": {
            		printHelp();
            		break;
            	}
                case "List" :
                case "list" : {
                	if(isValidLength(1, command.length)) {
                		ArrayList<String> s = getStateLines();
                		for(String string : s) {
                			System.out.println(string);
                		}
                        break;
                	}
                	break;
                }
                case "Tecton" :
                case "tecton" : {
                    if(!isValidLength(3, command.length)) {
                        System.out.println("Incorrect usage of command: Tecton");
                        break;
                    }
                    createTecton(command[1], command[2]);
                    break;
                }
                case "SetNeighbours" :
                case "setneighbours" : {
                    if(!isValidLength(3, command.length)) {
                        System.out.println("Incorrect usage of command: SetNeighbours");
                        break;
                    }
                    setNeighbours(command[1], command[2]);
                    break;
                }
                
                case "Insecter" :
                case "insecter" : {
                    if(!isValidLength(2, command.length)) {
                        System.out.println("Incorrect usage of command: Insecter");
                        break;
                    }
                    createInsecter(command[1]);
                    break;
                }
                case "Shroomer" :
                case "shroomer" : {
                    if(!isValidLength(2, command.length)) {
                        System.out.println("Incorrect usage of command: Shroomer");
                        break;
                    }
                    createShroomer(command[1]);
                    break;
                }
                
                case "Insect" :
                case "insect" : {
                    if(!isValidLength(4, command.length)) {
                        System.out.println("Incorrect usage of command: Insect");
                        break;
                    }
                    createInsect(command[1], command[2], command[3]);
                    break;
                }
                case "Mushroom" :
                case "mushroom" : {
                    if(!isValidLength(3, command.length)) {
                        System.out.println("Incorrect usage of command: Mushroom");
                        break;
                    }
                    createMushroom(command[1], command[2]);
                    break;
                }
                case "MushroomBody" :
                case "mushroombody" : {
                    if(!isValidCommandAction(command[0], command[5]) || !isValidLength(6, command.length)) {

                        System.out.println("Incorrect usage of command: MushroomBody");
                        break;
                    }
                    createMushroomBody(command[1], command[2], command[3], command[4], command[5]);
                    break;
                }

                case "SpreadSpore" :
                case "spreadspore" :
                {
                    if(!isValidCommandAction(command[0], command[5]) || !isValidLength(6, command.length)) {
                        System.out.println("Incorrect usage of command: SpreadSpore");
                        break;
                    }
                    spreadSpore(command[1], command[2], command[3], command[4], command[5]);
                    break;
                }
                case "MushroomString" :
                case "mushroomstring" : {
                    if(!isValidCommandAction(command[0], command[4]) || !isValidLength(5, command.length)) {
                        System.out.println("Incorrect usage of command: MushroomString");
                        break;
                    }
                    growHypha(command[1], command[2], command[3]);
                    break;
                }
                case "Branch" :
                case "branch" : {
                    if(!isValidCommandAction(command[0], command[3]) || !isValidLength(4, command.length)) {
                        System.out.println("Incorrect usage of command: Branch");
                        break;
                    }
                    branchHypha(command[1], command[2]);
                    break;
                }
                case "Move" :
                case "move" : {
                    if(!isValidCommandAction(command[0], command[3]) || !isValidLength(4, command.length)) {
                        System.out.println("Incorrect usage of command: Move");
                        break;
                    }
                    moveInsect(command[1], command[2], command[3]);
                    break;
                }
                case "Consume" :
                case "consume" : {
                    if(!isValidCommandAction(command[0], command[3]) || !isValidLength(4, command.length)) {
                        System.out.println("Incorrect usage of command: Consume");
                        break;
                    }
                    consumeSpore(command[1], command[2]);
                    break;
                }
                case "Cut" :
                case "cut" : {
                    if(!isValidCommandAction(command[0], command[3]) || !isValidLength(4, command.length)) {
                        System.out.println("Incorrect usage of command: Cut");
                        break;
                    }
                    cutHypha(command[1], command[2]);
                    break;
                }
                case "Split":
                case "split" : {
                    if(!isValidLength(4, command.length)) {
                        System.out.println("Incorrect usage of command: Split");
                        break;
                    }
                    splitTecton(command[1], command[2], command[3]);
                    break;
                }
                
                case "Pass" :
                case "pass" : {
                    if(!isValidCommandAction(command[0], command[1]) || !isValidLength(2, command.length)) {
                        System.out.println("Incorrect usage of command: Pass");
                        break;
                    }
                    pass(command[1]);
                    break;
                }
                
                case "Save" :
                case "save" : {
                    if(!isValidLength(2, command.length)) {
                        System.out.println("Incorrect usage of command: Save");
                        break;
                    }
                    saveState(command[1]);
                    break;
                }
                case "Load" :
                case "load" : {
                    if(!isValidLength(2, command.length)) {
                        System.out.println("Incorrect usage of command: Load");
                        break;
                    }
                    loadState(command[1]);
                    break;
                }
                default : {
                    System.out.println("Unknown command. Use 'help' or '?' for help");
                }
            }
        }
    }
    
    public ArrayList<String> getStateLines() {

        ArrayList<String> lines = new ArrayList<>();
        Planet planet = gc.getPlanet();

        /* körszám fejléc */
        lines.add("Current round: " + gc.getTurnCounter());
        lines.add("");

        /* ---------- Tectonok ---------- */
        int idx = 1;
        for (Tecton t : planet.getTectons()) {
            lines.add(idx++ + ". Tecton " + t.getName() + " {");

            /* típus */
            TectonTypePrintVisitor v = new TectonTypePrintVisitor();
            lines.add("  " + ((TectonAccept) t).accept(v));

            /* szomszédok */
            String neigh = t.getNeighbours().stream()
                    .map(Tecton::getName)
                    .collect(Collectors.joining(", "));
            lines.add("  Neighbours: {" + neigh + "}");

            /* összegyűjtött listák */
            String bodies = planet.getMushbodies().stream()
                    .filter(mb -> mb.getLocation() == t)
                    .map(MushroomBody::getName)
                    .collect(Collectors.joining(", "));
            lines.add("  MushroomBodies: {" + bodies + "}");

            String strings = planet.getMushstrings().stream()
                    .filter(ms -> ms.getConnection().contains(t))
                    .map(MushroomString::getName)
                    .collect(Collectors.joining(", "));
            lines.add("  MushroomStrings: {" + strings + "}");

            String insects = planet.getInsects().stream()
                    .filter(i -> i.getLocation() == t)
                    .map(Insect::getName)
                    .collect(Collectors.joining(", "));
            lines.add("  Insects: {" + insects + "}");

            String spores = planet.getSpores().stream()
                    .filter(s -> s.getLocation() == t)
                    .map(Spore::getName)
                    .collect(Collectors.joining(", "));
            lines.add("  Spores: {" + spores + "}");

            lines.add("}");
        }

        /* ---------- Insects ---------- */
        idx = 1;
        for (Insect i : planet.getInsects()) {
            lines.add(idx++ + ". Insect " + i.getName() + " {");
            lines.add("  Tecton: " + i.getLocation().getName());
            lines.add("  Nutrients: " + i.getNutrients());
            lines.add("  Effect: " + i.getEffect());
            lines.add("  can move? " + (i.getCanMove() ? "yes" : "no"));
            lines.add("  can cut string? " + (i.getCanCutString() ? "yes" : "no"));
            lines.add("  is dead? " + (i.getDead() ? "yes" : "no"));
            lines.add("}");
        }

        /* ---------- Mushrooms ---------- */
        idx = 1;
        for (Mushroom m : planet.getMushrooms()) {
            lines.add(idx++ + ". Mushroom " + m.getName() + " {");
            lines.add("  is dead? " + (m.getDead() ? "yes" : "no"));
            lines.add("}");
        }

        /* ---------- MushroomBodies ---------- */
        idx = 1;
        for (MushroomBody mb : planet.getMushbodies()) {
            lines.add(idx++ + ". MushroomBody " + mb.getName() + " {");
            lines.add("  Owner: " + mb.getMushroom().getName());
            lines.add("  Tecton: " + mb.getLocation().getName());
            lines.add("  state: " + mb.getState());
            lines.add("  spores available? " + (mb.canSpreadSpores() ? "yes" : "no"));
            lines.add("  remaining sporulations: " + mb.getRemainingSporulation());
            lines.add("  is dead? " + (mb.getDead() ? "yes" : "no"));
            lines.add("}");
        }

        /* ---------- MushroomStrings ---------- */
        idx = 1;
        for (MushroomString ms : planet.getMushstrings()) {
            lines.add(idx++ + ". MushroomString " + ms.getName() + " {");
            lines.add("  Mushroom: " + ms.getMushroom().getName());

            String con = ms.getConnection().stream()
                    .map(Tecton::getName)
                    .collect(Collectors.joining(", "));
            lines.add("  Connections: {" + con + "}");

            String neigh = ms.getNeighbours().stream()
                    .filter(Objects::nonNull)
                    .map(MushroomString::getName)
                    .collect(Collectors.joining(", "));
            lines.add("  Neighbours: {" + neigh + "}");

            lines.add("  is dead? " + (ms.getDead() ? "yes" : "no"));
            lines.add("}");
        }

        /* ---------- Spores ---------- */
        idx = 1;
        for (Spore s : planet.getSpores()) {
            lines.add(idx++ + ". Spore " + s.getName() + " {");
            lines.add("  Mushroom: " + s.getMushroom().getName());
            lines.add("  Tecton: " + s.getLocation().getName());
            lines.add("  Nutrient value: " + s.getNutrientValue());
            lines.add("  is dead? " + (s.getDead() ? "yes" : "no"));
            lines.add("}");
        }

        /* ---------- Players ---------- */
        idx = 1;
        for (Player p : gc.getPlayers()) {
            lines.add(idx++ + ". Player " + p.getName() + " {");

            PlayerListVisitor pv = new PlayerListVisitor(p.getName());
            ((PlayerAccept) p).accept(pv);
            lines.addAll(pv.getLines());

            if (gc.getCurrentPlayer() == p) {
                lines.add("  Remaining actions: " + p.getActions());
            }
            lines.add("}");
        }

        return lines;
    }

    /**
     * Main függvény. Létrehoz egy új terminált, és amíg nem kap exit-et, várja a parancsokat, utána kilép a programból.
     */   
    public static void main(String[] args) {
    	Terminal t = new Terminal(true, 20); //FALSE KELL LEGYEN, csak egyelőre true a tesztelés miatt.
        Scanner scanner = new Scanner(System.in);
        String commandLine;
        String[] command;
        System.out.println("Welcome to Fungorium!");
        System.out.println("Type 'help' or '?' for the commands, or 'exit' to exit the application");
        do {
            System.out.print("> ");
            commandLine = scanner.nextLine().trim();
            command = commandLine.split("\\s+");
            t.processCommand(commandLine, t.gc.getPlanet());
        } while (!command[0].equalsIgnoreCase("exit"));
        System.out.println("Program closed.");
        scanner.close();
    }
}
