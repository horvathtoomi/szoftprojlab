package main.java.view;

public class DefaultDrawingFactory implements DrawingFactory {

    @Override
    public DefaultTectonDrawer createTectonDrawer(){
        return new DefaultTectonDrawer();
    }

    @Override
    public DefaultSporeDrawer createSporeDrawer(){
        return new DefaultSporeDrawer();
    }

    @Override
    public DefaultMushroomBodyDrawer createMushroomBodyDrawer(){
        return new DefaultMushroomBodyDrawer();
    }

    @Override
    public DefaultMushroomStringDrawer createMushroomStringDrawer(){
        return new DefaultMushroomStringDrawer();
    }

    @Override
    public DefaultInsectDrawer createInsectDrawer(){
        return new DefaultInsectDrawer();
    }

}