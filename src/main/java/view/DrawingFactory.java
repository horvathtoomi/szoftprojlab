package main.java.view;

/**
 * A bővíthetőség érdekében létrehozott interfész a Factory modellhez
 */
public interface DrawingFactory {
    DefaultTectonDrawer createTectonDrawer();
    DefaultSporeDrawer createSporeDrawer();
    DefaultMushroomBodyDrawer createMushroomBodyDrawer();
    DefaultMushroomStringDrawer createMushroomStringDrawer();
    DefaultInsectDrawer createInsectDrawer();
}