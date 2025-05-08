package main.java;

import main.java.view.DrawManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    private final GameController gameController;
    private final DrawManager drawManager;

    public GamePanel() {
        gameController = new GameController(false, 100);
        drawManager = new DrawManager();

        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        while(true) {
            repaint();
            try{
                Thread.sleep(1000/2);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawManager.drawPlanet(g2, gameController.getPlanet());
    }

    public static void main(String[] args) {
        GamePanel gamePanel = new GamePanel();
        MainMenu menu = new MainMenu();
    }

}
