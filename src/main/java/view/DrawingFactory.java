package main.java.view;

public interface DrawingFactory {
    DefaultTectonDrawer createTectonDrawer();
    DefaultSporeDrawer createSporeDrawer();
    DefaultMushroomBodyDrawer createMushroomBodyDrawer();
    DefaultMushroomStringDrawer createMushroomStringDrawer();
    DefaultInsectDrawer createInsectDrawer();
}