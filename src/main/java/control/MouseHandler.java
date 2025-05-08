package main.java.control;

import main.java.GameController;
import main.java.player.Insecter;
import main.java.player.Shroomer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * A MouseHandler osztály felelős az egér események kezeléséért.
 * Kezeli a kattintásokat és az egér mozgását a felhasználói felületen.
 */
public class MouseHandler implements MouseListener, MouseMotionListener {

    private GameController gc;

    public MouseHandler(GameController gc) {
        this.gc = gc;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(gc.getCurrentPlayer() instanceof Shroomer) {

        }
        else if(gc.getCurrentPlayer() instanceof Insecter) {

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

}