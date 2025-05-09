package main.java;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import main.java.player.*;
import main.java.spore.FastSpore;
import main.java.spore.Spore;
import main.java.tecton.*;
import main.java.view.DefaultSporeDrawer;
import main.java.insect.Insect;
import main.java.mushroom.*;

/**
 * A GameController osztály felel a játék menetért, körváltásért, 
 * és a győztesek meghatározásáért. Egy Planet példányhoz kapcsolódik.
 */
public class GameController {
    private Planet planet;
    private final int maxTurn;
    boolean testing;
    private int turnCounter;
    private final ArrayList<Player> players;
    private Player currentPlayer;

    private boolean init = true;
    
    /**
     * Létrehoz egy GameController példányt a megadott maximális körszámmal, és egy tesztelési paraméterrel.
     *
     * @param maxTurn a játék maximális körszámát adja meg
     */
    public GameController(boolean testing, int maxTurn) {
    	this.maxTurn = maxTurn;
        this.testing = testing;
        planet = new Planet();
        players = new ArrayList<>();
        turnCounter = 1;
    }

    private Geometry randomOffsetInsideCircle(GeometryTecton geometry) {
        Random rand = new Random();
        Geometry g;
        int x, y;

        do {
            x = rand.nextInt(2 * geometry.getRadius()) - geometry.getRadius();
            y = rand.nextInt(2 * geometry.getRadius()) - geometry.getRadius();
        } while (Math.sqrt((double) (x * x + y * y)) > geometry.getRadius() - DefaultSporeDrawer.SIZE);

        g = new Geometry(geometry.getX() + x, geometry.getY() + y);

        return g;
    }

    /**
     * Létrehozza a pályát - ez egyelőre placeholder, kell több tecton majd
     */
    public Planet buildPlanet(){
    	Planet planet = new Planet();
    	
    	BigTecton t1 = new BigTecton("T1", 3);
    	t1.setGeometry(new GeometryTecton(600, 200, 110));

    	SmallTecton t2 = new SmallTecton("T2", 3);
    	t2.setGeometry(new GeometryTecton(900, 400, 55));
    	
    	HealingTecton t3 = new HealingTecton("T3", 3);
    	t3.setGeometry(new GeometryTecton(250, 250, 85));
    	
    	CoarseTecton t4 = new CoarseTecton("T4", 3);
    	t4.setGeometry(new GeometryTecton(400, 400, 90));
    	
    	ToxicTecton t5 = new ToxicTecton("T5", 3);
    	t5.setGeometry(new GeometryTecton(600, 600, 95));
    	
    	planet.addTecton(t1);
    	planet.addTecton(t2);
    	planet.addTecton(t3);
    	planet.addTecton(t4);
    	planet.addTecton(t5);
    	
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
    
    public boolean getTesting() {
		return testing;
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
        int i1 = 0;
        int i2 = 0;
        for(Player p : players){
            if(p.equals(currentPlayer)){
                i2 = i1;
                break;
            }
            i1++;
        }
        currentPlayer = players.get(i2);
    }
    public boolean getInit() {return init;}
    

    /**
     * Megnézi, hogy létezik-e a megadott nevű játékos a játékban.
     *
     * @param name a keresett név
     * @return true, ha van ilyen nevű játékos, különben false
     */
    public boolean hasPlayer(String name) {
        return players.stream().anyMatch(p -> p.getName().equalsIgnoreCase(name));
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
    public void nextTurnCheck() {
    	 planet.checkForDeadShrooms();
    	 planet.deleteDeadObjects(turnCounter, players);
    	 planet.checkForBodyConnection();
    	 if(currentPlayer.getActions() > 0)
    		 currentPlayer.takeAction();

         if (currentPlayer.getActions() == 0) {
             int currentIndex = players.indexOf(currentPlayer);
             int nextIndex = (currentIndex + 1) % players.size();  
             currentPlayer = players.get(nextIndex);
             turnCounter++;  
             currentPlayer.update(testing); 
             planet.update(!testing); 
             if (turnCounter == maxTurn) { 
                 ArrayList<Player> winners = determineWinners();
                 for (Player player : winners) {
                     System.out.println("Winner: " + player.getName() + " ");
                 }
             }
         }
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
