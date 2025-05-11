package main.java.control;

import main.java.GameController;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class KeyHandler implements KeyListener {

    private int keyCode = -1; // alapértelmezett érték
    private final GameController game;
    private final Map<Integer, Boolean> keyMap = new HashMap<>();


    public static final int KEY_PASS = KeyEvent.VK_P;
    public static final int KEY_GROW_BODY = KeyEvent.VK_G;
    public static final int KEY_MUSHROOM = KeyEvent.VK_M;
    public static final int KEY_BRANCH = KeyEvent.VK_B;
    public static final int KEY_SPREAD_SPORE = KeyEvent.VK_S;
    public static final int KEY_HYPHA = KeyEvent.VK_H;
    public static final int KEY_EAT = KeyEvent.VK_E;
    public static final int KEY_CUT = KeyEvent.VK_C;

    public KeyHandler(GameController gc) {
        game = gc;
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

        System.out.println("Key pressed: " + keyCode);

        if (keyCode == KEY_PASS) {
            if (game.getCurrentPlayer() != null) {
                game.getCurrentPlayer().pass();
                game.nextTurnCheck();
            }
        }
    }

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

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public void resetKeyCode() {
        keyCode = -1;
    }

}
