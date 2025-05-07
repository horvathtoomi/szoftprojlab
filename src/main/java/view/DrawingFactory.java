package main.java.view;

public interface DrawingFactory {
    public DefaultTectonDrawer createTectonDrawer();
    public DefaultSporeDrawer createSporeDrawer();
    public DefaultMushroomBodyDrawer createMushroomBodyDrawer();
    public DefaultMushroomStringDrawer createMushroomStringDrawer();
    public DefaultInsectDrawer createInsectDrawer();
}