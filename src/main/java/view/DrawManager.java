
public class DrawManager {

    private DefaultDrawingFactory factory;
    private DefaultTectonDrawer tectonDrawer;
    private DefaultSporeDrawer sporeDrawer;
    private DefaultMushroomBodyDrawer mushroomBodyDrawer;
    private DefaultMushroomStringDrawer mushroomStringDrawer;
    private DefaultInsectDrawer insectDrawer;

    public void setDrawers(DrawingFactory factory){
        
    }

    public void drawTecton(Graphics g, Tecton t, int x, int y){}

    public void drawSpore(Graphics g, Spore spore, int x, int y){}

    public void drawMushroomBody(Graphics g, MushroomBody mushroomBody, int x, int y){}

    public void drawMushroomString(Graphics g, MushroomString mushroomString, int x, int y){}

    public void drawInsect(Graphics g, Insect insect, int x, int y){}

    public void drawPlanet(Graphics g, Planet p){}

}