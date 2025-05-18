package main.java.control;

import main.java.GameController;
import main.java.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

/**
 * A KeyHandler osztály felelős a billentyűzeti input kezeléséért
 */
public class KeyHandler implements KeyListener {

    private int keyCode = -1; // alapértelmezett érték
    private final GameController game;
    private final Map<Integer, Boolean> keyMap = new HashMap<>();
    private final Runnable repaintCallback;
    private final GamePanel gamePanel;

    public static final int KEY_PASS = KeyEvent.VK_P;
    public static final int KEY_GROW_BODY = KeyEvent.VK_G;
    public static final int KEY_MUSHROOM = KeyEvent.VK_M;
    public static final int KEY_BRANCH = KeyEvent.VK_B;
    public static final int KEY_SPREAD_SPORE = KeyEvent.VK_S;
    public static final int KEY_HYPHA = KeyEvent.VK_H;
    public static final int KEY_EAT = KeyEvent.VK_E;
    public static final int KEY_CUT = KeyEvent.VK_C;
    public static final int KEY_MOVE = KeyEvent.VK_M;

    /**
     * Létrehozza a példányt a megfelelő komponensekkel
     *
     * @param gc A GameController példány, amihez tartozik
     * @param repaintCallback A képernyő újrarajzolását végző metódus
     * @param gamePanel A játék, amihez tartozik
     */
    public KeyHandler(GameController gc, Runnable repaintCallback, GamePanel gamePanel) {
        game = gc;
        this.repaintCallback = repaintCallback;
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Kezeli a billentyű lenyomás eseményeket.
     * Elmenti a lenyomott billentyű kódját.
     * @param e a billentyű esemény
     */
    @Override
    public void keyPressed(KeyEvent e) {
        keyCode = e.getKeyCode();
        keyMap.put(keyCode, true);

        //System.out.println("Key pressed: " + (char)keyCode);

        switch (keyCode) {
            case KEY_PASS:
                gamePanel.setShineOn(GamePanel.ShineOn.NONE);
                if (game.getCurrentPlayer() != null && !game.getInit()) {
                    game.getCurrentPlayer().pass();
                    game.nextTurnCheck();
                    repaintCallback.run();
                    resetKeyCode();
                }
                break;
            case KEY_GROW_BODY:
                gamePanel.setShineOn(GamePanel.ShineOn.TECTON);
                //Implementalas
                break;
            case KEY_MUSHROOM, KEY_HYPHA:
                gamePanel.setShineOn(GamePanel.ShineOn.MUSHBODY);
                break;
            case KEY_BRANCH, KEY_CUT:
                gamePanel.setShineOn(GamePanel.ShineOn.MUSHSTRING);
                break;
            case KEY_SPREAD_SPORE:
                gamePanel.setShineOn(GamePanel.ShineOn.TECTON);
                break;
            //case KEY_MOVE:
            //    gamePanel.setShineOn(GamePanel.ShineOn.TECTON);
            //    break;
            case KEY_EAT:
                gamePanel.setShineOn(GamePanel.ShineOn.SPORE);
                break;
            default:
                gamePanel.setShineOn(GamePanel.ShineOn.NONE);
                break;
        }
        //repaintCallback.run();
    }
    /**
     * A billentyű felengedésekor lefutó függvény
     * @param e a billentyű esemény
     */
    @Override
    public void keyReleased(KeyEvent e) {
        keyMap.put(e.getKeyCode(), false);
    }

    /**
     * Visszaadja az utoljára lenyomott billentyű kódját.
     * @return a keyCode
     */
    public int getKeyCode() {
        return keyCode;
    }
    /**
     * Visszaállítja a lenyomott billentyűt alapértelmezettre
     */
    public void resetKeyCode() {
        this.keyCode = -1;
    }
}
