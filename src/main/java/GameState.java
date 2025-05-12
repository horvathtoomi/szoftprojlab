package main.java;

import main.java.player.Player;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A játék állapotát tároló osztály, a mentés/betöltés funkcióhoz.
 */
public class GameState implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Planet planet;
    private final ArrayList<Player> players;
    private final int turnCounter;
    private final Player currentPlayer;
    private final boolean isInit;

    public GameState(Planet planet, ArrayList<Player> players, int turnCounter, Player currentPlayer, boolean isInit) {
        this.planet = planet;
        this.players = players;
        this.turnCounter = turnCounter;
        this.currentPlayer = currentPlayer;
        this.isInit = isInit;
    }

    public Planet getPlanet() {
        return planet;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isInit() {
        return isInit;
    }
}