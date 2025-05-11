package main.java.view;


import java.awt.*;

import main.java.GamePanel;
import main.java.Planet;
import main.java.tecton.*;
import main.java.mushroom.*;
import main.java.insect.Insect;
import main.java.spore.Spore;

public class DrawManager {
    private DefaultTectonDrawer tectonDrawer;
    private DefaultSporeDrawer sporeDrawer;
    private DefaultMushroomBodyDrawer mushroomBodyDrawer;
    private DefaultMushroomStringDrawer mushroomStringDrawer;
    private DefaultInsectDrawer insectDrawer;

    public DrawManager(){
        setDrawers(new DefaultDrawingFactory());
    }

    public void setDrawers(DrawingFactory factory){
        this.tectonDrawer = factory.createTectonDrawer();
        this.insectDrawer = factory.createInsectDrawer();
        this.sporeDrawer = factory.createSporeDrawer();
        this.mushroomStringDrawer = factory.createMushroomStringDrawer();
        this.mushroomBodyDrawer = factory.createMushroomBodyDrawer();
    }

    public void drawTecton(Graphics2D g, Tecton t, boolean shining){
        if(shining){
            drawShineEffect(g, t.getGeometry().getX(), t.getGeometry().getY(), t.getGeometry().getRadius() + 10);
        }
        tectonDrawer.draw(g, t);
    }

    public void drawSpore(Graphics2D g, Spore spore, boolean shining) {
        if(shining){
            drawShineEffect(g, spore.getGeometry().getX(), spore.getGeometry().getY(), 25);
        }
        sporeDrawer.draw(g, spore);
    }

    public void drawMushroomBody(Graphics2D g, MushroomBody mushroomBody, boolean shining) {
        if(shining) {
            drawShineEffect(g, mushroomBody.getGeometry().getX(), mushroomBody.getGeometry().getY(), 35);
        }
        mushroomBodyDrawer.draw(g, mushroomBody);
    }

    public void drawMushroomString(Graphics2D g, MushroomString mushroomString, int x1, int x2, int y1, int y2, boolean shining){
        if(shining) {
            int midX = (x1 + x2) / 2;
            int midY = (y1 + y2) / 2;
            drawShineEffect(g, midX, midY, 30);
        }
        mushroomStringDrawer.draw(g, mushroomString, x1, y1, x2, y2);
    }

    public void drawInsect(Graphics2D g, Insect insect, boolean shining) {
        if(shining) {
            drawShineEffect(g, insect.getGeometry().getX() + 20, insect.getGeometry().getY() + 20, 30);
        }
        insectDrawer.draw(g, insect);
    }

    public void drawPlanet(Graphics2D g, Planet p, GamePanel.ShineOn shining) {
    	for(Tecton t : p.getTectons()) {
    		drawTecton(g, t, shining == GamePanel.ShineOn.TECTON);
    	}
    	
    	for(MushroomBody b : p.getMushbodies()) {
    		drawMushroomBody(g, b, shining == GamePanel.ShineOn.MUSHBODY);
    	}

        for(MushroomString ms : p.getMushstrings()) {
            if (ms.getConnection().size() >= 2) {
                Tecton t1 = ms.getConnection().get(0);
                Tecton t2 = ms.getConnection().get(1);
                drawMushroomString(g, ms,
                        t1.getGeometry().getX(), t1.getGeometry().getY(),
                        t2.getGeometry().getX(), t2.getGeometry().getY(),
                        shining == GamePanel.ShineOn.MUSHSTRING);
            }
        }
    	
    	for(Insect i : p.getInsects()) {
    		drawInsect(g, i, shining == GamePanel.ShineOn.INSECT);
    	}
    	
    	for(Spore sp : p.getSpores()) {
    		drawSpore(g, sp, shining == GamePanel.ShineOn.SPORE);
    	}
    }

    // Új metódus a kiemelő effekt rajzolásához
    private void drawShineEffect(Graphics2D g, int x, int y, int radius) {
        // Mentjük az eredeti beállításokat
        Composite originalComposite = g.getComposite();
        Color originalColor = g.getColor();
        Stroke originalStroke = g.getStroke();

        // Átlátszó fénylő köröket rajzolunk az objektum köré
        for (int i = 0; i < 5; i++) {
            float alpha = 0.1f - (i * 0.015f);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g.setColor(Color.YELLOW);
            g.setStroke(new BasicStroke(5 - i));
            g.drawOval(x - (radius + i*4), y - (radius + i*4), (radius + i*4) * 2, (radius + i*4) * 2);
        }

        // Visszaállítjuk az eredeti beállításokat
        g.setComposite(originalComposite);
        g.setColor(originalColor);
        g.setStroke(originalStroke);
    }

}