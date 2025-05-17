package main.java.view;

/**
 * A drawer osztályokat létrehozó Factory modell
 */
public class DefaultDrawingFactory implements DrawingFactory {

    /**
     * Tektonkirajzoló létrehozása
     */
    @Override
    public DefaultTectonDrawer createTectonDrawer(){
        return new DefaultTectonDrawer();
    }

    /**
     * Spórkirajzoló létrehozása
     */
    @Override
    public DefaultSporeDrawer createSporeDrawer(){
        return new DefaultSporeDrawer();
    }

    /**
     * Gombatestkirajzoló létrehozása
     */
    @Override
    public DefaultMushroomBodyDrawer createMushroomBodyDrawer(){
        return new DefaultMushroomBodyDrawer();
    }

    /**
     * Fonalkirajzoló létrehozása
     */
    @Override
    public DefaultMushroomStringDrawer createMushroomStringDrawer(){
        return new DefaultMushroomStringDrawer();
    }

    /**
     * Rovarkirajzoló létrehozása
     */
    @Override
    public DefaultInsectDrawer createInsectDrawer(){
        return new DefaultInsectDrawer();
    }

}