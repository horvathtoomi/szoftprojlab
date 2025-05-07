package main.java.view;


import java.awt.*;
import main.java.*;

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

    public void drawTecton(Graphics2D g, Tecton t, int x, int y){
        tectonDrawer.draw(g, t, x, y);
    }

    public void drawSpore(Graphics2D g, Spore spore, int x, int y){
        sporeDrawer.draw(g, spore, x, y);
    }

    public void drawMushroomBody(Graphics2D g, MushroomBody mushroomBody, int x, int y){
        mushroomBodyDrawer.draw(g, mushroomBody, x, y);
    }

    public void drawMushroomString(Graphics2D g, MushroomString mushroomString, int x1, int x2, int y1, int y2){
        mushroomStringDrawer.draw(g, mushroomString, x1, x2, y1, y2);
    }

    public void drawInsect(Graphics2D g, Insect insect, int x, int y){
        insectDrawer.draw(g, insect, x, y);
    }

    public void drawPlanet(Graphics2D g, Planet p){}

}