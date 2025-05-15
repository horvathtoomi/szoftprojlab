package main.java;

import main.java.player.Player;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A játék állapotát tároló osztály, a mentés/betöltés funkcióhoz.
 */
public record GameState(Planet planet, ArrayList<Player> players, int turnCounter, Player currentPlayer, boolean isInit) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

}