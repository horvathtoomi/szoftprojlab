package main.java.view;


import java.awt.*;

import main.java.GamePanel;
import main.java.Planet;
import main.java.tecton.*;
import main.java.mushroom.*;
import main.java.insect.Insect;
import main.java.spore.Spore;

/**
 * A kirajzolást kezelő osztály. Tartalmaz kirajzoló objektumokat
 */
public class DrawManager {
    private DefaultTectonDrawer tectonDrawer;
    private DefaultSporeDrawer sporeDrawer;
    private DefaultMushroomBodyDrawer mushroomBodyDrawer;
    private DefaultMushroomStringDrawer mushroomStringDrawer;
    private DefaultInsectDrawer insectDrawer;

    /**
     * ALétrehoz egy új példányt, és meghívja a setDrawers() metódust
     */
    public DrawManager(){
        setDrawers(new DefaultDrawingFactory());
    }

    /**
     * A kapott factory segítségével létrehozza a kirajzolókat
     * @param factory a Factory, amivel a létrehozást végzi
     */
    public void setDrawers(DrawingFactory factory){
        this.tectonDrawer = factory.createTectonDrawer();
        this.insectDrawer = factory.createInsectDrawer();
        this.sporeDrawer = factory.createSporeDrawer();
        this.mushroomStringDrawer = factory.createMushroomStringDrawer();
        this.mushroomBodyDrawer = factory.createMushroomBodyDrawer();
    }

    /**
     * A megfelelő drawer draw() metódusát meghívja
     * @param g A Rajzoló objektum
     * @param t A kirajzolandó objektum
     * @param shining Ki kell-e emelni az objektumot
     */
    public void drawTecton(Graphics2D g, Tecton t, boolean shining){
        if(shining){
            drawShineEffect(g, (int) t.getGeometry().getX(), (int) t.getGeometry().getY(), (int) (t.getGeometry().getRadius() + 10));
        }
        tectonDrawer.draw(g, t);
    }

    /**
     * A megfelelő drawer draw() metódusát meghívja
     * @param g A Rajzoló objektum
     * @param spore A kirajzolandó objektum
     * @param shining Ki kell-e emelni az objektumot
     */
    public void drawSpore(Graphics2D g, Spore spore, boolean shining) {
        if(shining){
            drawShineEffect(g, (int) spore.getGeometry().getX(), (int) spore.getGeometry().getY(), 25);
        }
        sporeDrawer.draw(g, spore);
    }
    /**
     * A megfelelő drawer draw() metódusát meghívja
     * @param g A Rajzoló objektum
     * @param mushroomBody A kirajzolandó objektum
     * @param shining Ki kell-e emelni az objektumot
     */
    public void drawMushroomBody(Graphics2D g, MushroomBody mushroomBody, boolean shining) {
        if(shining) {
            drawShineEffect(g, (int) mushroomBody.getGeometry().getX(), (int) mushroomBody.getGeometry().getY(), 35);
        }
        mushroomBodyDrawer.draw(g, mushroomBody);
    }

    /**
     * A megfelelő drawer draw() metódusát meghívja
     * @param g A Rajzoló objektum
     * @param mushroomString A kirajzolandó objektum
     * @param shining Ki kell-e emelni az objektumot
     */
    public void drawMushroomString(Graphics2D g, MushroomString mushroomString,boolean shining){
        if(shining) {
            int midX = (mushroomString.getGeometry().getX() +mushroomString.getGeometry().getX2() ) / 2;
            int midY = (mushroomString.getGeometry().getY() +mushroomString.getGeometry().getY2()) / 2;
            drawShineEffect(g, midX, midY, 30);
        }
        mushroomStringDrawer.draw(g, mushroomString);
    }

    /**
     * A megfelelő drawer draw() metódusát meghívja
     * @param g A Rajzoló objektum
     * @param insect A kirajzolandó objektum
     * @param shining Ki kell-e emelni az objektumot
     */
    public void drawInsect(Graphics2D g, Insect insect, boolean shining) {
        if(shining) {
            drawShineEffect(g, (int) (insect.getGeometry().getX() + 20), (int) (insect.getGeometry().getY() + 20), 30);
        }
        insectDrawer.draw(g, insect);
    }

    /**
     * Kirajzolja a teljes bolygót
     * @param g A Rajzoló objektum
     * @param p A kirajzolandó objektumokat tartalmazó planet
     * @param shining Ki kell-e emelni az objektumot
     */
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

    /**
     * Megjeleníti a kiemelés effektet
     * @param g A Rajzoló objektum
     * @param x Az effekt x koordinátája
     * @param y Az effekt Y koordinátája
     * @param radius Az effekt sugara
     */
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