package main.java.view;


import java.awt.*;
import main.java.*;

public class DrawManager {

    private DefaultDrawingFactory factory;
    private DefaultTectonDrawer tectonDrawer;
    private DefaultSporeDrawer sporeDrawer;
    private DefaultMushroomBodyDrawer mushroomBodyDrawer;
    private DefaultMushroomStringDrawer mushroomStringDrawer;
    private DefaultInsectDrawer insectDrawer;

    public void setDrawers(DrawingFactory factory){
        
    }

    public void drawTecton(Graphics2D g, Tecton t, int x, int y){}

    public void drawSpore(Graphics2D g, Spore spore, int x, int y){}

    public void drawMushroomBody(Graphics2D g, MushroomBody mushroomBody, int x, int y){}

    public void drawMushroomString(Graphics2D g, MushroomString mushroomString, int x, int y){}

    public void drawInsect(Graphics2D g, Insect insect, int x, int y){}

    public void drawPlanet(Graphics2D g, Planet p){}

}