package main.java;


import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

import main.java.player.*;
import main.java.tecton.*;
import main.java.view.DefaultSporeDrawer;

/**
 * A GameController osztály felel a játék menetért, körváltásért, 
 * és a győztesek meghatározásáért. Egy Planet példányhoz kapcsolódik.
 */
public class GameController {

    private Planet planet;
    private int turnCounter;
    private final int maxTurn;
    private final boolean testing;
    private final ArrayList<Player> players;
    private Player currentPlayer;
    private final transient Runnable repaintCallback;

    private boolean init = true;
    
    /**
     * Létrehoz egy GameController példányt a megadott maximális körszámmal, és egy tesztelési paraméterrel.
     *
     * @param maxTurn a játék maximális körszámát adja meg
     */
    public GameController(boolean testing, int maxTurn, Runnable repaintCallback) {
    	this.maxTurn = maxTurn;
        this.testing = testing;
        this.repaintCallback = repaintCallback;
        planet = new Planet();
        players = new ArrayList<>();
        turnCounter = 1;
    }

    /**
     * A kirajzoláskor egy véletlen eltolást hoz létre az adott tektonon, hogy ne minden objektum egymáson legyen.
     *
     * @param geometry a felhasznált GeometryTecton példány
     * @return az új geometry
     */
    public Geometry randomOffsetInsideCircle(GeometryTecton geometry) {
        Random rand = new Random();
        Geometry g;
        int x, y;
        do {
            x = rand.nextInt(2 * geometry.getRadius()) - geometry.getRadius();
            y = rand.nextInt(2 * geometry.getRadius()) - geometry.getRadius();
        } while (Math.sqrt((x * x + y * y)) > geometry.getRadius() - DefaultSporeDrawer.SIZE);
        g = new Geometry(geometry.getX() + x, geometry.getY() + y);
        return g;
    }

    /**
     * Létrehozza a pályát, elhelyezi a tektonokat
     */
    public Planet buildPlanet() {
        Planet planet = new Planet();

        addTectons(planet, BigTecton::new, 4,
                new GeometryTecton(600, 200, 110),
                new GeometryTecton(532, 830, 110),
                new GeometryTecton(1734, 325, 110),
                new GeometryTecton(1264, 267, 110),
                new GeometryTecton(900, 800, 110)
        );

        addTectons(planet, SmallTecton::new, 2,
                new GeometryTecton(900, 400, 55),
                new GeometryTecton(100, 731, 55),
                new GeometryTecton(1510, 412, 55)
        );

        addTectons(planet, HealingTecton::new, 3,
                new GeometryTecton(250, 250, 85),
                new GeometryTecton(1123, 581, 85),
                new GeometryTecton(310, 700, 85)
        );

        addTectons(planet, CoarseTecton::new, 3,
                new GeometryTecton(400, 400, 90),
                new GeometryTecton(1564, 153, 90),
                new GeometryTecton(700, 420, 90)
        );

        addTectons(planet, ToxicTecton::new, 3,
                new GeometryTecton(600, 600, 95),
                new GeometryTecton(1400, 800, 95)
        );

        planet.recalcNeighbours();
        return planet;
    }

    /**
     * Segédfüggvény a tektonok létrehozásához
     */
    private <T extends Tecton> void addTectons(Planet planet, Function<Integer, T> constructor, int energy, GeometryTecton... geometries) {
        for (GeometryTecton geometry : geometries) {
            T tecton = constructor.apply(energy);
            tecton.setGeometry(geometry);
            planet.addTecton(tecton);
        }
    }


    /**
     * Megvizsgálja, hogy minden játékos letette-e már a kezdő objektumát. Ha igen, akkor innentől kezdve éles a játék.
     */
    public void setInitCheck()
    {
        if(planet.getInsects().size() == 2 && planet.getMushbodies().size() == 2) {
            init = false;
        }
    }

    //getterek, setterek
    public int getTurnCounter() {
    	return turnCounter;
    }

    public Planet getPlanet() {
        return planet;
    }

    public void setTurnCounter(int turnCounter) {
        this.turnCounter = turnCounter;
    }

    public int getMaxTurn() {
    	return maxTurn;
    }
    
    public void setPlanet(Planet newPlanet) {
        this.planet = newPlanet;
    }
    
    public ArrayList<Player> getPlayers() {
    	return players;
    }

    public Player getCurrentPlayer() {
    	return currentPlayer;
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public void setCurrentPlayerToNextPlayer() {
        int index = players.indexOf(currentPlayer);
        index = (index + 1) % players.size();
        currentPlayer = players.get(index);
    }

    public boolean getInit() {
        return init;
    }
    public void setInit(boolean init) {
        this.init = init;
    }
    
    /**
     * Ezzel a metódussal lehet játékosokat hozzáadni a listához. Ha ez az első eleme, beállítja kezdőjátékosnak.
     *
     * @param player A játékos, akit hozzá szeretnénk adni a listához
     */
    public void addPlayer(Player player) {
    	players.add(player);
    	if(players.size() == 1) {
    		currentPlayer = players.get(0);
    	}
    }
    
    /** Egy játék-kör zárása:
     *  – ha minden játékos kifogyott az akcióból VAGY passzolt, új kör indul
     *  – ekkor Planet.update() fut, visszatöltjük az akciókat, körszámláló nő
     */
    public ArrayList<Player> nextTurnCheck() {
        planet.checkForDeadShrooms();
        planet.deleteDeadObjects(turnCounter, players);
        planet.checkForBodyConnection();

        if (currentPlayer.getActions() > 0) {
            currentPlayer.takeAction();
        }

        if (currentPlayer.getActions() == 0) {
            if (turnCounter == maxTurn) {
                return determineWinners();
            }
            int currentIndex = players.indexOf(currentPlayer);
            int nextIndex = (currentIndex + 1) % players.size();
            currentPlayer = players.get(nextIndex);
            turnCounter++;
            currentPlayer.update(testing);
            planet.growBodyOnParalyzedInsect(this);
            planet.update(!testing);
            planet.deleteDeadObjects(turnCounter, players);
        }
        repaintCallback.run();
        return new ArrayList<>();
    }


    /**
     * Meghatározza a játék győzteseit egy GameOverVisitor segítségével.
     *
     * @return egy lista, amely tartalmazza a legjobb Shroomer és Insecter típusú játékosokat
     */
    public ArrayList<Player> determineWinners() {
        GameOverVisitor visitor = new GameOverVisitor();
        ArrayList<Player> winners = new ArrayList<>();
        for (Player player : players) {
            ((PlayerAccept) player).accept(visitor);
        }
        if(visitor.getBestShroomer() != null) {
            winners.add(visitor.getBestShroomer());
        }
        if(visitor.getBestInsecter() != null) {
            winners.add(visitor.getBestInsecter());
        }
        return winners;
    }
}
