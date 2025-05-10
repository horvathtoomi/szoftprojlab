package main.java.control;

import main.java.GameController;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private int keyCode = -1; // alapértelmezett érték
    GameController game;

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
        if(keyCode == KeyEvent.VK_P) {
            game.getCurrentPlayer().pass();
            game.nextTurnCheck();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    /**
     * Visszaadja az utoljára lenyomott billentyű kódját.
     * @return a keyCode
     */
    public int getKeyCode() {
        return keyCode;
    }
    public void setKeyCode(int keyCode) {keyCode = keyCode;}
    public void resetKeyCode() {keyCode = -1;}
}
