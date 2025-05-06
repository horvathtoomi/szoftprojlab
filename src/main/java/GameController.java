package main.java;

import java.util.ArrayList;

/**
 * A GameController osztály felel a játék menetért, körváltásért, 
 * és a győztesek meghatározásáért. Egy Planet példányhoz kapcsolódik.
 */
public class GameController {
    private Planet planet;
    private int maxTurn;
    boolean testing;
    private int turnCounter;
    private ArrayList<Player> players;
    private Player currentPlayer;
    
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

    //Getterek, Setterek
    public Planet getPlanet(){
    	return planet;
    }
    
    public int getTurnCounter() {
    	return turnCounter;
    }

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
