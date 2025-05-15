package main.java;


import java.util.ArrayList;
import java.util.Random;

import main.java.mushroom.GeometryString;
import main.java.mushroom.Mushroom;
import main.java.mushroom.MushroomString;
import main.java.player.*;
import main.java.tecton.*;
import main.java.view.DefaultSporeDrawer;
import main.java.view.MushroomStringDrawer;

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
    private final Runnable repaintCallback;

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
     * A kirajzoláskor egy vélwtlen eltolást hoz létre az adott tektonon, hogy ne minden objektum egymáson legyen.
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

    public void setInit(boolean init) {
        this.init = init;
    }

    /**
     * Létrehozza a pályát - ez egyelőre placeholder, kell több tecton majd
     */
    public Planet buildPlanet(){
    	Planet planet = new Planet();
    	
    	BigTecton t1 = new BigTecton(3);
    	t1.setGeometry(new GeometryTecton(600, 200, 110));

    	SmallTecton t2 = new SmallTecton(3);
    	t2.setGeometry(new GeometryTecton(900, 400, 55));
    	
    	HealingTecton t3 = new HealingTecton(3);
    	t3.setGeometry(new GeometryTecton(250, 250, 85));
    	
    	CoarseTecton t4 = new CoarseTecton(3);
    	t4.setGeometry(new GeometryTecton(400, 400, 90));
    	
    	ToxicTecton t5 = new ToxicTecton(3);
    	t5.setGeometry(new GeometryTecton(600, 600, 95));

        //Mushroom m = new Mushroom(false);
        //MushroomString ms1 = new MushroomString("hypha", m, new ArrayList<>(), new ArrayList<>(), 0, new GeometryString(0, 0, 100, 100));
    	
    	planet.addTecton(t1);
    	planet.addTecton(t2);
    	planet.addTecton(t3);
    	planet.addTecton(t4);
    	planet.addTecton(t5);
        //planet.addMushroomString(ms1);
        planet.recalcNeighbours();
    	
    	return planet;
    }
    /**
     * Megvizsgálja, hogy minden játékos letette-e már a kezdő objektumát. Ha igen, akkor innentől kezdve éles a játék.
     */
    public void setInitCheck()
    {
        if(planet.getInsects().size() == 2 && planet.getMushbodies().size() == 2)
            init = false;
    }

    //getterek, setterek
    public int getTurnCounter() {
    	return turnCounter;
    }

    public Planet getPlanet() {return planet;}

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
    public boolean getInit() {return init;}
    
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

        if (currentPlayer.getActions() > 0)
            currentPlayer.takeAction();

        if (currentPlayer.getActions() == 0) {
            if (turnCounter == maxTurn) {
                return determineWinners();
            }
            int currentIndex = players.indexOf(currentPlayer);
            int nextIndex = (currentIndex + 1) % players.size();
            currentPlayer = players.get(nextIndex);
            turnCounter++;
            currentPlayer.update(testing);
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
        if(visitor.getBestShroomer() != null)
            winners.add(visitor.getBestShroomer());
        if(visitor.getBestInsecter() != null)
            winners.add(visitor.getBestInsecter());
        return winners;
    }
}
